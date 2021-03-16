package com.example.FacebookPost.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author meghna.bajoria
 * @since 13/03/21 2:40 PM
 **/
@Getter
@Setter
public class CommentResponseDto {
    private String postId;
    private String message;
    private String userName;


}
