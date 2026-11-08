package com.example.pharmacyalert.controller;

import com.example.pharmacyalert.dto.AlertMessage;
import com.example.pharmacyalert.publisher.RedisMessagePublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alerts")
public class AlertController {

    private final RedisMessagePublisher publisher;

    @Value("${app.redis.channel.pharmacy-alerts}")
    private String defaultChannel;

    public AlertController(RedisMessagePublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping
    public ResponseEntity<String> sendAlert(@RequestBody AlertMessage request) {
        // Lấy tên kênh từ trường topicListen, nếu không có thì lấy kênh mặc định
        String targetChannel = (request.getTopicListen() != null && !request.getTopicListen().isEmpty())
                ? request.getTopicListen()
                : defaultChannel;

        // Bắn tin nhắn vào Redis Channel
        publisher.publish(targetChannel, request);

        // Chuỗi phản hồi đúng từng ký tự như ảnh Postman
        return ResponseEntity.ok("Đã gửi thông báo đến kênh : " + targetChannel);
    }
}