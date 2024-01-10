package com.teamc.moodtracker.dto;

import lombok.Getter;

@Getter
public class MemberDto {
    private int memberNum; //회원번호
    private String  memberid; // 아이디
    private String password;
    private String name;
    private String email;
    private String birth;
    private String gender;
    private String profile; //프로필이미지
    private String bio;     //바이오
    private String banstate; // 정지상태
    private String banstart; // 정지 시작일
    private String banend;   // 정지 종료일
}
