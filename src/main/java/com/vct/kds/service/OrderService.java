package com.vct.kds.service;

import com.vct.kds.web.request.NewOrderRequest;
import com.vct.kds.web.request.OrderStatusRequest;
import com.vct.kds.web.response.ActiveOrderResponse;
import com.vct.kds.web.response.OrderResponse;

import java.util.List;

public interface OrderService {

    void updateOrderStatus(OrderStatusRequest orderRequest);

    OrderResponse createNewOrder(NewOrderRequest newOrderRequest);

    List<ActiveOrderResponse> getAllActiveOrders();
}
