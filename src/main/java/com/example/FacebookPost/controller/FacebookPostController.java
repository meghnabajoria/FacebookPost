package com.example.FacebookPost.controller;

import com.example.FacebookPost.dto.*;
import com.example.FacebookPost.entity.Comments;
import com.example.FacebookPost.entity.FacebookPost;
import com.example.FacebookPost.entity.Login;
import com.example.FacebookPost.repository.FacebookPostRepository;
import com.example.FacebookPost.repository.LoginDao;
import com.example.FacebookPost.service.FacebookPostService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author meghna.bajoria
 * @since 10/03/21 11:08 AM
 **/
@RestController
@CrossOrigin
@RequestMapping(path = "/QuinBookPost")
public class FacebookPostController {

    @Autowired
    private FacebookPostService facebookPostService;
    @Autowired
    private LoginDao loginDao;



    @PostMapping("/qbpost")
    public FacebookResponseDto uploadFacebookPost(@RequestHeader(value="sessionId") String sessionId,@RequestBody FacebookRequestDto facebookRequestDto) {

        List<Login> loginList = loginDao.findAll();
        HashMap<String,String> userMap=new HashMap<>();
        for(Login obj:loginList)
        {
            String session=obj.getSessionId();
            String userName=obj.getUserName();
            userMap.put(session,userName);
        }
        System.out.println(sessionId);
        String userName=userMap.get(sessionId);
        System.out.println(userMap);
        System.out.println(userName);

        return facebookPostService.uploadFacebookPost(facebookRequestDto,userName);
    }
    @GetMapping("/getAllPostByUser")
    public List<FacebookPost> getAllFacebookPostByUserId(@RequestHeader(value="sessionId") String sessionId) {

        List<Login> loginList= loginDao.findAll();
        System.out.println(loginList);
        HashMap<String,String> userMap=new HashMap<>();
        for(Login login:loginList)
        {
            String session=login.getSessionId();
            System.out.println(session);
            String userName=login.getUserName();
            System.out.println(userName);
            userMap.put(session,userName);
        }
        String userName=userMap.get(sessionId);
        System.out.println(userName+"----");
        return facebookPostService.getFacebookPostByUserName(userName);
    }
    @GetMapping("/getAllPostByUserName/{userName}")
    public List<FacebookPost> getAllFacebookPostByUserNameOnly(@PathVariable("userName") String userName) {

        return facebookPostService.getFacebookPostByUserName(userName);
    }
    @GetMapping("/getPost/{postId}")
    public FacebookPost getFacebookPost(@PathVariable("postId") String postId,@RequestHeader(value="sessionId") String sessionId) {

        List<Login> loginList = loginDao.findAll();
        HashMap<String,String> userMap=new HashMap<>();
        for(Login obj:loginList)
        {
            String session=obj.getSessionId();
            String userName=obj.getUserName();
            userMap.put(session,userName);
        }
        String userName=userMap.get(sessionId);
        return facebookPostService.getFacebookPostById(postId,userName);
    }
///
    @PutMapping("/updatePost/{postId}")
    public FacebookResponseDto updateFacebookPost(@RequestBody FacebookRequestDto facebookRequestDto, @PathVariable("postId") String postId,@RequestHeader(value="sessionId") String sessionId)
    {

        List<Login> loginList=loginDao.findAll();
        HashMap<String,String> userMap=new HashMap<>();
        for(Login obj:loginList)
        {
            String session=obj.getSessionId();
            String userName=obj.getUserName();
            userMap.put(session,userName);
        }
        String userName=userMap.get(sessionId);

        return facebookPostService.updateFacebookPostByPostId(facebookRequestDto,postId,userName);
    }

    @DeleteMapping("/deleteqb/{postId}")
    public String deleteFacebookPost(@PathVariable("postId") String postId) {
        return facebookPostService.deleteFacebookPost(postId);
    }

    @GetMapping()
    public List<Login> getAllUsers() {
        return loginDao.findAll();
    }

    @GetMapping("/user/{sessionId}")
    public Login getUser(@PathVariable("sessionId") String sessionId) {

        return loginDao.findUserById(sessionId);
    }

    @PostMapping("/postEngagement")
    public String likeDislike(@RequestBody LikeDislikeRequestDto likeDislikeRequestDto){


//        List<Login> loginList = loginDao.findAll();
//        HashMap<String,String> userMap=new HashMap<>();
//        for(Login obj:loginList)
//        {
//            String session=obj.getSessionId();
//            String userName=obj.getUserName();
//            userMap.put(session,userName);
//        }

        return facebookPostService.likeDislike(likeDislikeRequestDto);

    }

    @GetMapping("/{userName}")
    public String deleteSession(@PathVariable("userName") String userName)   {
        return loginDao.deleteUser(userName);
    }

    @PostMapping("/postComment")
    public CommentsRequestDto createComment(@RequestBody CommentsRequestDto commentsRequestDto) {

        return facebookPostService.createComment(commentsRequestDto);

    }






}
