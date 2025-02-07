package com.devteria.identityservice.service;

import com.devteria.identityservice.configuration.MQConfig;
import com.devteria.identityservice.dto.custom.CustomMessage;
import com.devteria.identityservice.entity.Notification;
import com.devteria.identityservice.entity.User;
import com.devteria.identityservice.repository.NotificationRepository;
import com.devteria.identityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void consumeNotification(CustomMessage message) {
        System.out.println(message.getMessage());
        Notification notification = new Notification();
        notification.setMessage("Ban da order thanh cong!");

        User user = userRepository.findById(message.getMessage())
                .orElseThrow(() -> new RuntimeException("User not found"));

        notification.setUser(user);
        notificationRepository.save(notification);
    }
}