package com.example.FacebookPost.service.serviceImpl;

import com.example.FacebookPost.dto.FacebookRequestDto;
import com.example.FacebookPost.dto.FacebookResponseDto;
import com.example.FacebookPost.dto.LikeDislikeRequestDto;
import com.example.FacebookPost.entity.FacebookPost;
import com.example.FacebookPost.entity.User;
import com.example.FacebookPost.repository.FacebookPostRepository;
import com.example.FacebookPost.repository.UserRepository;
import com.example.FacebookPost.service.FacebookPostService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author meghna.bajoria
 * @since 10/03/21 11:11 AM
 **/
@Service
public class FacebookPostServiceImpl implements FacebookPostService {

    public static Properties getPropertiesOfKafka(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.177.68.98:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    @Autowired
    private FacebookPostRepository facebookPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public FacebookResponseDto uploadFacebookPost(FacebookRequestDto facebookRequestDto,String userName) {

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime());
        String postId = userName+timeStamp;
        FacebookPost facebookPost=new FacebookPost();
        facebookPost.setPostId(postId);
        facebookPost.setUserName(userName);
        facebookPost.setPostCaption(facebookRequestDto.getPostCaption());
        facebookPost.setLocation(facebookRequestDto.getLocation());
        facebookPost.setDate(timeStamp);
        List<String> url=facebookRequestDto.getPostImages();
        facebookPost.setPostImages(url);
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
        System.out.println("before kafka");
        kafkaMethod("hello","from producer");
        System.out.println("after kafka");
        return "deleted successfully";
    }

//    @Override
//    public Optional<FacebookPost> getFacebookPost(String postId) {
//        return facebookPostRepository.findById(postId);
//
//    }

     @Override
    public List<FacebookPost> getFacebookPostByUserName (String userName) {


         Sort sort = Sort.by(Sort.Direction.DESC, "date");
         return facebookPostRepository.getFacebookPostByUserName(userName, sort);


    }
    private static void kafkaMethod(String userName ,String sessionID){

        Producer<String,String> producer = new KafkaProducer<>(getPropertiesOfKafka());
        producer.send(new ProducerRecord<>("notifications",userName,sessionID));
        producer.close();
    }


    @Override
    public String likeDislike(String userName, LikeDislikeRequestDto likeDislikeRequestDto)
    {

        String postId=likeDislikeRequestDto.getPostId();
        FacebookPost facebookPost=facebookPostRepository.findByPostId(postId);

        if(facebookPost.getUserName().equalsIgnoreCase(userName)) {

            if(likeDislikeRequestDto.getLike()==1) {
                List<User> userList=facebookPost.getPostLike();
                int prevLike = facebookPost.getLike();
                facebookPost.setLike(likeDislikeRequestDto.getLike() + prevLike);
                User user = new User();
                user.setLikeDislikeUserName(userName);
                user.setLikeDislikeFullName(likeDislikeRequestDto.getFullName());
                user.setLikeDislikeProfileUrl(likeDislikeRequestDto.getUrl());
                userList.add(user);
                facebookPost.setPostLike(userList);
            }
            if(likeDislikeRequestDto.getDislike()==1){
                int prevDislike = facebookPost.getDislike();
                facebookPost.setDislike(likeDislikeRequestDto.getDislike() + prevDislike);
                List<User> userList2=facebookPost.getPostDislike();
                User user = new User();
                user.setLikeDislikeUserName(userName);
                user.setLikeDislikeFullName(likeDislikeRequestDto.getFullName());
                user.setLikeDislikeProfileUrl(likeDislikeRequestDto.getUrl());
                userList2.add(user);
                facebookPost.setPostDislike(userList2);
            }
            facebookPostRepository.save(facebookPost);
            return "successFul";
        }
        else
        {
            return "Can't Find User";
        }

    }

}
