package com.vct.kds.web.controller;

import com.vct.kds.service.OrderService;
import com.vct.kds.web.request.NewOrderRequest;
import com.vct.kds.web.request.OrderStatusRequest;
import com.vct.kds.web.response.ActiveOrderResponse;
import com.vct.kds.web.response.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PutMapping
    public ResponseEntity<Void> updateOrderStatus(@RequestBody @Valid OrderStatusRequest orderRequest) {
        log.debug("Update order status.");
        orderService.updateOrderStatus(orderRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ActiveOrderResponse>> getAllActiveOrders() {
        log.debug("Get all active orders.");
        return ResponseEntity.status(HttpStatus.OK)
                .body(orderService.getAllActiveOrders());
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createNewOrder(@RequestBody @Valid NewOrderRequest newOrderRequest) {
        log.debug("Create new table order.");
        final var orderResponse = orderService.createNewOrder(newOrderRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderResponse);
    }
}
