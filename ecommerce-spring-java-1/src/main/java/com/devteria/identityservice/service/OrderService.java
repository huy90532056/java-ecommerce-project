package com.devteria.identityservice.service;

import com.devteria.identityservice.dto.request.OrderCreationRequest;
import com.devteria.identityservice.dto.request.OrderUpdateRequest;
import com.devteria.identityservice.dto.response.OrderResponse;
import com.devteria.identityservice.entity.Order;
import com.devteria.identityservice.entity.OrderStatus;
import com.devteria.identityservice.entity.User;
import com.devteria.identityservice.exception.AppException;
import com.devteria.identityservice.exception.ErrorCode;
import com.devteria.identityservice.repository.OrderRepository;
import com.devteria.identityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    // Tạo một đơn hàng mới
    public OrderResponse createOrder(OrderCreationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = Order.builder()
                .user(user)
                .orderDate(request.getOrderDate())
                .status(OrderStatus.valueOf(request.getStatus()))
                .build();

        order = orderRepository.save(order);

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getId())  // Lấy userId từ đối tượng User
                .orderDate(order.getOrderDate())
                .status(OrderStatus.valueOf(order.getStatus().name())) // Lấy tên của OrderStatus
                .build();
    }

    // Lấy tất cả các đơn hàng
    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream()
                .map(order -> OrderResponse.builder()
                        .orderId(order.getOrderId())
                        .userId(order.getUser().getId()) // Lấy userId từ đối tượng User
                        .orderDate(order.getOrderDate())
                        .status(OrderStatus.valueOf(order.getStatus().name())) // Chuyển trạng thái sang String
                        .build())
                .collect(Collectors.toList());
    }

    // Lấy đơn hàng theo ID
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getId())  // Lấy userId từ đối tượng User
                .orderDate(order.getOrderDate())
                .status(OrderStatus.valueOf(order.getStatus().name())) // Lấy tên của OrderStatus
                .build();
    }

    // Cập nhật đơn hàng
    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        order.setStatus(OrderStatus.valueOf(request.getStatus()));
        order = orderRepository.save(order);

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getId())  // Lấy userId từ đối tượng User
                .orderDate(order.getOrderDate())
                .status(OrderStatus.valueOf(order.getStatus().name())) // Lấy tên của OrderStatus
                .build();
    }

    // Xóa đơn hàng
    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        orderRepository.delete(order);
    }
}
