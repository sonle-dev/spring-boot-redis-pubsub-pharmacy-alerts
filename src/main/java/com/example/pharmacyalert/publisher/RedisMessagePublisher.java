package com.example.pharmacyalert.publisher;

import com.example.pharmacyalert.dto.AlertMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMessagePublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisMessagePublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publish(String channel, AlertMessage message) {
        try {
            // Chuyển đối tượng thành chuỗi JSON
            String jsonPayload = objectMapper.writeValueAsString(message);
            // Gửi bất đồng bộ lên Redis Channel
            redisTemplate.convertAndSend(channel, jsonPayload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Lỗi chuyển đổi AlertMessage sang JSON: " + e.getMessage());
        }
    }
}