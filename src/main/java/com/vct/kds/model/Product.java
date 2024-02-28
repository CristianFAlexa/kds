package com.vct.kds.model;

import com.vct.kds.model.type.ProductClassificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Enumerated
    @Column(name = "classification", columnDefinition = "smallint")
    private ProductClassificationType classification;

    @Column(name = "price")
    private double price;

    public Product(String name, ProductClassificationType classification, Double price) {
        this.name = name;
        this.classification = classification;
        this.price = price;
    }
}
