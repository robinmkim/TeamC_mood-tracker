package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.JH_CommentDto;
import com.teamc.moodtracker.service.JH_CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/jh_comment")
public class JH_CommentController {
    @Autowired
    private JH_CommentService commentService;

    @GetMapping("/allCommentCount")
    public int allCommentCount(int cm_bid) {
        List<JH_CommentDto> comments= getCommentList(cm_bid);
        int replyCount = 0;
        for (JH_CommentDto dto : comments) {
            replyCount += commentService.replyCount(dto.getCm_id());
        }
        return commentService.commentCount(cm_bid)+replyCount;
    };

    @GetMapping("/getCommentList")
    public List<JH_CommentDto> getCommentList(int cm_bid) {
      return commentService.getCommentList(cm_bid);
    };

    @GetMapping("/getCommentListDetail")
    public List<JH_CommentDto> getCommentListDetail(int cm_bid) {

        return commentService.getCommentListDetail(cm_bid);
    };

}
