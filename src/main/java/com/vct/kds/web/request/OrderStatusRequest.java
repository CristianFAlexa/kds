package com.vct.kds.web.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vct.kds.model.type.OrderStatusType;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderStatusRequest(
        @NotNull(message = "OrderId should not be null!")
        Long orderId,
        @NotNull(message = "Order status type should not be null!")
        OrderStatusType status
) {
}
