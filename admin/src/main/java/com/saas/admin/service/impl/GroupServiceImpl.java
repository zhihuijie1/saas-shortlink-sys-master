package com.saas.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saas.admin.common.biz.user.UserContext;
import com.saas.admin.dao.entity.GroupDo;
import com.saas.admin.dao.mapper.GroupMapper;
import com.saas.admin.dto.req.ShortLinkGroupSortReqDTO;
import com.saas.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.saas.admin.dto.resp.ShortLinkGroupRespDTO;
import com.saas.admin.service.GroupService;
import com.saas.admin.toolkit.RandomGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDo> implements GroupService {

    /**
     * 新增分组
     * @name: 组名
     */
    @Override
    public void saveGroup(String name) {
        String gid;
        do {
            // 随机生成一个6位数字id
            gid = RandomGenerator.generateRandom();
        }while(isHasGid(gid));

        baseMapper.insert(
                GroupDo.builder()
                        .gid(gid)
                        .name(name)
                        .sortOrder(0)
                        .username(UserContext.getUsername())
                        .build()
        );
    }

    // 数据库中是否存在一样的gid 是的话return true
    private boolean isHasGid(String gid) {
        LambdaQueryWrapper<GroupDo> queryWrapper = Wrappers.lambdaQuery(GroupDo.class)
                // TODO 设置用户名 - 这个地方需要从网关中传入
                .eq(GroupDo::getUsername, UserContext.getUsername())
                .eq(GroupDo::getGid, gid)
                .eq(GroupDo::getName, null);
        GroupDo groupDo = baseMapper.selectOne(queryWrapper);
        return groupDo == null ? false : true;
    }

    /**
     * 查询分组列表
     */
    @Override
    public List<ShortLinkGroupRespDTO> listGroup() {
        // TODO 要根据用户名来查询当前的分组列表
        LambdaQueryWrapper<GroupDo> queryWrapper = Wrappers.lambdaQuery(GroupDo.class)
                .eq(GroupDo::getUsername, UserContext.getUsername())
                .eq(GroupDo::getDelFlag, 0)
                .orderByAsc(GroupDo::getSortOrder, GroupDo::getUpdateTime);
        List<GroupDo> groupDos = baseMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(groupDos, ShortLinkGroupRespDTO.class);
    }

    /**
     * 修改分组
     * @requestParam： 分组内容
     */
    @Override
    public void updateGroup(ShortLinkGroupUpdateReqDTO requestParam) {
        LambdaQueryWrapper<GroupDo> queryWrapper = Wrappers.lambdaQuery(GroupDo.class)
                .eq(GroupDo::getGid, requestParam.getGid())
                .eq(GroupDo::getUsername, UserContext.getUsername())
                .eq(GroupDo::getDelFlag, 0);
        GroupDo build = GroupDo.builder()
                .name(requestParam.getName())
                .build();
        baseMapper.update(build, queryWrapper);
    }

    /**
     * 删除分组
     * @gid：分组标识
     */
    @Override
    public void deleteGroup(String gid) {
        LambdaQueryWrapper<GroupDo> queryWrapper = Wrappers.lambdaQuery(GroupDo.class)
                .eq(GroupDo::getGid, gid)
                .eq(GroupDo::getUsername, UserContext.getUsername())
                .eq(GroupDo::getDelFlag, 0);
        GroupDo build = GroupDo.builder()
                .delFlag(1)
                .build();
        baseMapper.update(build, queryWrapper);
    }

    /**
     * 分组排序
     * @requestParam: 分组集合
     */
    @Override
    public void sortGroup(List<ShortLinkGroupSortReqDTO> requestParam) {
        requestParam.forEach(each -> {
            LambdaQueryWrapper<GroupDo> queryWrapper = Wrappers.lambdaQuery(GroupDo.class)
                    .eq(GroupDo::getUsername, UserContext.getUsername())
                    .eq(GroupDo::getDelFlag, 0)
                    .eq(GroupDo::getGid, each.getGid());
            GroupDo build = GroupDo.builder()
                    .sortOrder(each.getSortOrder())
                    .build();
            baseMapper.update(build, queryWrapper);
        });
    }
}
