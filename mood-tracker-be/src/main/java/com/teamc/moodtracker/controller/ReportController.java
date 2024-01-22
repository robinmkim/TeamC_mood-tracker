package com.teamc.moodtracker.controller;

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

    // 신고 게시글
    @GetMapping("/list")
    public List<ReportDto> reportList() {
        System.out.println("들어왔니?" + service.reportList());
        return service.reportList();
    }

    // 신고 게시글에 있는 게시물
    @GetMapping("/get/{report_id}")
    public ReportDto reportDetail(@PathVariable int report_id) {
        return service.reportDetail(report_id);
    }

    // 신고 댓글
    @GetMapping("/comment/list")
    public List<ReportDto> reportCommentList() {
        System.out.println("댓글 목록 호출");
        return service.reportCommentList();
    }

    // 신고 댓글에 있는 댓글
    @GetMapping("/comment/get/{comment_id}")
    public ReportDto reportCommentDetail(@PathVariable int comment_id) {
        return service.reportCommentDetail(comment_id);
    }

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
