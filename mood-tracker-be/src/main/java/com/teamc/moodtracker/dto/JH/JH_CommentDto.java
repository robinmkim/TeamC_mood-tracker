package com.teamc.moodtracker.dto.JH;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("jh_cvo")
@Getter
@Setter
public class JH_CommentDto {
    private int cm_id;
    private int b_id;
    private int m_id;
    private  String cm_content;
    private String regdate;
//    private List<JH_ReplyDto> replyList;
    private JH_MemberDto member;


    private int reply_count;  // 리플 count

    private boolean isMyLike;
    public void setIsMyLike(boolean isMyLike) {
        this.isMyLike = isMyLike;
    }
    public int likeCount;
}
