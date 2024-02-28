package com.vct.kds.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatusType {
    PENDING("Pending", (short) 0), STARTED("Started", (short) 1), FINISHED("Finished", (short) 2);
    private final String name;
    private final short value;
}
