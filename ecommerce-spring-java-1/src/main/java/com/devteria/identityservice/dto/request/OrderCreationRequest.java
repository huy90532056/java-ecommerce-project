package com.devteria.identityservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderCreationRequest {

    @NotNull(message = "User ID is mandatory")
    private String userId; // ID của khách hàng (User)

    @NotNull(message = "Order date is mandatory")
    private LocalDate orderDate; // Ngày đặt hàng

    @NotNull(message = "Order status is mandatory")
    private String status; // Trạng thái đơn hàng (ví dụ: "PENDING", "COMPLETED", "CANCELLED")

    // Các item của đơn hàng có thể được thêm sau khi đơn hàng được tạo
    // Thông tin thanh toán và giao hàng có thể được thêm sau
}
