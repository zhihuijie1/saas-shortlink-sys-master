package com.saas.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.saas.admin.common.constant.RedisCacheConstant.LOCK_USER_REGISTER_KEY;
import static com.saas.admin.common.enums.UserErrorCodeEnum.USER_NAME_EXIST;
import static com.saas.admin.common.enums.UserErrorCodeEnum.USER_SAVE_ERROR;
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService {

    @Autowired
    RBloomFilter<String> userRegisterCachePenetrationBloomFilter; // 引入用户注册布隆过滤器
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据用户名，查询用户信息
     */
    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class)
                .eq(UserDo::getUsername, username);
        UserDo userDo = baseMapper.selectOne(queryWrapper);
        if (userDo == null) {
            throw new ServiceException(UserErrorCodeEnum.USER_NULL);
        }
        UserRespDTO result = new UserRespDTO();
        BeanUtil.copyProperties(userDo, result);
        return result;
    }

    /**
     * 查询用户名是否存在
     */
    @Override
    public boolean hasUsername(String username) {
        boolean iscontains = userRegisterCachePenetrationBloomFilter.contains(username);
        return iscontains;
    }

    /**
     * 注册用户
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if (hasUsername(requestParam.getUsername())) {
            throw new ClientException(USER_NAME_EXIST);
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + requestParam.getUsername());
        try {
            boolean triedLock = lock.tryLock();
            if (triedLock) {
                int insert = baseMapper.insert(BeanUtil.toBean(requestParam, UserDo.class));
                if (insert < 1) {
                    throw new ClientException(USER_SAVE_ERROR);
                }
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
                return;
            }
            throw new ClientException(USER_NAME_EXIST);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 修改用户信息
     */
    @Override
    public void update(UserUpdateReqDTO requestParam) {
        // TODO: 验证修改的用户信息是否属于当前用户 - 使用网关验证
        LambdaUpdateWrapper<UserDo> updateWrapper = Wrappers.lambdaUpdate(UserDo.class)
                .eq(UserDo::getUsername, requestParam.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParam, UserDo.class), updateWrapper);
    }

    /**
     * 用户登录
     */
    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class)
                .eq(UserDo::getUsername, requestParam.getUsername())
                .eq(UserDo::getPassword, requestParam.getPassword())
                .eq(UserDo::getDelFlag, 0);
        UserDo userDo = baseMapper.selectOne(queryWrapper);
        if(userDo == null) {
            throw new ClientException("用户不存在");
        }
        // 可能存在用户无限次登录的情况 -> 导致同一个redis key 无限次被重新赋值
        if(stringRedisTemplate.hasKey("login_" + requestParam.getUsername())) {
            throw new ClientException("用户已登录");
        }
        /**
         * HASH:
         * key: login_username
         * value:
         *      key: token
         *      value: 用户的个人信息
         */
        // 生成token
        String uuid = UUID.randomUUID().toString();
        // redis存储用户登录时的信息： 用户登录redis键 + token + 个人信息
        stringRedisTemplate.opsForHash().put("login_" + requestParam.getUsername(), uuid, JSON.toJSONString(userDo));
        // 设置redis键的存活时间 - 用户登录时间
        stringRedisTemplate.expire("login_" + requestParam.getUsername(),30L, TimeUnit.DAYS);
        return new UserLoginRespDTO(uuid);
    }

    /**
     * 检查用户是否登录
     */
    @Override
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get("login_" + username, token) != null;
    }

    /**
     * 用户退出登录
     */
    @Override
    public void logout(String username, String token) {
        if(checkLogin(username, token)) {
            stringRedisTemplate.delete("login_" + username);
            return;
        }
        throw new ClientException("用户Token不存在或用户未登录");
    }
}
