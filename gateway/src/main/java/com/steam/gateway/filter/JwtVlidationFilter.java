package com.steam.gateway.filter;

import com.steam.gateway.jwt.JwtUtil;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;

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

        List<String> skippedPath = List.of("/auth/login", "/auth/regist");
        String path = httpServletRequest.getServletPath();
        //String redirectPath = "http://localhost/login?requestURI=" + httpServletRequest.getRequestURI();

        String accessToken = "";

        if (skippedPath.contains(path))
            chain.doFilter(request, response);
        else {
            accessToken = jwtUtil.getAccessTokenInRequest(httpServletRequest);
            if (accessToken.isBlank() || !jwtUtil.isValid(accessToken)) {
                httpServletResponse.setStatus(401);
            }

            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    public void printHeader(HttpServletRequest httpServletRequest) {
        Enumeration headerNames = httpServletRequest.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String name = (String)headerNames.nextElement();
            String value = httpServletRequest.getHeader(name);

            System.out.println(name + ":" + value);
        }
    }
}
