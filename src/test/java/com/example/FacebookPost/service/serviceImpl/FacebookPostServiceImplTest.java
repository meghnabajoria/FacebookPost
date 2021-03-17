package com.example.FacebookPost.service.serviceImpl;

import com.example.FacebookPost.dto.FacebookRequestDto;
import com.example.FacebookPost.dto.FacebookResponseDto;
import com.example.FacebookPost.entity.Comments;
import com.example.FacebookPost.entity.FacebookPost;
import com.example.FacebookPost.entity.User;
import com.example.FacebookPost.repository.FacebookPostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author meghna.bajoria
 * @since 17/03/21 2:42 PM
 **/
@RunWith(MockitoJUnitRunner.class)
class FacebookPostServiceImplTest {


    @InjectMocks
    private FacebookPostServiceImpl facebookPostService;


    @Mock
    private FacebookPostRepository facebookPostRepository;


    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void uploadFacebookPost() {
        FacebookPost facebookPost=new FacebookPost();
        facebookPost.setDislike(0);
        facebookPost.setLike(0);
        facebookPost.setLocation("Varanasi");
        facebookPost.setUserName("Prateek");
        List<String> postImagesList=new ArrayList<>();
        facebookPost.setPostImages(postImagesList);
        facebookPost.setPostCaption("Hello Prateek");
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime());
        facebookPost.setDate(timeStamp);
        User user=new User();
        user.setLikeDislikeUserName("Meghna");
        user.setLikeDislikeFullName("Bajoria");
        user.setLikeDislikeProfileUrl("hwhw");
        List<User> userList=new ArrayList<>();
        userList.add(user);
        facebookPost.setPostLikedList(userList);
        facebookPost.setPostLikedList(userList);
        facebookPost.setPostId("id1");
        Comments comments=new Comments();
        comments.setCommentedBy("Meghna");
        comments.setCommentText("Bad Pic");
        List<Comments> commentsList=new ArrayList<>();
        commentsList.add(comments);
        facebookPost.setCommentList(commentsList);
        //FacebookPost facebookPost1=facebookPostRepository.save(facebookPost);
       // Mockito.when(facebookPostRepository.save(facebookPost)).thenReturn(facebookPost);
        FacebookRequestDto facebookRequestDto=new FacebookRequestDto();
        facebookRequestDto.setLocation("Varanasi");
        facebookRequestDto.setPostCaption("bad pic");
        facebookRequestDto.setPostImages(postImagesList);
        FacebookResponseDto facebookResponseDto=new FacebookResponseDto();
        facebookResponseDto.setPostId("id1");
        facebookResponseDto.setMessage("SuceessFul");
        facebookResponseDto.setUserName("Prateek");
        Mockito.when(facebookPostService.uploadFacebookPost(facebookRequestDto,"Prateek")).thenReturn(facebookResponseDto);
       assertEquals(facebookPostService.uploadFacebookPost(facebookRequestDto,"Prateek"),facebookResponseDto);

    }

    @Test
    void updateFacebookPostByPostId() {
    }

    @Test
    void deleteFacebookPost() {
    }

    @Test
    void getFacebookPostByUserName() {

    }

    @Test
    void likeDislike() {



    }

    @Test
    void getFacebookPostById() {
    }

    @Test
    void createComment() {
    }
}