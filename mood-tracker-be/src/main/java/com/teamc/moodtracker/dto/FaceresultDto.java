package com.teamc.moodtracker.dto;

import lombok.Getter;

@Getter
public class FaceresultDto {
    private int resultId;
    private String memberId;
    private String imageFile;
    private String resultset; // {happy:0.675525, sad:0.231122, ...}
}
