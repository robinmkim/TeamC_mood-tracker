package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.BoardDto;
import com.teamc.moodtracker.dto.JH.JH_DayDto;
import com.teamc.moodtracker.dto.MemberDto;
import com.teamc.moodtracker.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/mypage")
public class MyPageController {
    @Autowired
    private MyPageService service;

    @GetMapping("/sentiment")
    @ResponseBody
    public List<Map<String, Object>> getSentimentData(@RequestParam("m_id")int m_id) {
        return service.getSentiment(m_id);
    }

    @GetMapping("/mylist")
    public List<Integer> getMyBoardList(@RequestParam(value = "lastRowNum") int lastRowNum,
            @RequestParam(value = "mid") int mid) {
        return service.getMyBoardList(lastRowNum, mid);
    }

    @GetMapping("/likelist")
    public List<Integer> getLikedBoardList(@RequestParam(value = "lastRowNum") int lastRowNum,
            @RequestParam("m_id")int m_id) {
        return service.getLikedBoardList(lastRowNum, m_id);
    }

    @GetMapping("/reglist")
    public List<Integer> getListByDate(@RequestParam(value = "regdate") String regdate, @RequestParam(value = "m_id")int m_id) {
        return service.getListByDate(regdate, m_id);
    }

    @GetMapping("/const")
    public Integer getConsecPostsCnt(@RequestParam("m_id")int m_id) {
        return service.getConsecPostsCnt(m_id);
    }

    @GetMapping("/postcnt")
    public Integer getThisMonthPosts(@RequestParam("m_id")int m_id)  {
        return service.getThisMonthPosts(m_id);
    }

    @GetMapping("/mainsenti")
    public String getMainSentiment(@RequestParam("m_id")int m_id) {
        return service.getMainSentiment(m_id);
    }

    // 검색창 검색 기능입니다. 주의하세요
    @GetMapping("/search")
    public List<BoardDto> SearchBoard(@RequestParam String boardContent,
            @AuthenticationPrincipal MemberDto memberDto) {
        List<BoardDto> searchBoard = service.SearchBoard(boardContent);
        return searchBoard;
    }

    // 달력 감정
    @GetMapping("/getTopSentiment")
    public List<String> getTopSentiment(@ModelAttribute JH_DayDto dto, @RequestParam(value = "m_id") int m_id) {
        List<String> list = new ArrayList<String>();
        int i = 0;
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDate.format(formatter);
        int toDay = Integer.parseInt(formattedDate);
        for (i = dto.getFirstDay(); i <= dto.getLastDate(); i++) {
            String regdate = String.format("%04d%02d%02d", dto.getYear(), dto.getMonth(), i);
            int intReg = Integer.parseInt(regdate);
            String sent = service.getTopSentiment(regdate, m_id);
            if (sent == null) {
                if (intReg <= toDay) {
                    list.add("nullPostDayImage");
                } else {
                    list.add(null);
                }
            } else {
                list.add("Calendar_" + sent);
            }
        }
        return list;
    }
}
