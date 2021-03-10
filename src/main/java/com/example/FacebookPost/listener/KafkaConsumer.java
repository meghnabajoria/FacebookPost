package com.example.FacebookPost.listener;

/**
 * @author meghna.bajoria
 * @since 10/03/21 8:38 PM
 **/

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "session", groupId = "group_id")
    public void consume(String message) {

        String[] str=message.split(" ");

        System.out.println("Consumed message: " + message);
    }

}