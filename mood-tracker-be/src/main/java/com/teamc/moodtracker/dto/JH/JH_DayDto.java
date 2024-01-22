package com.teamc.moodtracker.dto.JH;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Alias("jh_day")
@Getter
@Setter
public class JH_DayDto {
    private int year;
    private int month;
    private int firstDay;
    private int lastDate;

}
