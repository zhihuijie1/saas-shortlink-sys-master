package com.saas.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.saas.admin.dao.entity.UserDo;
import com.saas.admin.dto.resp.UserRespDTO;

public interface UserService extends IService<UserDo> {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    UserRespDTO getUserByUsername(String username);
}