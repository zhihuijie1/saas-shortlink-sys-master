package com.saas.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.saas.admin.dao.entity.UserDo;
import com.saas.admin.dto.req.UserLoginReqDTO;
import com.saas.admin.dto.req.UserRegisterReqDTO;
import com.saas.admin.dto.req.UserUpdateReqDTO;
import com.saas.admin.dto.resp.UserLoginRespDTO;
import com.saas.admin.dto.resp.UserRespDTO;

public interface UserService extends IService<UserDo> {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    UserRespDTO getUserByUsername(String username);

    boolean hasUsername(String username);

    Void register(UserRegisterReqDTO requestParam);

    Void update(UserUpdateReqDTO userUpdateReqDTO);

    UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO);
}
