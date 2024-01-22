package com.teamc.moodtracker.dto;

import java.util.Date;

import lombok.ToString;
import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("report")
@Getter
@Setter
@ToString
public class ReportDto {
    private int report_id; // 신고 id
    private int b_c_id; // 신고게시물
    private String report_type; // 신고종류
    private String regdate; // 신고날짜
    private int m_id; // 신고한 사람 번호
    private int r_type; // 신고 타입

    public boolean isVisible() {
        // r_type이 0이면 게시글에 들어가고, 1이면 댓글에 들어갈 것
        return r_type == 0 || r_type == 1;
    }

    public String getType() {
        return this.report_type;
    }
}
