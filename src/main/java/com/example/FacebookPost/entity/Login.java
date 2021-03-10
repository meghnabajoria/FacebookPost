package com.example.FacebookPost.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * @author meghna.bajoria
 * @since 10/03/21 8:59 PM
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Login")
public class Login implements Serializable {


    @Id
    private String sessionId;
    private String userName;
}
