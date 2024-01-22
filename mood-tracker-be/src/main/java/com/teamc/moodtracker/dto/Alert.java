package com.teamc.moodtracker.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Alert {
    private String type;
    private int m_id_to;
    private int m_id_from;
    private String m_content;
}
