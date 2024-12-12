package com.saas.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saas.admin.common.convention.exception.ClientException;
import com.saas.admin.common.convention.exception.ServiceException;
import com.saas.admin.common.enums.UserErrorCodeEnum;
import com.saas.admin.dao.entity.UserDo;
import com.saas.admin.dao.mapper.UserMapper;
import com.saas.admin.dto.req.UserLoginReqDTO;
import com.saas.admin.dto.req.UserRegisterReqDTO;
import com.saas.admin.dto.req.UserUpdateReqDTO;
import com.saas.admin.dto.resp.UserLoginRespDTO;
import com.saas.admin.dto.resp.UserRespDTO;
import com.saas.admin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import java.util.Date;

import java.rmi.ServerException;
import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService {
    private static final String SECRET_KEY = "your_secret_key"; // Replace with your actual secret key
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据用户名，查询用户信息
     */
    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class).eq(UserDo::getUsername, username);
        UserDo userDo = baseMapper.selectOne(queryWrapper);
        if(userDo == null) {
            throw new ServiceException(UserErrorCodeEnum.USER_NULL);
        }
        UserRespDTO result = new UserRespDTO();
        return BeanUtil.toBean(userDo, UserRespDTO.class);
        //return result;
    }

    /**
     * 查询用户名是否存在
     */
    @Override
    public boolean hasUsername(String username) {
        LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class).eq(UserDo::getUsername, username);
        return baseMapper.exists(queryWrapper);
    }

    /**
     * 注册用户
     */
    @Override
    public Void register(UserRegisterReqDTO requestParam) {
        if(this.hasUsername(requestParam.getUsername())) {
            throw new ClientException(UserErrorCodeEnum.USER_EXIST);
        }
        int insert = baseMapper.insert(BeanUtil.toBean(requestParam, UserDo.class));
        if(insert < 1) {
            throw new ClientException(UserErrorCodeEnum.USER_SAVE_ERROR);
        }
        return null;
    }

    /**
     * 修改用户信息
     */
    @Override
    public Void update(UserUpdateReqDTO userUpdateReqDTO) {
        LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class).eq(UserDo::getUsername, userUpdateReqDTO.getUsername());
        int update = baseMapper.update(BeanUtil.toBean(userUpdateReqDTO, UserDo.class), queryWrapper);
        if(update < 1) {
            throw new ClientException(UserErrorCodeEnum.USER_UPDATE_ERROR);
        }
        return null;
    }

    /**
     * 用户登录
     */
    @Override
    public UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO) {
        LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class)
                .eq(UserDo::getUsername, userLoginReqDTO.getUsername())
                .eq(UserDo::getPassword, userLoginReqDTO.getPassword());
        UserDo userDo = baseMapper.selectOne(queryWrapper);
        if(userDo == null) {
            throw new ClientException(UserErrorCodeEnum.USER_NULL);
        }
        String token = generateToken(userLoginReqDTO.getUsername());
        return new UserLoginRespDTO(token);
    }
    
    /**
     * 检查用户是否登录
     */
    @Override
    public Boolean checkLogin(String username, String token) {
        return validateToken(token);
    }

    /**
     * 用户退出登录
     */
    @Override
    public Void logout() {
        return null;
    }
}
