package com.steam.auth.controller;

import com.steam.auth.dto.LoginRequest;
import com.steam.auth.dto.RegistRequest;
import com.steam.auth.dto.Token;
import com.steam.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Token> login(@RequestBody LoginRequest request) {
        System.out.println(request.getEmail());
        System.out.println(request.getPassword());
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/regist")
    @ResponseBody
    public ResponseEntity regist(@RequestBody RegistRequest request) {

        return ResponseEntity.ok(authService.regist(request));
    }

    @PostMapping("/email-check")
    @ResponseBody
    public ResponseEntity isDuplicated(@RequestParam String email) {

        return ResponseEntity.ok(authService.isDuplicated(email));
    }
}
