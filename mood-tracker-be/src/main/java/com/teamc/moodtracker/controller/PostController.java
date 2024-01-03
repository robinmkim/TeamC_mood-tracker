package com.teamc.moodtracker.controller;

import com.teamc.moodtracker.dto.PostDto;
import com.teamc.moodtracker.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    public List<PostDto> postList(){
        return postService.getList();
    }

    @GetMapping("/write")
    public void writePost(PostDto post){
        postService.writePost(post);
    }

    @GetMapping("/detail")
    public PostDto postDetail(int pno){
        return postService.detailPost(pno);
    }

}
