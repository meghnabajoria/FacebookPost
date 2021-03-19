package com.example.FacebookPost.clients;

import com.example.FacebookPost.dto.FriendsSocialDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author meghna.bajoria
 * @since 11/03/21 5:48 PM
 **/
@FeignClient(name="fetchpost-client",url="10.177.68.67:8082")
public interface FriendClient {

        @PostMapping("/fetchUserSocial")
        FriendsSocialDTO getFeedPost(@RequestParam("userName") String userName);

}
