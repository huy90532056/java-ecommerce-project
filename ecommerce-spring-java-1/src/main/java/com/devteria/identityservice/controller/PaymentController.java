package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.dto.request.PaymentCreationRequest;
import com.devteria.identityservice.dto.request.PaymentUpdateRequest;
import com.devteria.identityservice.dto.response.PaymentResponse;
import com.devteria.identityservice.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    PaymentService paymentService;

    // Tạo mới một Payment
    @PostMapping
    public ApiResponse<PaymentResponse> createPayment(@RequestBody PaymentCreationRequest request) {
        PaymentResponse paymentResponse = paymentService.createPayment(request);
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentResponse)
                .build();
    }

    // Lấy tất cả các Payment
    @GetMapping
    public ApiResponse<List<PaymentResponse>> getPayments() {
        List<PaymentResponse> payments = paymentService.getPayments();
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(payments)
                .build();
    }

    // Lấy Payment theo ID
    @GetMapping("/{paymentId}")
    public ApiResponse<PaymentResponse> getPayment(@PathVariable Long paymentId) {
        PaymentResponse paymentResponse = paymentService.getPayment(paymentId);
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentResponse)
                .build();
    }

    // Cập nhật Payment
    @PutMapping("/{paymentId}")
    public ApiResponse<PaymentResponse> updatePayment(@PathVariable Long paymentId, @RequestBody PaymentUpdateRequest request) {
        PaymentResponse paymentResponse = paymentService.updatePayment(paymentId, request);
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentResponse)
                .build();
    }

    // Xóa Payment
    @DeleteMapping("/{paymentId}")
    public ApiResponse<String> deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
        return ApiResponse.<String>builder()
                .result("Payment has been deleted")
                .build();
    }
}
