package com.teamc.moodtracker.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("rvo")
@Getter
@Setter
public class ReplyDto { // 대댓글 테이블
    private int re_id; // 대댓글 고유번호
    private int cm_id; // 댓글 고유번호
    private int m_id;
    private  String re_content;
    private String regdate;
    private MemberDto member;
    private boolean isMyLike;
    public void setIsMyLike(boolean isMyLike) {
        this.isMyLike = isMyLike;
    }

    private int likeCount;
}
