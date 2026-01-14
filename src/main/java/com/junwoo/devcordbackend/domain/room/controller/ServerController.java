package com.junwoo.devcordbackend.domain.room.controller;

import com.junwoo.devcordbackend.domain.auth.dto.AuthDTO;
import com.junwoo.devcordbackend.domain.auth.security.DevcordUserDetails;
import com.junwoo.devcordbackend.domain.room.dto.CreateServerRequest;
import com.junwoo.devcordbackend.domain.room.dto.ServerResponse;
import com.junwoo.devcordbackend.domain.room.service.ServerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
@Slf4j
@RestController
@RequestMapping("/api/servers")
@RequiredArgsConstructor
@Tag(name = "Server", description = "서버 관리 API")
public class ServerController {

    private final ServerService serverService;

    @Operation(
            summary = "서버 생성",
            description = "새로운 서버를 생성하고 기본 텍스트/보이스 채널을 자동으로 생성합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서버 생성 성공"),
            @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping
    public ResponseEntity<Long> createServer(
            @RequestBody CreateServerRequest request,
            @AuthenticationPrincipal DevcordUserDetails userDetails
    ) {

        AuthDTO user = userDetails.getUser();

        Long response = serverService.createServer(user.id(), request);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "내 서버 목록 조회",
            description = "로그인한 사용자가 참여 중인 서버 목록을 조회합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "서버 목록 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403", description = "접근 권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/me")
    public ResponseEntity<List<ServerResponse>> getMyServers(
            @AuthenticationPrincipal DevcordUserDetails userDetails
    ) {

        AuthDTO user = userDetails.getUser();

        List<ServerResponse> response = serverService.getMyServers(user.id());

        return ResponseEntity.ok(response);

    }
}
