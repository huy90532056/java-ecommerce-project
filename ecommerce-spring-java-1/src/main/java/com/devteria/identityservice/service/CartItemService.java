package com.devteria.identityservice.service;

import com.devteria.identityservice.dto.request.CartItemCreationRequest;
import com.devteria.identityservice.dto.response.CartItemResponse;
import com.devteria.identityservice.entity.CartItem;
import com.devteria.identityservice.entity.Product;
import com.devteria.identityservice.entity.Cart;
import com.devteria.identityservice.exception.AppException;
import com.devteria.identityservice.exception.ErrorCode;
import com.devteria.identityservice.repository.CartItemRepository;
import com.devteria.identityservice.repository.ProductRepository;
import com.devteria.identityservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartItemResponse createCartItem(CartItemCreationRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(request.getQuantity());
        cartItem.setProduct(product);
        cartItem.setCart(cart);

        cartItem = cartItemRepository.save(cartItem);

        CartItemResponse response = new CartItemResponse();
        response.setCartItemId(cartItem.getCartItemId());
        response.setQuantity(cartItem.getQuantity());
        response.setProductId(cartItem.getProduct().getProductId());
        response.setCartId(cartItem.getCart().getCartId());

        return response;
    }

    public List<CartItemResponse> getAllCartItems() {
        return cartItemRepository.findAll().stream()
                .map(cartItem -> {
                    CartItemResponse response = new CartItemResponse();
                    response.setCartItemId(cartItem.getCartItemId());
                    response.setQuantity(cartItem.getQuantity());
                    response.setProductId(cartItem.getProduct().getProductId());
                    response.setCartId(cartItem.getCart().getCartId());
                    return response;
                })
                .toList();
    }

    public CartItemResponse getCartItemById(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        CartItemResponse response = new CartItemResponse();
        response.setCartItemId(cartItem.getCartItemId());
        response.setQuantity(cartItem.getQuantity());
        response.setProductId(cartItem.getProduct().getProductId());
        response.setCartId(cartItem.getCart().getCartId());

        return response;
    }

    public CartItemResponse updateCartItem(Long cartItemId, CartItemCreationRequest request) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        cartItem.setQuantity(request.getQuantity());

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        cartItem.setProduct(product);

        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        cartItem.setCart(cart);

        cartItem = cartItemRepository.save(cartItem);

        CartItemResponse response = new CartItemResponse();
        response.setCartItemId(cartItem.getCartItemId());
        response.setQuantity(cartItem.getQuantity());
        response.setProductId(cartItem.getProduct().getProductId());
        response.setCartId(cartItem.getCart().getCartId());

        return response;
    }

    public void deleteCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new AppException(ErrorCode.CART_ITEM_NOT_FOUND);
        }
        cartItemRepository.deleteById(cartItemId);
    }
}
