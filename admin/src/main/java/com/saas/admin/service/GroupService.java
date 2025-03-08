package com.saas.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.saas.admin.dao.entity.GroupDo;

public interface GroupService extends IService<GroupDo> {
    /**
     * 新增分组
     * @param name 分组名称
     */
    void saveGroup(String name);
}
