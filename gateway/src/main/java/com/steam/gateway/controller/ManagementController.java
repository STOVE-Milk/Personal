package com.steam.gateway.controller;

import com.steam.gateway.service.ApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/management")
public class ManagementController {
    private final String managementServerUri = "http://localhost:8081/management";
    private final ApiService<ResponseEntity<Object>> apiService;

    public ManagementController(ApiService<ResponseEntity<Object>> apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity<byte[]> getUserPage(@RequestHeader MultiValueMap<String, String> header, @RequestParam Integer page) {
        return apiService.get(managementServerUri, "?page=" + Integer.toString(page), header);
    }

    @PatchMapping("/users/{userId}")
    @ResponseBody
    public ResponseEntity<byte[]> regist(@RequestHeader MultiValueMap<String, String> header, @RequestBody byte[] body) {
        return apiService.patch(managementServerUri, header, body);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseBody
    public ResponseEntity<byte[]> deleteUser(@RequestHeader MultiValueMap<String, String> header, @RequestBody byte[] body) {
        return apiService.patch(managementServerUri, header, body);
    }
}
