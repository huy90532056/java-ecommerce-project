package com.devteria.identityservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemUpdateRequest {

    private Integer quantity;
    private Double totalPrice;
}
