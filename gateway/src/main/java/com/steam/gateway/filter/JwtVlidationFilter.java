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
        Cookie[] cookies = httpServletRequest.getCookies();

        List<String> skippedPath = List.of("/auth/login", "/auth/regist");
        String path = httpServletRequest.getServletPath();
        String redirectPath = "http://localhost/login?requestURI=" + httpServletRequest.getRequestURI();

        String accessToken = "";

        System.out.println(cookies == null);

        if (skippedPath.contains(path))
            chain.doFilter(request, response);
        else if (cookies == null)
            httpServletResponse.sendRedirect(redirectPath);
        else {
            accessToken = jwtUtil.getAccessTokenByCookies(cookies);

            if (accessToken.isBlank() || !jwtUtil.isValid(accessToken)) {
                for (Cookie cookie : cookies) cookie.setMaxAge(0);
                httpServletResponse.sendRedirect(redirectPath);
                return;
            }

            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    public static String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }
}
