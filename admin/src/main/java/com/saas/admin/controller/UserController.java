package com.saas.admin.controller;

import com.saas.admin.common.convention.result.Result;
import com.saas.admin.common.convention.result.Results;
import com.saas.admin.dto.resp.UserRespDTO;
import com.saas.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    /**
     * 根据用户名，查询用户信息
     * @param username 用户名
     * @return
     */
    @GetMapping("/api/short-link/admin/v1/user/{username}")
    public Result<UserRespDTO> getByUsername(@PathVariable("username") String username) {
        return Results.success(userService.getUserByUsername(username));
    }

    
}