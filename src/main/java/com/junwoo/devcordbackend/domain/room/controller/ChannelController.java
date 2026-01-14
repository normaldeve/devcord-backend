package com.junwoo.devcordbackend.domain.room.controller;

import com.junwoo.devcordbackend.domain.auth.dto.AuthDTO;
import com.junwoo.devcordbackend.domain.auth.security.DevcordUserDetails;
import com.junwoo.devcordbackend.domain.room.dto.ChannelResponse;
import com.junwoo.devcordbackend.domain.room.dto.CreateChannelRequest;
import com.junwoo.devcordbackend.domain.room.dto.RenameChannelRequest;
import com.junwoo.devcordbackend.domain.room.dto.ServerChannelResponse;
import com.junwoo.devcordbackend.domain.room.service.ChannelService;
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
 * @date 26. 1. 13.
 */
@Slf4j
@RestController
@RequestMapping("/api/servers/{serverId}/channels")
@RequiredArgsConstructor
@Tag(name = "Channel", description = "채널 관리 API")
public class ChannelController {

    private final ChannelService channelService;

    @Operation(
            summary = "채널 생성",
            description = "서버 내에 텍스트 또는 음성 채널을 생성합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "채널 생성 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403", description = "채널 생성 권한 없음"),
            @ApiResponse(responseCode = "409", description = "중복된 채널 이름"),
            @ApiResponse(responseCode = "404", description = "서버를 찾을 수 없음")
    })
    @PostMapping
    public ResponseEntity<Long> createChannel(
            @PathVariable Long serverId,
            @AuthenticationPrincipal DevcordUserDetails userDetails,
            @RequestBody CreateChannelRequest request
    ) {
        AuthDTO user = userDetails.getUser();

        Long channelId = channelService.createChannel(serverId, user.id(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(channelId);
    }

    @Operation(
            summary = "채널 이름 변경",
            description = "채널 이름을 수정합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "채널 이름 변경 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403", description = "채널 수정 권한 없음"),
            @ApiResponse(responseCode = "404", description = "채널을 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "중복된 채널 이름")
    })
    @PatchMapping("/{channelId}")
    public ResponseEntity<Void> renameChannel(
            @PathVariable Long serverId,
            @PathVariable Long channelId,
            @AuthenticationPrincipal DevcordUserDetails userDetails,
            @RequestBody RenameChannelRequest request
    ) {
        AuthDTO user = userDetails.getUser();

        channelService.renameChannel(serverId, channelId, user.id(), request);

        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "서버 채널 목록 조회",
            description = "특정 서버에 속한 모든 채널(텍스트/음성)을 조회합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "채널 목록 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "403", description = "서버 멤버가 아님"),
            @ApiResponse(responseCode = "404", description = "서버를 찾을 수 없음")
    })
    @GetMapping
    public ResponseEntity<ServerChannelResponse> getChannels(
            @PathVariable Long serverId,
            @AuthenticationPrincipal DevcordUserDetails userDetails
    ) {
        AuthDTO user = userDetails.getUser();

        ServerChannelResponse channels = channelService.getChannels(serverId, user.id());

        return ResponseEntity.ok(channels);
    }
}
