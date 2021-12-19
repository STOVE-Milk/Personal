package com.steam.view.controller;

import com.steam.view.jwt.JwtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PageController {
    private final JwtUtil jwtUtil;

    public PageController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/")
    public String getIndexPage(Model model, HttpServletRequest request) {
        setAuthorization(model, request);
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/regist")
    public String getRegistPage() {
        return "regist";
    }

    @GetMapping("/management")
    public String getManagementPage(Model model, HttpServletRequest request) {
        setAuthorization(model, request);
        return "management";
    }

    private void setAuthorization(Model model, HttpServletRequest request) {
        String accessToken = jwtUtil.getAccessTokenByCookies(request.getCookies());
        model.addAttribute("isLogin", jwtUtil.isLogin(accessToken));

        model.addAttribute("isAdmin", jwtUtil.isAdmin(accessToken));
    }
}
