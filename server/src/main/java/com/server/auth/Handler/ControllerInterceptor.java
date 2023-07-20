package com.server.auth.Handler;


import com.server.advice.ErrorResponder;
import com.server.auth.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class ControllerInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;
    private static final ThreadLocal<String> authenticatedUsername = new ThreadLocal<>();

    public ControllerInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public static String getAuthenticatedUsername() {
        return authenticatedUsername.get();
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String method = request.getMethod(), uri = request.getRequestURI();


        // Member 전체 권한 관련 설정
        if (method.equals("POST") && uri.equals("/members")) return true;
        if (method.equals("GET") && uri.equals("/members")) return true;
        if (method.equals("POST") && uri.equals("/login")) return true;

        // Trade 전체 권한 관련 설정
//        if (method.equals("POST") && uri.startsWith("/trades")) return true;
//        if (method.equals("GET") && uri.startsWith("/wishlists")) return true;


        try {
            Map<String, Object> claims = jwtUtils.getJwsClaimsFromRequest(request); // claims
            authenticatedUsername.set(String.valueOf(claims.get("username").toString()));
            return true;
        } catch (Exception e) {
            // 보안과 관련된 에러는 ErrorResponder를 이용하여 중복 로직을 최소화하자
            ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED);
            return false;
        }
    }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                @Nullable ModelAndView modelAndView) throws Exception {
            this.authenticatedUsername.remove();
        }






}