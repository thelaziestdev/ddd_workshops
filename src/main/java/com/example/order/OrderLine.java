package com.example.order;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public
class OrderLine {

    @Id
    @Getter
    private String id;

    @Getter
    private String productId;

    @Getter
    private String productName;

    @Getter
    @Setter
    private Integer quantity;

    @Embedded
    @Getter
    @Setter
    private Money price;

}
