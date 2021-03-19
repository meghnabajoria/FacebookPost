package com.example.FacebookPost.service.serviceImpl;

import com.example.FacebookPost.clients.FriendClient;
import com.example.FacebookPost.clients.UserClient;
import com.example.FacebookPost.dto.*;
import com.example.FacebookPost.entity.Comments;
import com.example.FacebookPost.entity.FacebookPost;
import com.example.FacebookPost.entity.Friend;
import com.example.FacebookPost.entity.User;
import com.example.FacebookPost.repository.FacebookPostRepository;
import com.example.FacebookPost.repository.UserRepository;
import com.example.FacebookPost.service.FacebookPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClients;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
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

    @Autowired
    private FriendClient friendClient;

    @Autowired
    private UserClient userClient;

    static MongoOperations mongoOperations = new MongoTemplate(MongoClients.create(),"FacebookPostdb");

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
        facebookPost.setLike(facebookPost.getLike());
        facebookPost.setDislike(facebookPost.getDislike());
        List<User> likedList=new ArrayList<>();
        facebookPost.setPostLikedList(likedList);
        List<User> dislikedList=new ArrayList<>();
        facebookPost.setPostDislikedList(dislikedList);
        List<String> url=facebookRequestDto.getPostImages();
        facebookPost.setPostImages(url);

//        //Comments
        List<Comments> commentList=new ArrayList<>();
        facebookPost.setCommentList(commentList);

        facebookPostRepository.save(facebookPost);
        //Notification
        makeNotification(userName,postId);
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
        //kafkaMethod("hello","from producer");
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
    private static void kafkaMethod(String key ,String value){

        Producer<String,String> producer = new KafkaProducer<>(getPropertiesOfKafka());
        producer.send(new ProducerRecord<>("notifications",key,value));
        producer.close();
    }


    @Override
    public String likeDislike(LikeDislikeRequestDto likeDislikeRequestDto) {

//        String postId = likeDislikeRequestDto.getPostId();
//        FacebookPost facebookPost=facebookPostRepository.findByPostId(postId);
//
//        if(facebookPost.getUserName().equalsIgnoreCase(userName)) {
//
//            if(likeDislikeRequestDto.getLike()==1) {
//                List<User> userList=facebookPost.getPostLike();
//                int prevLike = facebookPost.getLike();
//                facebookPost.setLike(likeDislikeRequestDto.getLike() + prevLike);
//                User user = new User();
//                user.setLikeDislikeUserName(userName);
//                user.setLikeDislikeFullName(likeDislikeRequestDto.getFullName());
//                user.setLikeDislikeProfileUrl(likeDislikeRequestDto.getProfilepic());
//                userList.add(user);
//                facebookPost.setPostLike(userList);
//            }
//            if(likeDislikeRequestDto.getDislike()==1){
//                int prevDislike = facebookPost.getDislike();
//                facebookPost.setDislike(likeDislikeRequestDto.getDislike() + prevDislike);
//                List<User> userList2=facebookPost.getPostDislike();
//                User user = new User();
//                user.setLikeDislikeUserName(userName);
//                user.setLikeDislikeFullName(likeDislikeRequestDto.getFullName());
//                user.setLikeDislikeProfileUrl(likeDislikeRequestDto.getProfilepic());
//                userList2.add(user);
//                facebookPost.setPostDislike(userList2);
//            }
//            facebookPostRepository.save(facebookPost);
//            return "successFul";
//        }
//        else
//        {
//            return "Can't Find User";
//        }
//        Optional<FacebookPost> optionalFacebookPost = facebookPostRepository.findById(postId);
//        if (optionalFacebookPost.isPresent()) {
//            int likes = optionalFacebookPost.get().getLike();
//            int disLikes = optionalFacebookPost.get().getDislike();
//            likes += likeDislikeRequestDto.getLike();
//            disLikes += likeDislikeRequestDto.getDislike();
//            Query query = new Query();
//            Criteria criteria = Criteria.where("postId").is(postId);
//            query.addCriteria(criteria);
//            Update update = new Update().set("like", likes);
//            mongoOperations.updateFirst(query, update, FacebookPost.class);
//            update = new Update().set("dislike", disLikes);
//            mongoOperations.updateFirst(query, update, FacebookPost.class);
//
//
//        }
//        return  "SUCESSS";
//        FacebookPost facebookPost=facebookPostRepository.findByPostId(postId);
//
//        if(facebookPost.getUserName().equalsIgnoreCase(userName)) {
//
//            if(likeDislikeRequestDto.getLike()==1 && likeDislikeRequestDto.getDislike()==0) {
//                List<User> userList=facebookPost.getPostLikedList();
//                List<User> userDislikedList = facebookPost.getPostDislikedList();
//                int prevLike = facebookPost.getLike();
//                int x = likeDislikeRequestDto.getLike() + prevLike;
//                facebookPost.setLike(x);
//
//                User user = new User();
//                user.setLikeDislikeUserName(userName);
//                user.setLikeDislikeFullName(likeDislikeRequestDto.getFullName());
//                user.setLikeDislikeProfileUrl(likeDislikeRequestDto.getProfilepic());
//                if(userDislikedList.contains(user)){
//                    userDislikedList.remove(user);
//                }
//                userList.add(user);
//                facebookPost.setPostLikedList(userList);
//                facebookPost.setPostDislikedList(userDislikedList);
//                facebookPostRepository.save(facebookPost);
//            }
//            if(likeDislikeRequestDto.getDislike()==0 && likeDislikeRequestDto.getLike()==0){
//                int prevDislike = facebookPost.getDislike();
//                int x = likeDislikeRequestDto.getDislike() + prevDislike;
//                facebookPost.setDislike(x);
//                List<User> userList=facebookPost.getPostDislikedList();
//                List<User> userLikedlist = facebookPost.getPostLikedList();
//                User user = new User();
//                user.setLikeDislikeUserName(userName);
//                user.setLikeDislikeFullName(likeDislikeRequestDto.getFullName());
//                user.setLikeDislikeProfileUrl(likeDislikeRequestDto.getProfilepic());
//                if(userLikedlist.contains(user)){
//                    userLikedlist.remove(user);
//                }
//                userList.add(user);
//                facebookPost.setPostDislikedList(userList);
//                facebookPost.setPostLikedList(userLikedlist);
//                facebookPostRepository.save(facebookPost);
//            }
//            // some changes started here
//            if(likeDislikeRequestDto.getDislike() == 0 && likeDislikeRequestDto.getLike() == -1) {
//
//                List<User> userLikedlist = facebookPost.getPostLikedList();
//                int prevLike=facebookPost.getLike()-1;
//                facebookPost.setLike(prevLike);
//                User user = new User();
//                user.setLikeDislikeUserName(userName);
//                user.setLikeDislikeFullName(likeDislikeRequestDto.getFullName());
//                user.setLikeDislikeProfileUrl(likeDislikeRequestDto.getProfilepic());
//                userLikedlist.remove(user);
//                facebookPost.setPostLikedList(userLikedlist);
//                facebookPostRepository.save(facebookPost);
//            }
//            if(likeDislikeRequestDto.getDislike() == -1 && likeDislikeRequestDto.getLike() == 0)
//            {
//                List<User> userDisliked=facebookPost.getPostDislikedList();
//                int prevDislike=facebookPost.getDislike()-1;
//                facebookPost.setDislike(prevDislike);
//                User user = new User();
//                user.setLikeDislikeUserName(userName);
//                user.setLikeDislikeFullName(likeDislikeRequestDto.getFullName());
//                user.setLikeDislikeProfileUrl(likeDislikeRequestDto.getProfilepic());
//                userDisliked.remove(user);
//                facebookPost.setPostDislikedList(userDisliked);
//                facebookPostRepository.save(facebookPost);
//            }
//
//            //few changes more
//            if(likeDislikeRequestDto.getLike()==-1 && likeDislikeRequestDto.getDislike()==1)
//            {
//                facebookPost.setLike(facebookPost.getLike()-1);
//                facebookPost.setDislike(facebookPost.getDislike()+1);
//                User user=new User();
//                user.setLikeDislikeProfileUrl(likeDislikeRequestDto.getProfilepic());
//                user.setLikeDislikeFullName(likeDislikeRequestDto.getFullName());
//                user.setLikeDislikeUserName(userName);
//                List<User> likedUserList=facebookPost.getPostLikedList();
//                List<User> dislikedUserList=facebookPost.getPostDislikedList();
//                likedUserList.remove(user);
//                dislikedUserList.add(user);
//            }
//            if(likeDislikeRequestDto.getLike()==1 && likeDislikeRequestDto.getDislike()==-1)
//            {
//                facebookPost.setLike(facebookPost.getLike()+1);
//                facebookPost.setDislike(facebookPost.getDislike()-1);
//                User user=new User();
//                user.setLikeDislikeProfileUrl(likeDislikeRequestDto.getProfilepic());
//                user.setLikeDislikeFullName(likeDislikeRequestDto.getFullName());
//                user.setLikeDislikeUserName(userName);
//                List<User> likedUserList=facebookPost.getPostLikedList();
//                List<User> dislikedUserList=facebookPost.getPostDislikedList();
//                likedUserList.add(user);
//                dislikedUserList.remove(user);
//
//            }
//            //few changes end here
//
//            //some changes end here
//
//            return "successFul";
//        }
//        else
//        {
//            return "Can't Find User";
//        }


        String postId = likeDislikeRequestDto.getPostId();
        System.out.println(likeDislikeRequestDto.getLike()+";;"+likeDislikeRequestDto.getUserName());
        System.out.println(postId);
        String userName=likeDislikeRequestDto.getUserName();
        FacebookPost facebookPost=facebookPostRepository.findByPostId(postId);
        System.out.println(facebookPost.getUserName());
        try{
            if(likeDislikeRequestDto.getLike()==1) {
                List<User> userLikedList=facebookPost.getPostLikedList();
                List<User> userDisLikedList=new ArrayList<>();
                int prevLike = facebookPost.getLike();
                facebookPost.setLike(likeDislikeRequestDto.getLike() + prevLike);
                User user = new User();
                user.setLikeDislikeUserName(userName);
                user.setLikeDislikeFullName(userName);
                user.setLikeDislikeProfileUrl(likeDislikeRequestDto.getProfilepic());
                userLikedList.add(user);
                facebookPost.setPostLikedList(userLikedList);
                facebookPost.setDislike(0);
                facebookPost.setPostDislikedList(userDisLikedList);
            }
            else if(likeDislikeRequestDto.getLike()==0) {

                List<User> userLikedList=facebookPost.getPostLikedList();
                List<User> userDisLikedList=new ArrayList<>();
                int prevLike = facebookPost.getLike();
                facebookPost.setLike(prevLike-1);
                User user = new User();
                user.setLikeDislikeUserName(userName);
                user.setLikeDislikeFullName(userName);
                user.setLikeDislikeProfileUrl(likeDislikeRequestDto.getProfilepic());
                userLikedList.remove(user);
                facebookPost.setPostLikedList(userLikedList);
                facebookPost.setDislike(0);
                facebookPost.setPostDislikedList(userDisLikedList);
            }
            else{
                return "Unsuccessful";
            }}
            catch (Exception e){
                   e.printStackTrace();
            }
            facebookPostRepository.save(facebookPost);
            return "SuccessFul";


    }

    private void makeNotification(String userName,String postId){
        ObjectMapper objectMapper = new ObjectMapper();
        LatestPostNotification latestPostNotification = new LatestPostNotification();
        latestPostNotification.setEventType("NEWPOST");
        latestPostNotification.setPostId(postId);
        Friend friend = new Friend();
        friend.setUserName(userName);
        //todo
        friend.setProfilePic("update later");
        try{
            FriendProfileDTO friendProfileDTO = userClient.getFriendProfile(userName);
            friend.setFullName(friendProfileDTO.getFullName());
            friend.setProfilePic(friendProfileDTO.getImg());
            latestPostNotification.setPostedBy(friend);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {
            FriendsSocialDTO friendsSocialDTO = friendClient.getFeedPost(userName);
            List<Friend> friendList = friendsSocialDTO.getFriendList();
            String key = "NEWPOST";
            for (Friend f : friendList) {
                key += "--" + f.getUserName();
            }
            String value = "";
            try {
                value = objectMapper.writeValueAsString(latestPostNotification);
                kafkaMethod(key, value);
            }
            catch (Exception e){

            }
        }
        catch (Exception e){


        }
    }

    @Override
    public FacebookPost getFacebookPostById(String postId,String userName)
    {
        FacebookPost facebookPost=facebookPostRepository.findByPostId(postId);
        String user=facebookPost.getUserName();
        if(user.equalsIgnoreCase(userName)){
            return facebookPost;
        }
        else{
            return null;
        }
    }

    @Override
    public CommentsRequestDto createComment ( CommentsRequestDto commentsRequestDto) {
        String postId = commentsRequestDto.getPostId();
        FacebookPost facebookPost =  facebookPostRepository.findByPostId(postId);
        System.out.println(postId+" ----");
        String commentText = commentsRequestDto.getCommentText();
        String commentedBy = commentsRequestDto.getCommentedBy();
        try {
            List<Comments> commentsList=facebookPost.getCommentList();
            System.out.println(commentsList+"......");
            Comments comments=new Comments();
            comments.setCommentText(commentText);
            comments.setCommentedBy(commentedBy);
            commentsList.add(comments);
            System.out.println(commentsList);
            facebookPost.setCommentList(commentsList);
            facebookPostRepository.save(facebookPost);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return commentsRequestDto;




    }

}
