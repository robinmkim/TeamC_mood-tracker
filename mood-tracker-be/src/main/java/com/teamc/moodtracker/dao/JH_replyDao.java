package com.teamc.moodtracker.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JH_replyDao {
    public int replyCount(int re_cmid);
}
