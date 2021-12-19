package com.steam.gateway.filter;


import com.steam.gateway.jwt.JwtUtil;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PermissionCheckFilter implements Filter {
    private final JwtUtil jwtUtil;

    public PermissionCheckFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Cookie[] cookies = ((HttpServletRequest)request).getCookies();
        String accessToken = jwtUtil.getAccessTokenByCookies(cookies);

        if(jwtUtil.isAdmin(accessToken))
            chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
}
