package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.dto.request.ShippingTrackerCreationRequest;
import com.devteria.identityservice.dto.request.ShippingTrackerUpdateRequest;
import com.devteria.identityservice.dto.response.ShippingTrackerResponse;
import com.devteria.identityservice.service.ShippingTrackerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShippingTrackerController {

    ShippingTrackerService shippingTrackerService;

    // Tạo thông tin theo dõi vận chuyển
    @PostMapping
    public ApiResponse<ShippingTrackerResponse> createShippingTracker(@RequestBody ShippingTrackerCreationRequest request) {
        ShippingTrackerResponse response = shippingTrackerService.createShippingTracker(request);
        return ApiResponse.<ShippingTrackerResponse>builder()
                .result(response)
                .build();
    }

    // Lấy tất cả thông tin theo dõi vận chuyển
    @GetMapping
    public ApiResponse<List<ShippingTrackerResponse>> getAllShippingTrackers() {
        List<ShippingTrackerResponse> responses = shippingTrackerService.getShippingTrackers();
        return ApiResponse.<List<ShippingTrackerResponse>>builder()
                .result(responses)
                .build();
    }

    // Lấy thông tin theo dõi vận chuyển theo trackingId
    @GetMapping("/{trackingId}")
    public ApiResponse<ShippingTrackerResponse> getShippingTracker(@PathVariable Long trackingId) {
        ShippingTrackerResponse response = shippingTrackerService.getShippingTracker(trackingId);
        return ApiResponse.<ShippingTrackerResponse>builder()
                .result(response)
                .build();
    }

    // Cập nhật thông tin theo dõi vận chuyển
    @PutMapping("/{trackingId}")
    public ApiResponse<ShippingTrackerResponse> updateShippingTracker(@PathVariable Long trackingId, @RequestBody ShippingTrackerUpdateRequest request) {
        ShippingTrackerResponse response = shippingTrackerService.updateShippingTracker(trackingId, request);
        return ApiResponse.<ShippingTrackerResponse>builder()
                .result(response)
                .build();
    }

    // Xóa thông tin theo dõi vận chuyển
    @DeleteMapping("/{trackingId}")
    public ApiResponse<String> deleteShippingTracker(@PathVariable Long trackingId) {
        shippingTrackerService.deleteShippingTracker(trackingId);
        return ApiResponse.<String>builder()
                .result("Shipping Tracker has been deleted")
                .build();
    }
}
