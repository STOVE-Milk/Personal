package com.steam.gateway.controller;

import com.steam.gateway.service.ApiService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final String authServerURL = "http://localhost:8081/auth";
    private final ApiService<ResponseEntity<Object>> apiService;

    public AuthController(ApiService<ResponseEntity<Object>> apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<byte[]> login(@RequestHeader MultiValueMap<String, String> header, @RequestBody byte[] body) {
        return apiService.post(authServerURL + "/login", header, body);
    }

    @PostMapping("/regist")
    @ResponseBody
    public ResponseEntity<byte[]> regist(@RequestHeader MultiValueMap<String, String> header, @RequestBody byte[] body) {
        return apiService.post(authServerURL + "/regist", header, body);
    }
//    @PostMapping("/proxy/**")
//    public ResponseEntity<byte[]> proxy(@RequestHeader MultiValueMap<String, String> header, @RequestBody byte[] body) throws IOException, URISyntaxException {
//        return ApiService.
//    }
}
