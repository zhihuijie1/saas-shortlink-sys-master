package com.saas.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saas.admin.dao.entity.GroupDo;
import com.saas.admin.dao.mapper.GroupMapper;
import com.saas.admin.service.GroupService;
import com.saas.admin.toolkit.RandomGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDo> implements GroupService {

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
                        .build()
        );
    }

    // 数据库中是否存在一样的gid 是的话return true
    private boolean isHasGid(String gid) {
        LambdaQueryWrapper<GroupDo> queryWrapper = Wrappers.lambdaQuery(GroupDo.class)
                // TODO 设置用户名 - 这个地方需要从网关中传入
                .eq(GroupDo::getGid, gid)
                .eq(GroupDo::getName, null);
        GroupDo groupDo = baseMapper.selectOne(queryWrapper);
        return groupDo == null ? false : true;
    }
}
