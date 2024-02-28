package com.vct.kds.model.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TableStatusType {
    FREE("Free", (short) 0), ORDERING("Ordering", (short) 1), FINISHED("Finished", (short) 2);
    private final String name;
    private final short value;
}
