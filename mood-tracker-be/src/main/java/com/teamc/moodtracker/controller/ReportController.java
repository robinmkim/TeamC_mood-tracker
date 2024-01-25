package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.dto.ReportDto;
import com.teamc.moodtracker.service.ReportService;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService service;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/add")
    public int addReport(
            @AuthenticationPrincipal MemberDto memberDto,
            @RequestBody ReportDto dto) {
        dto.setM_id(memberDto.getM_id());
        // 여기에 필요한 로직 추가
        // memberDto, dto, someParam 등을 사용하여 작업 수행
        System.out.println(dto.toString());
        service.addReport(dto);
        return 1;
    }

}
