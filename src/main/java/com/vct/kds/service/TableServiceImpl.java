package com.vct.kds.service;

import com.vct.kds.converter.NullSafeConverter;
import com.vct.kds.model.TableDetail;
import com.vct.kds.model.type.OrderStatusType;
import com.vct.kds.model.type.TableStatusType;
import com.vct.kds.repository.TableDetailsRepository;
import com.vct.kds.web.error.exeption.InvalidStateException;
import com.vct.kds.web.error.exeption.TableNotFoundException;
import com.vct.kds.web.request.TableRequest;
import com.vct.kds.web.response.CurrentTableOrderResponse;
import com.vct.kds.web.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TableServiceImpl implements TableService {

    private final TableDetailsRepository tableDetailsRepository;
    private final NullSafeConverter nullSafeConverter;

    @Override
    @Transactional
    public void updateTableStatus(@NonNull TableRequest tableRequest) {
        final var tableDetail = tableDetailsRepository.findById(tableRequest.id())
                .orElseThrow(() -> new TableNotFoundException("Table with id: " + tableRequest.id() + " does not exist."));
        handleTableState(tableRequest.status(), tableDetail.getStatus(), tableDetail);
    }

    protected void handleTableState(
            @NonNull TableStatusType currentState,
            @NonNull TableStatusType previousState,
            @NonNull TableDetail tableDetail
    ) {
        switch (currentState) {
            case FREE -> handleFreeState(previousState, tableDetail);
            case ORDERING -> handleOrderingState(previousState, tableDetail);
            case FINISHED -> handleFinishedState(previousState, tableDetail);
        }
    }

    protected void handleFreeState(@NonNull TableStatusType previousState, @NonNull TableDetail tableDetail) {
        if (previousState.equals(TableStatusType.FREE)) {
            log.warn("Table status is already set as free!");
            return;
        }
        if (previousState.equals(TableStatusType.ORDERING)) {
            throw new InvalidStateException("Cannot set table status to free from previous state ordering.");
        }
        if (previousState.equals(TableStatusType.FINISHED)) {
            tableDetail.setStatus(TableStatusType.FREE);
            tableDetail.getOrders().clear();
            tableDetailsRepository.save(tableDetail);
        }
    }

    protected void handleOrderingState(@NonNull TableStatusType previousState, @NonNull TableDetail tableDetail) {
        if (previousState.equals(TableStatusType.ORDERING)) {
            log.warn("Table status is already set as ordering!");
            return;
        }
        if (previousState.equals(TableStatusType.FREE)) {
            tableDetail.setStatus(TableStatusType.ORDERING);
            tableDetailsRepository.save(tableDetail);
        }
        if (previousState.equals(TableStatusType.FINISHED)) {
            throw new InvalidStateException("Cannot set table status to ordering from previous state finished.");
        }
    }

    protected void handleFinishedState(@NonNull TableStatusType previousState, @NonNull TableDetail tableDetail) {
        if (previousState.equals(TableStatusType.FINISHED)) {
            log.warn("Table status is already set as finished!");
            return;
        }
        if (previousState.equals(TableStatusType.FREE)) {
            throw new InvalidStateException("Cannot set table status to free from previous state finished.");
        }
        if (previousState.equals(TableStatusType.ORDERING)) {
            tableDetail.setStatus(TableStatusType.FINISHED);
            tableDetailsRepository.save(tableDetail);
        }
    }

    @Override
    public @NonNull CurrentTableOrderResponse getCurrentTableOrder(@NonNull Long tableId) {
        final var tableDetail = tableDetailsRepository.findById(tableId)
                .orElseThrow(() -> new TableNotFoundException("Table with id: " + tableId + " does not exist."));
        final var waiter = tableDetail.getWaiter();
        var currentOrderStatus = tableDetail.getOrders()
                .stream()
                .filter(order -> OrderStatusType.STARTED.equals(order.getStatus()) || OrderStatusType.PENDING.equals(order.getStatus()))
                .map(order -> nullSafeConverter.convert(order, OrderResponse.class))
                .toList();
        return new CurrentTableOrderResponse(
                tableDetail.getStatus().getName(),
                tableDetail.getId(),
                waiter.getName(),
                waiter.getId(),
                currentOrderStatus
        );
    }
}
