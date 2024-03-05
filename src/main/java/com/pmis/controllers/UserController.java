package com.pmis.controllers;

import com.pmis.annotation.CurrentUser;
import com.pmis.payload.response.UserInfoResponse;
import com.pmis.payload.response.service.ExecServiceResponse;
import com.pmis.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("isAuthenticated()")
public class UserController {
    @GetMapping("/me")
    public ResponseEntity<ExecServiceResponse> getUserInfo(@CurrentUser UserDetailsImpl userDetails) {
        try {
            List<String> roles = userDetails.getAuthorities()
                    .stream().map(GrantedAuthority::getAuthority).toList();
            return ResponseEntity.ok(
                    new ExecServiceResponse(
                            new UserInfoResponse(
                                    userDetails.getUserId(),
                                    userDetails.getUsername(),
                                    userDetails.getDescript(),
                                    roles,
                                    userDetails.getFgrant(),
                                    1,
                                    ""
                            ),
                            1,
                            ""
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ExecServiceResponse(null, 0, "Không lấy được thông tin người dùng."));
        }
    }
}
