package com.junwoo.devcordbackend.domain.user.controller;

import com.junwoo.devcordbackend.domain.auth.security.DevcordUserDetails;
import com.junwoo.devcordbackend.domain.user.dto.FriendRequestResponse;
import com.junwoo.devcordbackend.domain.user.dto.FriendResponse;
import com.junwoo.devcordbackend.domain.user.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 14.
 */
@Slf4j
@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
@Tag(name = "Friend", description = "친구 관리 API")
public class FriendController {

    private final FriendService friendService;

    @Operation(
            summary = "친구 요청 보내기",
            description = "다른 사용자에게 친구 요청을 보냅니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "친구 요청 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "409", description = "이미 친구이거나 요청됨")
    })
    @PostMapping("/request/{receiverId}")
    public ResponseEntity<Void> sendFriendRequest(
            @AuthenticationPrincipal DevcordUserDetails userDetails,
            @PathVariable Long receiverId
    ) {
        Long requesterId = userDetails.getUser().id();

        friendService.sendFriendRequest(requesterId, receiverId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "받은 친구 요청 목록 조회",
            description = "나에게 들어온 친구 요청을 최신순으로 조회합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
    })
    @GetMapping("/requests/received")
    public ResponseEntity<List<FriendRequestResponse>> getReceivedRequests(
            @AuthenticationPrincipal DevcordUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().id();

        List<FriendRequestResponse> responses =
                friendService.getReceivedFriendRequests(userId);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "친구 요청 수락")
    @PatchMapping("/{requesterId}/accept")
    public ResponseEntity<Void> acceptFriend(
            @PathVariable Long requesterId,
            @AuthenticationPrincipal DevcordUserDetails userDetails
    ) {
        friendService.acceptFriendRequest(userDetails.getUser().id(), requesterId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "친구 요청 차단")
    @PatchMapping("/{requesterId}/block")
    public ResponseEntity<Void> blockFriend(
            @PathVariable Long requesterId,
            @AuthenticationPrincipal DevcordUserDetails userDetails
    ) {
        friendService.blockFriendRequest(userDetails.getUser().id(), requesterId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "친구 목록 조회 및 검색",
            description = "내 친구 목록을 조회하거나 닉네임으로 검색합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping
    public ResponseEntity<List<FriendResponse>> getFriends(
            @RequestParam(required = false) String keyword,
            @AuthenticationPrincipal DevcordUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().id();
        return ResponseEntity.ok(
                friendService.getMyFriends(userId, keyword)
        );
    }
}