package com.example.FacebookPost.dto;

import com.example.FacebookPost.entity.Friend;
import lombok.Data;

@Data
public class LatestPostNotification {

    private Friend postedBy;
    private String postId;
    private String eventType;
}