package com.example.FacebookPost.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author meghna.bajoria
 * @since 13/03/21 12:41 PM
 **/
@Data
public class Comments {
//    private long commentId;
    private String commentText;
    private String commentedBy;
   // private String commentedOnUserName;
}
