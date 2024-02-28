package com.vct.kds.service;

import com.vct.kds.converter.NullSafeConverter;
import com.vct.kds.model.Order;
import com.vct.kds.model.ProductOrder;
import com.vct.kds.model.type.OrderStatusType;
import com.vct.kds.repository.OrderRepository;
import com.vct.kds.repository.ProductRepository;
import com.vct.kds.repository.TableDetailsRepository;
import com.vct.kds.web.error.exeption.InvalidStateException;
import com.vct.kds.web.error.exeption.OrderNotFoundException;
import com.vct.kds.web.error.exeption.TableNotFoundException;
import com.vct.kds.web.request.NewOrderRequest;
import com.vct.kds.web.request.OrderStatusRequest;
import com.vct.kds.web.response.ActiveOrderResponse;
import com.vct.kds.web.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.vct.kds.model.type.OrderStatusType.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final TableDetailsRepository tableDetailsRepository;
    private final ProductRepository productRepository;
    private final NullSafeConverter nullSafeConverter;

    @Override
    @Transactional
    public void updateOrderStatus(@NonNull OrderStatusRequest orderRequest) {
        final var orderDetail = orderRepository.findById(orderRequest.orderId())
                .orElseThrow(() -> new OrderNotFoundException("Order with id: " + orderRequest.orderId() + " does not exist."));

        handleOrderState(orderRequest.status(), orderDetail.getStatus(), orderDetail.getId());
    }

    protected void handleOrderState(
            @NonNull OrderStatusType currentState,
            @NonNull OrderStatusType previousState,
            @NonNull Long orderId
    ) {
        switch (currentState) {
            case PENDING -> handlePendingState(previousState, orderId);
            case STARTED -> handleStartedState(previousState, orderId);
            case FINISHED -> handleFinishedState(previousState, orderId);
        }
    }

    private void handlePendingState(@NonNull OrderStatusType previousState, @NonNull Long orderId) {
        if (previousState.equals(PENDING)) {
            log.warn("Order status is already set as pending!");
            return;
        }
        if (previousState.equals(STARTED)) {
            throw new InvalidStateException("Cannot set order status to pending from previous state started.");
        }
        if (previousState.equals(FINISHED)) {
            orderRepository.updateByIdWithStatus(orderId, PENDING);
        }
    }

    private void handleStartedState(@NonNull OrderStatusType previousState, @NonNull Long orderId) {
        if (previousState.equals(STARTED)) {
            log.warn("Order status is already set as started!");
            return;
        }
        if (previousState.equals(PENDING)) {
            orderRepository.updateByIdWithStatus(orderId, STARTED);
        }
        if (previousState.equals(FINISHED)) {
            throw new InvalidStateException("Cannot set order status to started from previous state finished.");
        }
    }

    private void handleFinishedState(@NonNull OrderStatusType previousState, @NonNull Long orderId) {
        if (previousState.equals(FINISHED)) {
            log.warn("Order status is already set as finished!");
            return;
        }
        if (previousState.equals(STARTED)) {
            orderRepository.updateByIdWithStatus(orderId, FINISHED);
        }
        if (previousState.equals(PENDING)) {
            throw new InvalidStateException("Cannot set order status to finished from previous state pending.");
        }
    }

    @Override
    @Transactional
    public @NonNull OrderResponse createNewOrder(@NonNull NewOrderRequest newOrderRequest) {
        final var tableDetails = tableDetailsRepository.findById(newOrderRequest.tableId())
                .orElseThrow(() -> new TableNotFoundException("Could not find table with id."));
        final var productOrders = assembleProductOrders(newOrderRequest);

        var order = new Order(newOrderRequest.orderNumber(), newOrderRequest.note());
        order.setTableDetail(tableDetails);
        order.setTotal(computeTotalPaymentPerOrder(productOrders));
        order.setProductOrders(productOrders);

        final var persistedOrder = orderRepository.save(order);

        return nullSafeConverter.convert(persistedOrder, OrderResponse.class);
    }

    private @NonNull Set<ProductOrder> assembleProductOrders(@NonNull NewOrderRequest newOrderRequest) {
        return newOrderRequest.productOrders()
                .stream()
                .map(productOrderRequest -> {
                    final var product = productRepository.findById(productOrderRequest.productId())
                            .orElseThrow();
                    var productOrder = new ProductOrder(productOrderRequest.count(), newOrderRequest.note());
                    productOrder.setProduct(product);
                    return productOrder;
                })
                .collect(Collectors.toSet());
    }

    protected double computeTotalPaymentPerOrder(@NonNull Set<ProductOrder> productOrders) {
        return productOrders.stream()
                .map(po -> po.getCount() * po.getProduct().getPrice())
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    @Override
    public @NonNull List<ActiveOrderResponse> getAllActiveOrders() {
        List<Order> activeOrders = orderRepository.getAllByStatusIn(Set.of(PENDING, STARTED));
        return activeOrders.stream()
                .map(order -> nullSafeConverter.convert(order, ActiveOrderResponse.class))
                .toList();
    }
}
