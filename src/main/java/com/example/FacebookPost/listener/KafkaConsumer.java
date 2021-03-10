package com.example.FacebookPost.listener;

/**
 * @author meghna.bajoria
 * @since 10/03/21 8:38 PM
 **/

import com.example.FacebookPost.entity.Login;
import com.example.FacebookPost.repository.LoginDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
public class KafkaConsumer {

    @Autowired
    private LoginDao loginDao;

    @KafkaListener(topics = "session", groupId = "group_id")
    public void consume(String message) {

        String[] str=message.split(" ");
        String sessionId=str[1];
        String userName=str[0];
        System.out.println(sessionId+"----"+userName);
        Login login=new Login();
        login.setUserName(userName);
        login.setSessionId(sessionId);
        loginDao.save(login);
        System.out.println("Saved Successfully: " + message);
    }

}