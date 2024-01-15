package com.teamc.moodtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamc.moodtracker.dto.CY_BoardDto;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.CY_BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/mypage")
public class CY_BoardController {
    @Autowired
    private CY_BoardService cboardService;

    @GetMapping("/sentiment")
    @ResponseBody
    public List<Map<String, Object>> getSentimentData(int mid) {
        System.out.println("-----------controller getSenti-----------");
        System.out.println(cboardService.getSentiment(mid));

        return cboardService.getSentiment(mid);
    }

    @GetMapping("/mylist")
    public List<Integer> getMyBoardList(@RequestParam(value = "lastRowNum") int lastRowNum,
                                        @AuthenticationPrincipal MemberDto memberDto) {
        int mid = memberDto.getM_id();
        System.out.println("-------controller get boardlist-----");
        System.out.println("rownum:" + lastRowNum + "mid:" + mid);
        return cboardService.getMyBoardList(lastRowNum, mid);
    }

    @GetMapping("/likelist")
    public List<Integer> getLikedBoardList(@RequestParam(value = "lastRowNum") int lastRowNum,
                                           @AuthenticationPrincipal MemberDto memberDto) {
        int mid = memberDto.getM_id();
        System.out.println("-------controller get board like list-----");
        System.out.println("rownum:" + lastRowNum + "mid:" + mid);
        return cboardService.getLikedBoardList(lastRowNum, mid);
    }

    @GetMapping("/reglist")
    public List<Integer> getListByDate(@RequestParam(value = "regdate") String regdate,
                                       @AuthenticationPrincipal MemberDto memberDto) {
        int mid = memberDto.getM_id();
        System.out.println("-------controller get list by regdate-----");
        System.out.println("regdate:" + regdate  + "mid:" + mid);
        return cboardService.getListByDate(regdate, mid);
    }

}