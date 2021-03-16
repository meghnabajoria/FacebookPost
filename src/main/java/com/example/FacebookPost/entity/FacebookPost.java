package com.example.FacebookPost.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @author meghna.bajoria
 * @since 10/03/21 11:09 AM
 **/

@Data
@ToString
@Document(collection = "FacebookPost")
public class FacebookPost {



    private String userName;
    @Id
    private String postId;
    private String postCaption;
    private List<String> postImages;
    private int like;
    private int dislike;
    private List<User> postLikedList;
    private List<User> postDislikedList;
    private String date;
    private String location;

    private List<Comments> commentList;
}
