package com.saas.admin.common.biz.user;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Set;

/**
 * 用户信息传输过滤器
 */
@RequiredArgsConstructor
public class UserTransmitFilter implements Filter {

    private final StringRedisTemplate stringRedisTemplate;

    // 白名单路径（精确匹配）
    private static final Set<String> ALLOWED_PATHS = Set.of(
            "/api/short-link/admin/v1/user",
            "/api/short-link/admin/v1/user/login"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        // 白名单路径直接放行
        if (ALLOWED_PATHS.contains(path)) {
            filterChain.doFilter(request, response);
            return; // 直接返回，不执行后续逻辑
        }

        // 从请求头获取用户的token和username
        String token = httpRequest.getHeader("token");
        String username = httpRequest.getHeader("username");

        // 1. 检查必要参数是否存在
        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(username)) {
            httpResponse.sendError(401, "用户名或Token缺失");
            return; // 拦截
        }

        // 2. 检查用户信息是否存在
        Object userDo = stringRedisTemplate.opsForHash().get("login_" + username, token);
        if(userDo == null) {
            httpResponse.sendError(401, "用户未登录或Token失效");
            return;
        }

        // 3. 用户信息存在时才放行
        UserInfoDTO userInfoDTO = JSON.parseObject(userDo.toString(), UserInfoDTO.class);
        try {
            UserContext.setUser(userInfoDTO);
            filterChain.doFilter(request, response);
        }finally {
            UserContext.removeUser();
        }
    }
}
