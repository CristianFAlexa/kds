package com.vct.kds.web.response;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ActiveOrderResponse(
        String status,
        int orderNumber,
        Instant createdAt,
        String note,
        double total
) {
}
