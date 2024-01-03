package com.teamc.moodtracker.dao;


import com.teamc.moodtracker.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostDao {
    public List<PostDto> selectList();
    public void writePost(PostDto post);
    public PostDto detailPost(int pno);
    public void updatePost(PostDto post);
    public void deletePost(int pno);
}
