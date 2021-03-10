package com.example.FacebookPost.service.serviceImpl;

import com.example.FacebookPost.dto.FacebookRequestDto;
import com.example.FacebookPost.dto.FacebookResponseDto;
import com.example.FacebookPost.entity.FacebookPost;
import com.example.FacebookPost.entity.User;
import com.example.FacebookPost.repository.FacebookPostRepository;
import com.example.FacebookPost.repository.UserRepository;
import com.example.FacebookPost.service.FacebookPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * @author meghna.bajoria
 * @since 10/03/21 11:11 AM
 **/
@Service
public class FacebookPostServiceImpl implements FacebookPostService {

    @Autowired
    private FacebookPostRepository facebookPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public FacebookResponseDto uploadFacebookPost(FacebookRequestDto facebookRequestDto,String userName) {

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime());
        String postId = userName+timeStamp;
        FacebookPost facebookPost=new FacebookPost();
        User user = new User();
        user.setLikeDislikeFullName("Prateek");
        user.setLikeDislikeUserName("Prateek Srivastava");
        user.setLikeDislikeProfileUrl("prateek.png");
        userRepository.save(user);
        facebookPost.setPostId(postId);
        facebookPost.setUserName(userName);
        facebookPost.setPostCaption(facebookRequestDto.getPostCaption());
        facebookPost.setLike(facebookRequestDto.getLike());
        facebookPost.setDislike(facebookRequestDto.getDislike());
        List<User> likeList=facebookRequestDto.getPostLike();
        List<User> disLikeList=facebookRequestDto.getPostDisLike();
        List<String> url=facebookRequestDto.getPostImages();
        facebookPost.setPostImages(url);
        facebookPost.setPostLike(likeList);
        facebookPost.setPostDisLike(disLikeList);
        facebookPostRepository.save(facebookPost);

        FacebookResponseDto facebookResponseDto=new FacebookResponseDto();
        facebookResponseDto.setPostId(facebookPost.getPostId());
        facebookResponseDto.setMessage("Uploaded Successfully");
        facebookResponseDto.setUserName(userName);

        return facebookResponseDto;

    }

    @Override
    public FacebookResponseDto updateFacebookPostByPostId(FacebookRequestDto facebookRequestDto,String postId,String userName) {

       FacebookPost facebookPost=facebookPostRepository.findByPostId(postId);
       facebookPost.setUserName(userName);
       facebookPost.setPostDisLike(facebookRequestDto.getPostDisLike());
       facebookPost.setPostLike(facebookRequestDto.getPostLike());
       facebookPost.setLike(facebookRequestDto.getLike());
       facebookPost.setDislike(facebookRequestDto.getDislike());
       facebookPost.setPostImages(facebookRequestDto.getPostImages());
       facebookPost.setLocation(facebookRequestDto.getLocation());
       facebookPost.setPostId(postId);
       facebookPost.setPostCaption(facebookRequestDto.getPostCaption());
       facebookPostRepository.save(facebookPost);
       FacebookResponseDto facebookResponseDto=new FacebookResponseDto();
       facebookResponseDto.setUserName(userName);
       facebookResponseDto.setMessage("Update SuccessFully");
       facebookResponseDto.setPostId(postId);
       return facebookResponseDto;

    }

    @Override
    public String deleteFacebookPost(String postId) {
        facebookPostRepository.deleteById(postId);

        return "deleted successfully";
    }

    @Override
    public Optional<FacebookPost> getFacebookPost(String postId) {
        return facebookPostRepository.findById(postId);

    }

     @Override
    public List<FacebookPost> getFacebookPostByUserName (String userName) {
        return  facebookPostRepository.getFacebookPostByUserName(userName);
    }



}
