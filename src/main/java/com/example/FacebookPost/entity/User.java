package com.example.FacebookPost.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * @author meghna.bajoria
 * @since 10/03/21 11:18 AM
 **/
@Data
@Document(collection = "User")
public class User {


    @Id
    private String likeDislikeUserName;
    private String likeDislikeFullName;
    private String likeDislikeProfileUrl;
}
