package com.example.FacebookPost.dto;

import com.example.FacebookPost.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author meghna.bajoria
 * @since 10/03/21 11:09 AM
 **/
@Getter
@Setter
public class FacebookResponseDto {


    private String message;
    private String postId;
    private String userName;

}
