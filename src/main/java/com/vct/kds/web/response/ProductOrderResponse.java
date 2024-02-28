package com.vct.kds.web.response;

import lombok.Builder;

@Builder
public record ProductOrderResponse(
        int quantity,
        String name,
        String note,
        double price,
        String category,
        short categoryId,
        long productId
) {
}
