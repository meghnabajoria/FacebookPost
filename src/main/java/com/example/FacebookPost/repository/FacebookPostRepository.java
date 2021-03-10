package com.example.FacebookPost.repository;

import com.example.FacebookPost.entity.FacebookPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author meghna.bajoria
 * @since 10/03/21 11:10 AM
 **/
public interface FacebookPostRepository extends MongoRepository<FacebookPost,String> {

    @Query(value ="{userName : ?0}")
    List<FacebookPost> getFacebookPostByUserName(String userName);

    @Query(value = "{_id : ?0}")
    FacebookPost findByPostId(String postId);



}
