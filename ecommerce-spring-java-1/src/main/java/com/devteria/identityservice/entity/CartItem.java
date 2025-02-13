package com.devteria.identityservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @NotNull(message = "Quantity is mandatory")
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    private Cart cart;

}