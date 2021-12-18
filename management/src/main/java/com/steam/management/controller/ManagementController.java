package com.steam.management.controller;

import com.steam.management.dto.PatchRoleRequest;
import com.steam.management.dto.UserPageResponse;
import com.steam.management.service.ManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/management")
public class ManagementController {
    private final ManagementService managementService;

    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity<UserPageResponse> getUserPage(@RequestParam Integer page) {
        if(page > 0)
            return ResponseEntity.ok(managementService.getUserPage(page - 1));

        return ResponseEntity.ok(managementService.getUserPage(0));
    }

    @PatchMapping("/users/{userId}")
    @ResponseBody
    public ResponseEntity<String> patchUserRole(@RequestBody PatchRoleRequest request) {
        return ResponseEntity.ok(managementService.patchUserRole(request));
    }

    @DeleteMapping("/user/{userId}")
    @ResponseBody
    public ResponseEntity<String> patchUserRole(@PathVariable("userId") Integer id) {
        return ResponseEntity.ok(managementService.deleteUser(id));
    }
}
