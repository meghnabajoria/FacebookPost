package com.example.FacebookPost.dto;

import com.example.FacebookPost.entity.Friend;
import lombok.Data;

import java.util.List;

/**
 * @author meghna.bajoria
 * @since 11/03/21 5:46 PM
 **/
@Data
public class FriendsSocialDTO {
    private String userName;
    private List<Friend> friendList;
    private List<String> gotBlockedByList;
    private Policy policy;
}
