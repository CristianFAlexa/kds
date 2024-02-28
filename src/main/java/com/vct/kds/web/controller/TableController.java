package com.vct.kds.web.controller;

import com.vct.kds.service.TableService;
import com.vct.kds.web.request.TableRequest;
import com.vct.kds.web.response.CurrentTableOrderResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/table", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Slf4j
public class TableController {

    private final TableService tableService;

    @PutMapping
    public ResponseEntity<Void> updateTableStatus(@RequestBody @Valid TableRequest tableRequest) {
        log.debug("Update table status.");
        tableService.updateTableStatus(tableRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{tableId}")
    public ResponseEntity<CurrentTableOrderResponse> getCurrentTableOrder(@PathVariable(value = "tableId") @NotNull Long tableId) {
        log.debug("Get table order.");
        var currentOrder = tableService.getCurrentTableOrder(tableId);
        return ResponseEntity.ok(currentOrder);
    }
}
