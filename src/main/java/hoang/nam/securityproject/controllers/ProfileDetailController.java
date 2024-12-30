/**
 * @ (#) ProfileController.java 1.0 27-Dec-24
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved.
 */
package hoang.nam.securityproject.controllers;

import hoang.nam.securityproject.dtos.ApiResponse;
import hoang.nam.securityproject.dtos.requests.RegistrationRequest;
import hoang.nam.securityproject.dtos.responses.ProfileResponse;
import hoang.nam.securityproject.services.ProfileService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Le Hoang Nam
 * @date: 27-Dec-24
 * @version: 1.0
 */
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ProfileDetailController {

    @Autowired
    ProfileService profileService;

    @PostMapping("/register")
    ApiResponse<ProfileResponse> register(@RequestBody @Valid RegistrationRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.register(request))
                .build();
    }

    @GetMapping("/profiles")
    ApiResponse<List<ProfileResponse>> getProfiles() {
        return ApiResponse.<List<ProfileResponse>>builder()
                .result(profileService.getAllProfiles())
                .build();
    }

    @GetMapping("/my-profile")
    ApiResponse<ProfileResponse> getMyProfile() {
        log.info(String.valueOf(profileService.getMyProfile()));
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.getMyProfile())
                .build();
    }
}
