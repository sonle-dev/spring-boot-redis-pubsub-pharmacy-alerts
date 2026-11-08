package com.example.pharmacyalert.subscriber;

import com.example.pharmacyalert.dto.AlertMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class RedisMessageSubscriber implements MessageListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // Lấy nội dung chuỗi JSON từ byte array
            String jsonPayload = new String(message.getBody(), StandardCharsets.UTF_8);

            // Chuyển đổi JSON thành đối tượng AlertMessage bằng ObjectMapper
            AlertMessage alert = objectMapper.readValue(jsonPayload, AlertMessage.class);

            // In đúng định dạng Console như hình ảnh kết quả mong muốn
            System.out.println("=== THÔNG BÁO DASHBOARD QUẢN LÝ ===");
            System.out.println("Type : " + alert.getType());
            System.out.println("Nội dung: " + alert.getMessage());
            System.out.println("===================================");

        } catch (Exception e) {
            System.err.println("Lỗi phân tích cú pháp tin nhắn từ Redis Channel: " + e.getMessage());
        }
    }
}