package com.devteria.identityservice.service;

import com.devteria.identityservice.dto.request.NotificationCreationRequest;
import com.devteria.identityservice.dto.request.NotificationUpdateRequest;
import com.devteria.identityservice.dto.response.NotificationResponse;
import com.devteria.identityservice.entity.Notification;
import com.devteria.identityservice.entity.User;
import com.devteria.identityservice.exception.AppException;
import com.devteria.identityservice.exception.ErrorCode;
import com.devteria.identityservice.repository.NotificationRepository;
import com.devteria.identityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    // Create a new Notification
    public NotificationResponse createNotification(NotificationCreationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Notification notification = new Notification();
        notification.setMessage(request.getMessage());
        notification.setUser(user);

        notification = notificationRepository.save(notification);

        return new NotificationResponse(notification.getNotificationId(), notification.getMessage(), user.getId());
    }

    // Get all Notifications
    public List<NotificationResponse> getNotifications() {
        return notificationRepository.findAll().stream()
                .map(notification -> new NotificationResponse(notification.getNotificationId(), notification.getMessage(), notification.getUser().getId()))
                .toList();
    }

    // Get Notification by ID
    public NotificationResponse getNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));

        return new NotificationResponse(notification.getNotificationId(), notification.getMessage(), notification.getUser().getId());
    }

    // Update a Notification
    public NotificationResponse updateNotification(Long notificationId, NotificationUpdateRequest request) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));

        notification.setMessage(request.getMessage());

        notification = notificationRepository.save(notification);

        return new NotificationResponse(notification.getNotificationId(), notification.getMessage(), notification.getUser().getId());
    }

    // Delete a Notification
    public void deleteNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new AppException(ErrorCode.NOTIFICATION_NOT_FOUND));

        notificationRepository.delete(notification);
    }
}
