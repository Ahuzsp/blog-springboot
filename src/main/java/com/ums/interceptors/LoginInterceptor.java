package com.ums.interceptors;

import com.ums.common.ResultCode;
import com.ums.utils.JwtUtil;
import com.ums.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new UnauthorizedException(ResultCode.UNAUTHORIZED.getMessage());
        }

        try {
            String token = authorization.substring(7);
            String redisToken = stringRedisTemplate.opsForValue().get("loginToken");
            if (redisToken == null || !redisToken.equals(token)) {
                throw new UnauthorizedException(ResultCode.UNAUTHORIZED.getMessage());
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(claims);
            return true;
        } catch (Exception e) {
            throw new UnauthorizedException(ResultCode.UNAUTHORIZED.getMessage());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
