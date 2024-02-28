package com.vct.kds.model;


import com.vct.kds.model.type.OrderStatusType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_detail")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "order_number")
    private int orderNumber;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Enumerated
    @Column(name = "status", columnDefinition = "smallint")
    private OrderStatusType status;

    @Column(name = "total")
    private double total;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_detail_id", referencedColumnName = "id")
    private Set<ProductOrder> productOrders = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private TableDetail tableDetail;

    public Order(int orderNumber, String note) {
        this.orderNumber = orderNumber;
        this.createdAt = Instant.now();
        this.status = OrderStatusType.PENDING;
        this.note = note;
    }
}
