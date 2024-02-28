package com.vct.kds.web.response;

import java.util.List;

public record CurrentTableOrderResponse(
        String status,
        long tableNumber,
        String waiterName,
        long waiterId,
        List<OrderResponse> orders
) {
}
