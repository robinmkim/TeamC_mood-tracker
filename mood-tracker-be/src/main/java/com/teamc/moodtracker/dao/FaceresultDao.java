package com.teamc.moodtracker.dao;

import com.teamc.moodtracker.dto.FaceresultDto;
import com.teamc.moodtracker.dto.PostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FaceresultDao {
    // 입력
    public void insertFaceResult(FaceresultDto faceresultDto);

    // 조회
    public List<FaceresultDto> selectList();
    public List<FaceresultDto> selectMyList(int memberNum);
    public int selectIdOfMyLastFaceResult(int memberNum);
    public FaceresultDto selectFaceResultDetail(int ar_id);

    // 삭제
    public void deleteFaceResult(int resultId);


//    public List<PostDto> selectList();
//    public void writePost(PostDto post);
//    public PostDto detailPost(int pno);
//    public void updatePost(PostDto post);
//    public void deletePost(int pno);
}
