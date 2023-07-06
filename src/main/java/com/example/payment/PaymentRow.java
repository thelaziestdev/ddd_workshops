package com.example.payment;

import com.example.order.Money;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRow {

    @Id
    private String id;

    @Embedded
    @Getter
    private Money amount;

    @Enumerated
    PaymentMethod paymentMethod;
}
