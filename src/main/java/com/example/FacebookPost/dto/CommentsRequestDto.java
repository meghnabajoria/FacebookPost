package com.example.FacebookPost.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author meghna.bajoria
 * @since 13/03/21 2:40 PM
 **/
@Getter
@Setter
public class CommentsRequestDto {
    private String postId;
    private String commentText;
    private String commentedBy;
}
