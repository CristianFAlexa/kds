package com.vct.kds.web.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NewOrderRequest(
        @NotNull(message = "Order number should not be null!")
        Integer orderNumber,
        @Nullable
        String note,
        @NotNull(message = "Table id should not be null!")
        Long tableId,
        List<@Valid ProductOrderRequest> productOrders
) {
}
