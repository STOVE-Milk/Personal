package com.steam.gateway.filter;

import com.steam.gateway.jwt.JwtUtil;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtVlidationFilter implements Filter {
    private final JwtUtil jwtUtil;

    public JwtVlidationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Cookie[] cookies = httpServletRequest.getCookies();
        String path = httpServletRequest.getServletPath();
        String accessToken = "";

        if (path.equals("/api/auth/login") || path.equals("/api/auth/regist"))
            chain.doFilter(request, response);
        else if (cookies == null)
            httpServletResponse.sendRedirect("/login?requestURI=" + httpServletRequest.getRequestURI());
        else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    accessToken = cookie.getValue();
                    break;
                }
            }

            if (accessToken.isBlank() || !jwtUtil.isValid(accessToken)) {
                for (Cookie cookie : cookies) cookie.setMaxAge(0);
                httpServletResponse.sendRedirect("/login?requestURI=" + httpServletRequest.getRequestURI());
                return;
            }

            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
}
