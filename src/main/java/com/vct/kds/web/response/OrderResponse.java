package com.vct.kds.web.response;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record OrderResponse(
        long id,
        int orderNumber,
        Instant createdAt,
        String note,
        String status,
        double total,
        List<ProductOrderResponse> productOrders,
        long tableDetailId
) {
}
