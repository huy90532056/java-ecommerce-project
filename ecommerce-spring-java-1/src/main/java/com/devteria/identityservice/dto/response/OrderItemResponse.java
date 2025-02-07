package com.devteria.identityservice.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemResponse {

    private Long orderItemId;
    private Long productId;
    private Integer quantity;
    private Double totalPrice;
}
