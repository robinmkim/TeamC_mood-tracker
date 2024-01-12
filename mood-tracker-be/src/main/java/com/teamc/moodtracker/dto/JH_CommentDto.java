package com.teamc.moodtracker.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Alias("jh_cvo")
@Getter
@Setter
public class JH_CommentDto {
    private int cm_id;
    private int cm_bid;
    private int cm_mid;
    private  String cm_content;
    private String regdate;
    private List<JH_ReplyDto> replyList;
    private JH_MemberDto member;
}
