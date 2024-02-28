package com.vct.kds.web.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductOrderRequest(
        @NotNull(message = "Product id should not be null!")
        Long productId,
        @NotNull(message = "Product id should not be null!")
        Integer count,
        @Nullable
        String note
) {
}
