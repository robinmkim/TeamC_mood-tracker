package com.teamc.moodtracker.service;


import com.teamc.moodtracker.dto.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    public List<PostDto> getList();
    public void writePost(PostDto post);
    public PostDto detailPost(int pno);
    public void updatePost(PostDto post);
    public void deletePost(int pno);

}
