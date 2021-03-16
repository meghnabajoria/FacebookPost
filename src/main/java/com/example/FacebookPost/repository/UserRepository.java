package com.example.FacebookPost.repository;

import com.example.FacebookPost.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author meghna.bajoria
 * @since 10/03/21 12:46 PM
 **/
public interface UserRepository extends MongoRepository<User,Long> {



}
