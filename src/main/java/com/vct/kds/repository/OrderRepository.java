package com.vct.kds.repository;

import com.vct.kds.model.Order;
import com.vct.kds.model.type.OrderStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> getAllByStatusIn(Collection<OrderStatusType> statusType);

    @Modifying
    @Query(value = """
            update order_detail
            set status =:status
            where id =:id""", nativeQuery = true)
    void updateByIdWithStatus(@Param(value = "id") long id, @Param(value = "status") OrderStatusType status);
}