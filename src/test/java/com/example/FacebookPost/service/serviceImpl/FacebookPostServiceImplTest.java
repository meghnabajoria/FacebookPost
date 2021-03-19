package com.example.FacebookPost.service.serviceImpl;

import com.example.FacebookPost.dto.CommentsRequestDto;
import com.example.FacebookPost.dto.FacebookRequestDto;
import com.example.FacebookPost.dto.FacebookResponseDto;
import com.example.FacebookPost.dto.LikeDislikeRequestDto;
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
import org.springframework.data.domain.Sort;

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
        facebookPost.setUserName("Priyank");
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
        facebookPost.setPostId("Priyank"+timeStamp);
        Comments comments=new Comments();
        comments.setCommentedBy("Meghna");
        comments.setCommentText("Bad Pic");
        List<Comments> commentsList=new ArrayList<>();
        commentsList.add(comments);
        facebookPost.setCommentList(commentsList);
        //FacebookPost facebookPost1=facebookPostRepository.save(facebookPost);
        Mockito.when(facebookPostRepository.save(facebookPost)).thenReturn(facebookPost);
        FacebookRequestDto facebookRequestDto=new FacebookRequestDto();
        facebookRequestDto.setLocation("Varanasi");
        facebookRequestDto.setPostCaption("bad pic");
        facebookRequestDto.setPostImages(postImagesList);
        FacebookResponseDto facebookResponseDto=new FacebookResponseDto();
        facebookResponseDto.setPostId("Priyank"+timeStamp);
        facebookResponseDto.setMessage("Uploaded Successfully");
        facebookResponseDto.setUserName("Priyank");
        //Mockito.when(facebookPostService.uploadFacebookPost(facebookRequestDto,"Prateek")).thenReturn(facebookResponseDto);

        FacebookResponseDto facebookResponseDto1 = facebookPostService.uploadFacebookPost(facebookRequestDto,"Priyank");
        assertEquals(facebookResponseDto1.getMessage(),facebookResponseDto.getMessage());
        assertEquals(facebookResponseDto1.getPostId(),facebookResponseDto.getPostId());
        assertEquals(facebookResponseDto1.getUserName(),facebookResponseDto.getUserName());

    }

    @Test
    void updateFacebookPostByPostId() {
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
        Mockito.when(facebookPostRepository.findByPostId("id1")).thenReturn(facebookPost);
        FacebookResponseDto facebookResponseDto=new FacebookResponseDto();
        facebookResponseDto.setPostId("id1");
        facebookResponseDto.setMessage("Update SuccessFully");
        facebookResponseDto.setUserName("Prateek");
        FacebookRequestDto facebookRequestDto=new FacebookRequestDto();
        facebookRequestDto.setLocation("Varanasi");
        facebookRequestDto.setPostCaption("bad pic");
        facebookRequestDto.setPostImages(postImagesList);
        FacebookResponseDto facebookResponseDto1 = facebookPostService.updateFacebookPostByPostId(facebookRequestDto,"id1","Prateek");

        assertEquals(facebookResponseDto1.getUserName(),facebookResponseDto.getUserName());
        assertEquals(facebookResponseDto1.getPostId(),facebookResponseDto.getPostId());
        assertEquals(facebookResponseDto1.getMessage(),facebookResponseDto.getMessage());


    }

    @Test
    void deleteFacebookPost() {
        Mockito.doNothing().when(facebookPostRepository).deleteById("id1");
        assertEquals(facebookPostService.deleteFacebookPost("id1"),"deleted successfully");
    }

    @Test
    void getFacebookPostByUserName() {
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
        Sort sort = Sort.by(Sort.Direction.DESC, "date");
        List<FacebookPost> flist = new ArrayList<>();
        flist.add(facebookPost);
        Mockito.when(facebookPostRepository.getFacebookPostByUserName("Prateek", sort)).thenReturn(flist);
        List<FacebookPost> fflist = new ArrayList<>();
        fflist= facebookPostService.getFacebookPostByUserName("Prateek");
        assertEquals(fflist.size(),flist.size());

    }

    @Test
    void likeDislike() {
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

        LikeDislikeRequestDto likeDislikeRequestDto = new LikeDislikeRequestDto();
        likeDislikeRequestDto.setLike(1);
        likeDislikeRequestDto.setDislike(0);
        likeDislikeRequestDto.setPostId("id1");
        likeDislikeRequestDto.setUserName("Prateek");
        likeDislikeRequestDto.setProfilepic("profilepic");

        LikeDislikeRequestDto likeDislikeRequestDto1 = new LikeDislikeRequestDto();
        likeDislikeRequestDto1.setLike(0);
        likeDislikeRequestDto1.setDislike(0);
        likeDislikeRequestDto1.setPostId("id1");
        likeDislikeRequestDto1.setUserName("Prateek");
        likeDislikeRequestDto1.setProfilepic("profilepic");

        LikeDislikeRequestDto likeDislikeRequestDto2 = new LikeDislikeRequestDto();
        likeDislikeRequestDto2.setLike(3);
        likeDislikeRequestDto2.setDislike(0);
        likeDislikeRequestDto2.setPostId("id1");
        likeDislikeRequestDto2.setUserName("Prateek");
        likeDislikeRequestDto2.setProfilepic("profilepic");

        Mockito.when(facebookPostRepository.findByPostId("id1")).thenReturn(facebookPost);

        assertEquals(facebookPostService.likeDislike(likeDislikeRequestDto),"SuccessFul");
        assertEquals(facebookPostService.likeDislike(likeDislikeRequestDto1),"SuccessFul");
        assertEquals(facebookPostService.likeDislike(likeDislikeRequestDto2),"Unsuccessful");

        FacebookPost facebookPost1=new FacebookPost();
        facebookPost1.setDislike(0);
        facebookPost1.setLike(0);
        facebookPost1.setLocation("Aligarh");
        facebookPost1.setUserName("Priyank");
        List<String> postImagesList1=new ArrayList<>();
        facebookPost1.setPostImages(postImagesList1);
        facebookPost1.setPostCaption("Good Boi");
        facebookPost.setDate(timeStamp);
        User user1=new User();
        user1.setLikeDislikeUserName("Ashish");
        user1.setLikeDislikeFullName("Verma");
        user1.setLikeDislikeProfileUrl("jhsdkja");
//        List<User> userList1=new ArrayList<>();
        facebookPost.setPostId("id3");
        Comments comments1=new Comments();
        comments1.setCommentedBy("Ashish");
        comments1.setCommentText("Worst thing I've ever seen");
        List<Comments> commentsList1=new ArrayList<>();
        commentsList1.add(comments1);
        facebookPost.setCommentList(commentsList1);

        LikeDislikeRequestDto likeDislikeRequestDto3 = new LikeDislikeRequestDto();
        likeDislikeRequestDto3.setLike(0);
        likeDislikeRequestDto3.setDislike(0);
        likeDislikeRequestDto3.setPostId("id3");
        likeDislikeRequestDto3.setUserName("Priyank");
        likeDislikeRequestDto3.setProfilepic("profilepic");

        Mockito.when(facebookPostRepository.findByPostId("id3")).thenReturn(facebookPost1);
        assertEquals(facebookPostService.likeDislike(likeDislikeRequestDto3),"SuccessFul");


    }

    @Test
    void getFacebookPostById() {
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
        Mockito.when(facebookPostRepository.findByPostId("id1")).thenReturn(facebookPost);

        assertEquals(facebookPostService.getFacebookPostById("id1","Prateek"),facebookPost);
        assertEquals(facebookPostService.getFacebookPostById("id1","Prateek1"),null);
    }

    @Test
    void createComment() {
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
        Mockito.when(facebookPostRepository.findByPostId("id1")).thenReturn(facebookPost);
        CommentsRequestDto commentsRequestDto = new CommentsRequestDto();
        commentsRequestDto.setPostId("id1");
        commentsRequestDto.setCommentText("Lit");
        commentsRequestDto.setCommentedBy("Priyank");
        CommentsRequestDto commentsRequestDto1 = facebookPostService.createComment(commentsRequestDto);

        assertEquals(commentsRequestDto1.getPostId(),commentsRequestDto.getPostId());
        assertEquals(commentsRequestDto1.getCommentedBy(),commentsRequestDto.getCommentedBy());
        assertEquals(commentsRequestDto1.getCommentText(),commentsRequestDto.getCommentText());

        FacebookPost facebookPost1=new FacebookPost();
        facebookPost1.setDislike(0);
        facebookPost1.setLike(0);
        facebookPost1.setLocation("Aligarh");
        facebookPost1.setUserName("Priyank");
        List<String> postImagesList1=new ArrayList<>();
        facebookPost1.setPostImages(postImagesList1);
        facebookPost1.setPostCaption("Hello Priyank");
        //String timeStamp1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime());
        facebookPost1.setDate(timeStamp);
        User user1=new User();
        user1.setLikeDislikeUserName("Ashish");
        user1.setLikeDislikeFullName("Verma");
        user1.setLikeDislikeProfileUrl("hwhw");
        List<User> userList1=new ArrayList<>();
        userList1.add(user);
        facebookPost1.setPostLikedList(userList);
        facebookPost1.setPostLikedList(userList);
        facebookPost1.setPostId("id3");
        Comments comments1=new Comments();
        comments1.setCommentedBy("Ashish");
        comments1.setCommentText("Worst Pic");
//        List<Comments> commentsList1=new ArrayList<>();
//        commentsList1.add(comments1);
//        facebookPost1.setCommentList(commentsList1);
        CommentsRequestDto commentsRequestDto2 = new CommentsRequestDto();
        commentsRequestDto2.setPostId("id3");
        commentsRequestDto2.setCommentText("Loser");
        commentsRequestDto2.setCommentedBy("Ashish");
        //CommentsRequestDto commentsRequestDto1 = facebookPostService.createComment(commentsRequestDto);

        Mockito.when(facebookPostRepository.findByPostId("id3")).thenReturn(facebookPost1);

        assertEquals(facebookPostService.createComment(commentsRequestDto2),commentsRequestDto2);

    }
}