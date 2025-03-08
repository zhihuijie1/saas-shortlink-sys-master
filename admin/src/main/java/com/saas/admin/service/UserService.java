package com.saas.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.saas.admin.dao.entity.UserDo;
import com.saas.admin.dto.req.UserLoginReqDTO;
import com.saas.admin.dto.req.UserRegisterReqDTO;
import com.saas.admin.dto.req.UserUpdateReqDTO;
import com.saas.admin.dto.resp.UserLoginRespDTO;
import com.saas.admin.dto.resp.UserRespDTO;

public interface UserService extends IService<UserDo> {

    UserRespDTO getUserByUsername(String username);

    boolean hasUsername(String username);

    void register(UserRegisterReqDTO requestParam);

    void update(UserUpdateReqDTO userUpdateReqDTO);

    UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO);

    Boolean checkLogin(String username, String token);

    void logout(String username, String token);
}
