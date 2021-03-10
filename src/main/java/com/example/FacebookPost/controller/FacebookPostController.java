package com.example.FacebookPost.controller;

import com.example.FacebookPost.dto.FacebookRequestDto;
import com.example.FacebookPost.dto.FacebookResponseDto;
import com.example.FacebookPost.entity.FacebookPost;
import com.example.FacebookPost.repository.FacebookPostRepository;
import com.example.FacebookPost.service.FacebookPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author meghna.bajoria
 * @since 10/03/21 11:08 AM
 **/
@RestController
@CrossOrigin
@RequestMapping(path = "/FacebookPost")
public class FacebookPostController {

    @Autowired
    private FacebookPostService facebookPostService;




    @PostMapping("/fbpost")
    public FacebookResponseDto uploadFacebookPost(@RequestBody FacebookRequestDto facebookRequestDto) {
        return facebookPostService.uploadFacebookPost(facebookRequestDto);
    }

    @GetMapping("/getAllPostByUser/{userName}")
    public List<FacebookPost> getAllFacebookPostByUserId(@PathVariable("userName") String userName) {
        return facebookPostService.getFacebookPostByUserName(userName);
    }

    @GetMapping("/getPost/{postId}")
    public Optional<FacebookPost> getFacebookPost(@PathVariable("postId") String postId) {
        return facebookPostService.getFacebookPost(postId);
    }

    @PutMapping("/updatePost/{postId}")
    public FacebookResponseDto updateFacebookPost(@RequestBody FacebookRequestDto facebookRequestDto, @PathVariable("postId") String postId) {
        return facebookPostService.updateFacebookPostByPostId(facebookRequestDto,postId);
    }

    @DeleteMapping("/deletefb/{postId}")
    public String deleteFacebookPost(@PathVariable("postId") String postId) {
        return facebookPostService.deleteFacebookPost(postId);
    }
}
