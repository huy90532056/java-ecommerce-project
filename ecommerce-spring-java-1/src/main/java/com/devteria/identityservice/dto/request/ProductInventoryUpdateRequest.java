package com.devteria.identityservice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInventoryUpdateRequest {
    private Long productId;  // ID của Product
    private Long inventoryId;  // ID của Inventory
    private Integer quantity;  // Số lượng sản phẩm
}
