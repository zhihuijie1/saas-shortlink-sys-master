package com.saas.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.saas.admin.common.convention.result.Result;
import com.saas.admin.common.convention.result.Results;
import com.saas.admin.dto.req.UserLoginReqDTO;
import com.saas.admin.dto.req.UserRegisterReqDTO;
import com.saas.admin.dto.req.UserUpdateReqDTO;
import com.saas.admin.dto.resp.UserActualRespDTO;
import com.saas.admin.dto.resp.UserLoginRespDTO;
import com.saas.admin.dto.resp.UserRespDTO;
import com.saas.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 根据用户名，查询用户信息
     */
    @GetMapping("/api/short-link/admin/v1/user/{username}")
    public Result<UserRespDTO> getByUsername(@PathVariable("username") String username) {
        UserRespDTO userByUsernameDto = userService.getUserByUsername(username);
        return Results.success(userByUsernameDto);
    }

    /**
     * 根据用户名查询无脱敏用户信息
     */
    @GetMapping("/api/short-link/admin/v1/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username) {
        UserRespDTO userByUsernameDto = userService.getUserByUsername(username);
        return Results.success(BeanUtil.toBean(userByUsernameDto, UserActualRespDTO.class));
    }

    /**
     * 查询用户名是否存在
     */
    @GetMapping("/api/short-link/admin/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username) {
        boolean ishasUsername = userService.hasUsername(username);
        return Results.success(ishasUsername);
    }

    /**
     * 注册用户
     */
    @PostMapping("/api/short-link/admin/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam) {
        return Results.success(userService.register(requestParam));
    }

    /**
     * 修改用户
     */
    @GetMapping("/api/short-link/admin/v1/user")
    public Result<Void> update(@RequestBody UserUpdateReqDTO userUpdateReqDTO) {
        return Results.success(userService.update(userUpdateReqDTO));
    }

    /**
     * 用户登录
     */
    @PostMapping("/api/short-link/admin/v1/user/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO userLoginReqDTO) {
        return Results.success(userService.login(userLoginReqDTO));
    }


    /**
     * 检查用户是否登录
     */
    @GetMapping("/api/short-link/admin/v1/user/check-login")
    public Result<Boolean> checkLogin(@RequestParam("username") String username,@RequestParam("token") String token) {
        return Results.success(userService.checkLogin(username, token));
    }

    /**
     * 用户退出登录
     */
    @GetMapping("/api/short-link/admin/v1/user/logout")
    public Result<Void> logout(@RequestParam("username") String username, @RequestParam("token") String token) {
        return Results.success(userService.logout());
    }
}