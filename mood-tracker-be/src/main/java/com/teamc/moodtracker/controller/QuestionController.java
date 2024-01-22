package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.dto.QuestionDto;
import com.teamc.moodtracker.service.QuestionService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService service;

    @PostMapping("/add")
    public void addBoardContent(@AuthenticationPrincipal MemberDto memberDto,
            @RequestBody QuestionDto dtod) {
        // 유저정보
        dtod.setMemberDto(memberDto);
        // 질문,내용정보
        service.addQuestionContent(dtod);

        // 성공 시 적절한 상태 코드 반환
        // return service; // 예시: 성공 시 1 반환
    }
}
