package com.teamc.moodtracker.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("cvo")
@Getter
@Setter
public class CommentDto {
    private int cm_id;
    private int b_id;
    private int m_id;
    private  String cm_content;
    private String regdate;
    //    private List<JH_ReplyDto> replyList;
    private MemberDto member;


    private int reply_count;  // 리플 count

    private boolean isMyLike;
    public void setIsMyLike(boolean isMyLike) {
        this.isMyLike = isMyLike;
    }
    public int likeCount;
}
