package com.example.payment;

import com.example.Money;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
