package com.devteria.identityservice.service;

import com.devteria.identityservice.configuration.MQConfig;
import com.devteria.identityservice.dto.custom.CustomMessage;
import com.devteria.identityservice.dto.request.OrderCreationRequest;
import com.devteria.identityservice.dto.request.OrderUpdateRequest;
import com.devteria.identityservice.dto.response.OrderResponse;
import com.devteria.identityservice.entity.Notification;
import com.devteria.identityservice.entity.Order;
import com.devteria.identityservice.entity.OrderStatus;
import com.devteria.identityservice.entity.User;
import com.devteria.identityservice.exception.AppException;
import com.devteria.identityservice.exception.ErrorCode;
import com.devteria.identityservice.repository.OrderRepository;
import com.devteria.identityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    @Autowired
    private RabbitTemplate template;

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

        CustomMessage message = new CustomMessage();

        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        message.setMessage(request.getUserId());
        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY, message);

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
