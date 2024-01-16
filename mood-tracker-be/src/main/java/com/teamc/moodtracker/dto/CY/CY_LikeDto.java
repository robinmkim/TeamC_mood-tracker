package com.teamc.moodtracker.dto.CY;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("clvo")
@Getter
@Setter
public class CY_LikeDto {

    private String liketype; // 게시글/댓글/대댓글 좋아요인지 구분
    private String memberid; // 좋아요 누른 사람
    private String targetno; // 게시글/댓글 고유번호
    private int cntLike ; // 좋아요 개수
    private String regdate; // 좋아요 누른 시각

}
