package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.dto.follow.MyFollow;
import com.teamc.moodtracker.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @GetMapping("/myfollow")
    public ResponseEntity<List<MemberDto>> getMyFollow(@AuthenticationPrincipal MemberDto memberDto) {
        // header에 bearer token으로부터 m_id를 얻어오는 코드 작성해줘
        int memberId = memberDto.getM_id();
        return ResponseEntity.ok(followService.getMyFollow(memberId));
    }

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody MyFollow myFollow) {
        if (myFollow.getFollowerId() == myFollow.getFollowedId()) {
            return ResponseEntity.badRequest().body("자기 자신을 팔로우할 수 없습니다.");
        }
        try {
            followService.newFollow(myFollow);
            return ResponseEntity.ok("팔로우가 생성되었습니다.");
        } catch (DuplicateKeyException e) {
            followService.deleteFollow(myFollow);
            return ResponseEntity.ok("팔로우가 해제되었습니다.");
        }
    }
}
