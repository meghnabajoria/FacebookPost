package com.example.FacebookPost.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author meghna.bajoria
 * @since 11/03/21 3:06 PM
 **/

@Getter
@Setter
public class LikeDislikeRequestDto {

    private int like;
    private int dislike;
    private String postId;
    private String fullName;
    private  String url;





}
