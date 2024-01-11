package com.teamc.moodtracker.dto;

import lombok.Getter;

@Getter
public class FaceresultDto {
    private int ar_id; // 분석결과_id
    private int ar_member_num; // 회원_num
    private String ar_origin_img; // 원본_이미지 파일이름
    private String ar_generated_img; // 분석결과_합성_이미지 파일이름
    private String ar_content; // 분석결과_내용
    private String ar_content_max; // 분석결과_내용 중 가장 확률이 높은 항목
    private String ar_analyze_date; // 분석_날짜


}
