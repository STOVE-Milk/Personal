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
import java.util.stream.Collectors;

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

        //System.out.println(getBody(httpServletRequest));

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
