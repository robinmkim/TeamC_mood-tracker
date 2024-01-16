package com.teamc.moodtracker.dto.CY;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Alias("cbvo")
@Getter
@Setter
public class CY_BoardDto {
    private int b_id;
    private int m_id;
    private String b_content ;
    private String b_sentiment;
    private String regdate;
    private List<MultipartFile> mediaList;
    private int cnt; // 감정 개수
}
