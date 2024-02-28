package com.vct.kds.model;

import com.vct.kds.model.type.TableStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "table_detail")
public class TableDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Enumerated
    @Column(name = "status", columnDefinition = "smallint")
    private TableStatusType status;

    @OneToMany(mappedBy = "tableDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waiter_id")
    private Waiter waiter;

    public TableDetail() {
        this.status = TableStatusType.FREE;
    }

    public TableDetail(TableStatusType status) {
        this.status = status;
    }
}
