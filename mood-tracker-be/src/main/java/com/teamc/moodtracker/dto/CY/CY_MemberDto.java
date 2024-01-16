package com.teamc.moodtracker.dto.CY;

import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Alias("cmvo")
@Getter
public class CY_MemberDto {
    private int m_id; //회원번호
    private String m_handle; // 아이디
    private String m_pwd;
    private String m_name;
    private String m_email;
    private String m_bdate;
    private String m_gender;
    private String m_img_name; //프로필이미지
    private String m_img_path; // 이미지 경로
    private String m_bio;     //바이오
    private String banstate; // 정지상태
    private String banstart; // 정지 시작일
    private String banend;   // 정지 종료일
}
