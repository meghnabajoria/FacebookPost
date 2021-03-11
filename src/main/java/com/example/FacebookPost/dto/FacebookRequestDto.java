package com.example.FacebookPost.dto;

import com.example.FacebookPost.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * @author meghna.bajoria
 * @since 10/03/21 11:08 AM
 **/
@Getter
@Setter

public class FacebookRequestDto {

    private String sessionId;
    private String postCaption;
    private List<String> postImages;
    private String location;

}
