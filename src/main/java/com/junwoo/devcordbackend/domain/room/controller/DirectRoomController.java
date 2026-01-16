package com.junwoo.devcordbackend.domain.room.controller;

import com.junwoo.devcordbackend.domain.auth.security.userdetail.DevcordUserDetails;
import com.junwoo.devcordbackend.domain.room.dto.DirectRoomListResponse;
import com.junwoo.devcordbackend.domain.room.service.DirectRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 15.
 */
@RestController
@RequestMapping("/api/direct")
@RequiredArgsConstructor
public class DirectRoomController {

    private final DirectRoomService directRoomService;

    @PostMapping("/{toUserId}")
    public ResponseEntity<Long> create(
            @AuthenticationPrincipal DevcordUserDetails userDetails,
            @PathVariable Long toUserId
            ) {

        Long myId = userDetails.getUser().id();

        Long roomId = directRoomService.createDirectRoom(myId, toUserId);

        return ResponseEntity.ok(roomId);

    }

    @GetMapping
    public ResponseEntity<List<DirectRoomListResponse>> getMyDirectRooms(
            @AuthenticationPrincipal DevcordUserDetails userDetails
    ) {
        Long myId = userDetails.getUser().id();

        List<DirectRoomListResponse> response = directRoomService.getMyDirectRooms(myId);

        return ResponseEntity.ok(response);
    }
}