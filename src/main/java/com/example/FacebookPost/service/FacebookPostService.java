package com.example.FacebookPost.service;

import com.example.FacebookPost.dto.*;
import com.example.FacebookPost.entity.Comments;
import com.example.FacebookPost.entity.FacebookPost;

import java.util.List;
import java.util.Optional;

/**
 * @author meghna.bajoria
 * @since 10/03/21 11:10 AM
 **/
public interface FacebookPostService {

    FacebookResponseDto uploadFacebookPost(FacebookRequestDto facebookRequestDto,String userName);
    String deleteFacebookPost(String postId);
//    Optional<FacebookPost> getFacebookPost(String postId,String userName);
    List<FacebookPost> getFacebookPostByUserName (String userName);
    FacebookResponseDto updateFacebookPostByPostId(FacebookRequestDto facebookRequestDto,String postId,String userName);
    String likeDislike(LikeDislikeRequestDto likeDislikeRequestDto);
    FacebookPost getFacebookPostById(String postId,String userName);
     CommentsRequestDto createComment( CommentsRequestDto commentsRequestDto);


}