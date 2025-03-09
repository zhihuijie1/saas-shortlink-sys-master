package com.saas.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.saas.admin.dao.entity.GroupDo;
import com.saas.admin.dto.req.ShortLinkGroupSortReqDTO;
import com.saas.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.saas.admin.dto.resp.ShortLinkGroupRespDTO;

import java.util.List;

public interface GroupService extends IService<GroupDo> {
    /**
     * 新增分组
     * @param name 分组名称
     */
    void saveGroup(String name);

    List<ShortLinkGroupRespDTO> listGroup();

    void updateGroup(ShortLinkGroupUpdateReqDTO requestParam);

    void deleteGroup(String gid);

    void sortGroup(List<ShortLinkGroupSortReqDTO> requestParam);
}
