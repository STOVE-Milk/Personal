package com.steam.gateway.controller;

import com.steam.gateway.service.ApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/management")
public class ManagementController {
    private final String managementServerUri = "http://localhost:8082";
    private final ApiService<ResponseEntity<Object>> apiService;

    public ManagementController(ApiService<ResponseEntity<Object>> apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity<byte[]> getUserPage(HttpServletRequest request, @RequestHeader MultiValueMap<String, String> header, @RequestParam Integer page) {
        return apiService.get(managementServerUri + request.getRequestURI(), "?page=" + Integer.toString(page), header);
    }

    @PatchMapping("/users/{userId}")
    @ResponseBody
    public ResponseEntity<byte[]> regist(HttpServletRequest request, @RequestHeader MultiValueMap<String, String> header, @RequestBody byte[] body) {
        return apiService.patch(managementServerUri + request.getRequestURI(), header, body);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseBody
    public ResponseEntity<byte[]> deleteUser(HttpServletRequest request, @RequestHeader MultiValueMap<String, String> header, @RequestBody byte[] body) {
        return apiService.patch(managementServerUri + request.getRequestURI(), header, body);
    }
}
