package com.junwoo.devcordbackend.domain.user.controller;

import com.junwoo.devcordbackend.domain.auth.security.userdetail.DevcordUserDetails;
import com.junwoo.devcordbackend.domain.user.dto.SignupRequest;
import com.junwoo.devcordbackend.domain.user.dto.SignupResponse;
import com.junwoo.devcordbackend.domain.user.dto.UserSearchResponse;
import com.junwoo.devcordbackend.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "회원 가입", description = "새로운 사용자를 등록합니다")
    public ResponseEntity<SignupResponse> signup(
            @Validated @RequestBody SignupRequest request
    ) {

        SignupResponse response = userService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "전체 사용자 조회 및 검색",
            description = "닉네임 기준으로 사용자를 검색하며 가나다순으로 정렬됩니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @GetMapping
    public ResponseEntity<List<UserSearchResponse>> searchUsers(
            @AuthenticationPrincipal DevcordUserDetails userDetails,
            @RequestParam(required = false) String keyword
    ) {
        Long myId = userDetails.getUser().id();

        List<UserSearchResponse> result = userService.searchUsers(myId, keyword);

        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "프로필 이미지 변경",
            description = "사용자의 프로필 이미지를 변경합니다. Multipart 형식으로 이미지 파일을 업로드합니다."
    )
    @PatchMapping(value = "/me/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProfileImage(
            @AuthenticationPrincipal DevcordUserDetails userDetails,
            @RequestPart("file") MultipartFile file
    ) {
        Long userId = userDetails.getUser().id();
        String newImageUrl = userService.updateProfile(userId, file);

        return ResponseEntity.ok(newImageUrl);
    }
}