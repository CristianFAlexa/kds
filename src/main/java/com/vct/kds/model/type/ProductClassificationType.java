package com.vct.kds.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductClassificationType {
    DESSERT("Dessert", (short) 0),
    STARTERS("Starters", (short) 1),
    MAIN_COURSE("Main course", (short) 2),
    PIZZA("Pizza", (short) 3),
    BEVERAGES("Beverages", (short) 4);
    private final String name;
    private final short value;
}
