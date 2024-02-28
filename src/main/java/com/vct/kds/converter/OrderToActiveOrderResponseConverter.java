package com.vct.kds.converter;

import com.vct.kds.model.Order;
import com.vct.kds.web.response.ActiveOrderResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToActiveOrderResponseConverter implements Converter<Order, ActiveOrderResponse> {

    /**
     * Convenience method used to convert a {@link Order} into a {@link ActiveOrderResponse}
     */
    @Override
    public ActiveOrderResponse convert(Order source) {
        return ActiveOrderResponse.builder()
                .status(source.getStatus().getName())
                .total(source.getTotal())
                .createdAt(source.getCreatedAt())
                .note(source.getNote())
                .orderNumber(source.getOrderNumber())
                .build();
    }
}
