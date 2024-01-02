package com.teamc.moodtracker.service;


import com.teamc.moodtracker.dao.PostDao;
import com.teamc.moodtracker.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostServiceImpl implements PostService{
    private PostDao postDao;

    @Override
    public List<PostDto> getList() {
        return postDao.selectList();
    }

    @Override
    public void writePost(PostDto post) {
        postDao.writePost(post);
    }

    @Override
    public PostDto detailPost(int pno) {
        return postDao.detailPost(pno);
    }

    @Override
    public void updatePost(PostDto post) {
        postDao.updatePost(post);
    }

    @Override
    public void deletePost(int pno) {
        postDao.deletePost(pno);
    }
}
