package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.dto.follow.FollowCount;
import com.teamc.moodtracker.dto.follow.FollowRequestDto;
import com.teamc.moodtracker.dto.follow.MyFollow;
import com.teamc.moodtracker.service.FollowService;
import com.teamc.moodtracker.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @Autowired
    private final NotificationService notificationService;

    @PostMapping("")
    public ResponseEntity<String> makeFollow(@AuthenticationPrincipal MemberDto memberDto,
            @RequestBody Map<String, Integer> followedInfo) {
        int followerId = memberDto.getM_id();
        int followedId = followedInfo.get("followedId");
        if (followerId == followedId) {
            return ResponseEntity.badRequest().body("자기 자신을 팔로우할 수 없습니다.");
        }
        FollowRequestDto followRequestDTO = FollowRequestDto.builder()
                .followerId(followerId)
                .followedId(followedId)
                .build();
        try {
            followService.makeFollow(followRequestDTO);
            notificationService.makeFollow_SaveNotificationAndSendAlert(followRequestDTO);
            return ResponseEntity.ok("Follow Success");
        } catch (DuplicateKeyException e) {
            followService.deleteFollow(followRequestDTO);
            return ResponseEntity.ok("Follow Cancel");
        }
    }

    @GetMapping("/myfollow")
    public ResponseEntity<List<MemberDto>> getMyFollow(@AuthenticationPrincipal MemberDto memberDto) {
        int memberId = memberDto.getM_id();
        return ResponseEntity.ok(followService.getMyFollow(memberId));
    }

    @GetMapping("/followcnt/{memberId}")
    public ResponseEntity<FollowCount> getFollowCnt(@PathVariable("memberId") int memberId) {
        return ResponseEntity.ok(followService.getFollowCnt(memberId));
    }

    @GetMapping("/check/{memberId}")
    public ResponseEntity<String> checkFollow(@AuthenticationPrincipal MemberDto memberDto,
            @PathVariable("memberId") int followedId) {
        System.out.println("followedId: " + followedId);
        int followerId = memberDto.getM_id();
        FollowRequestDto followRequestDTO = FollowRequestDto.builder()
                .followerId(followerId)
                .followedId(followedId)
                .build();
        return ResponseEntity.ok(followService.checkFollow(followRequestDTO));
    }

    @GetMapping("/follower/{followerId}")
    public ResponseEntity<List<MemberDto>> FollowerId(@PathVariable("followerId") int followedId) {
        List<MemberDto> followers = followService.FollowerId(followedId);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/followed/{followedId}")
    public ResponseEntity<List<MemberDto>> FolloweredId(@PathVariable("followedId") int followeredId) {
        List<MemberDto> followers = followService.FolloweredId(followeredId);
        return ResponseEntity.ok(followers);
    }
}
