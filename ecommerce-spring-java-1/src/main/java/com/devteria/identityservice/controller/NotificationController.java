package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.dto.request.NotificationCreationRequest;
import com.devteria.identityservice.dto.request.NotificationUpdateRequest;
import com.devteria.identityservice.dto.response.NotificationResponse;
import com.devteria.identityservice.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    NotificationService notificationService;

    // Create a new Notification
    @PostMapping
    public ApiResponse<NotificationResponse> createNotification(@RequestBody NotificationCreationRequest request) {
        NotificationResponse notificationResponse = notificationService.createNotification(request);
        return ApiResponse.<NotificationResponse>builder()
                .result(notificationResponse)
                .build();
    }

    // Get all Notifications
    @GetMapping
    public ApiResponse<List<NotificationResponse>> getNotifications() {
        List<NotificationResponse> notifications = notificationService.getNotifications();
        return ApiResponse.<List<NotificationResponse>>builder()
                .result(notifications)
                .build();
    }

    // Get Notification by ID
    @GetMapping("/{notificationId}")
    public ApiResponse<NotificationResponse> getNotification(@PathVariable Long notificationId) {
        NotificationResponse notificationResponse = notificationService.getNotification(notificationId);
        return ApiResponse.<NotificationResponse>builder()
                .result(notificationResponse)
                .build();
    }

    // Update a Notification
    @PutMapping("/{notificationId}")
    public ApiResponse<NotificationResponse> updateNotification(@PathVariable Long notificationId, @RequestBody NotificationUpdateRequest request) {
        NotificationResponse notificationResponse = notificationService.updateNotification(notificationId, request);
        return ApiResponse.<NotificationResponse>builder()
                .result(notificationResponse)
                .build();
    }

    // Delete a Notification
    @DeleteMapping("/{notificationId}")
    public ApiResponse<String> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ApiResponse.<String>builder()
                .result("Notification has been deleted")
                .build();
    }
}
