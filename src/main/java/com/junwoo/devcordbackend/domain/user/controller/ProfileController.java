package com.junwoo.devcordbackend.domain.user.controller;

import com.junwoo.devcordbackend.domain.auth.security.userdetail.DevcordUserDetails;
import com.junwoo.devcordbackend.domain.user.dto.ProfileContextResponse;
import com.junwoo.devcordbackend.domain.user.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 22.
 */
@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{targetUserId}/profile-context")
    public ResponseEntity<ProfileContextResponse> getProfileContext(
            @PathVariable Long targetUserId,
            @AuthenticationPrincipal DevcordUserDetails userDetails
    ) {

        ProfileContextResponse response = profileService.getProfileContext(userDetails.getUser().id(), targetUserId);

        return ResponseEntity.ok(response);
    }
}
