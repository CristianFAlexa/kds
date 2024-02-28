package com.vct.kds.converter;

import com.vct.kds.model.Order;
import com.vct.kds.web.response.OrderResponse;
import com.vct.kds.web.response.ProductOrderResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToOrderResponseConverter implements Converter<Order, OrderResponse> {

    /**
     * Convenience method used to convert a {@link Order} into a {@link OrderResponse}
     */
    @Override
    public OrderResponse convert(Order source) {
        var productOrdersResponse = source.getProductOrders().stream()
                .map(productOrder -> {
                    final var product = productOrder.getProduct();
                    return ProductOrderResponse.builder()
                            .productId(product.getId())
                            .price(product.getPrice())
                            .name(product.getName())
                            .category(product.getClassification().getName())
                            .categoryId(product.getClassification().getValue())
                            .note(productOrder.getNote())
                            .quantity(productOrder.getCount())
                            .build();
                })
                .toList();

        return OrderResponse.builder()
                .id(source.getId())
                .orderNumber(source.getOrderNumber())
                .note(source.getNote())
                .createdAt(source.getCreatedAt())
                .status(source.getStatus().getName())
                .total(source.getTotal())
                .productOrders(productOrdersResponse)
                .tableDetailId(source.getTableDetail().getId())
                .build();
    }
}
