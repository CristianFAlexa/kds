package com.vct.kds.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderType {
    PICKUP("pickup", (short) 0), DINEIN("dinein", (short) 1);
    private final String name;
    private final short value;
}
