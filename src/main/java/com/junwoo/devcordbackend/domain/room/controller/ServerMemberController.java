package com.junwoo.devcordbackend.domain.room.controller;

import com.junwoo.devcordbackend.domain.auth.security.userdetail.DevcordUserDetails;
import com.junwoo.devcordbackend.domain.room.dto.ServerMemberResponse;
import com.junwoo.devcordbackend.domain.room.service.ServerMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
@Slf4j
@RestController
@RequestMapping("/api/servers/{serverId}/members")
@RequiredArgsConstructor
@Tag(name = "Server Member", description = "서버 멤버 조회 API")
public class ServerMemberController {

    private final ServerMemberService serverMemberService;

    @Operation(
            summary = "서버 멤버 목록 조회",
            description = "해당 서버에 참여 중인 멤버 목록을 조회합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403", description = "서버 멤버가 아님"),
            @ApiResponse(responseCode = "404", description = "서버 없음")
    })
    @GetMapping
    public ResponseEntity<List<ServerMemberResponse>> getServerMembers(
            @PathVariable Long serverId,
            @AuthenticationPrincipal DevcordUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().id();

        return ResponseEntity.ok(
                serverMemberService.getServerMembers(serverId, userId)
        );
    }
}
