package com.vct.kds.web.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vct.kds.model.type.TableStatusType;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TableRequest(
        @NotNull(message = "TableId should not be null!")
        Long id,
        @NotNull(message = "Table status type should not be null!")
        TableStatusType status
) {
}
