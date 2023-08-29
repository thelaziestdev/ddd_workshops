package com.example.payment;

import com.example.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Payment {

    public Payment(String id, String orderId, String currency) {
        this.id = id;
        this.orderId = orderId;
        this.totalPayed = Money.ZERO(currency);
    }

    @Getter
    @Id
    private String id;

    private String orderId;

    @Embedded
    @Getter
    private Money totalPayed;

    @OneToMany(cascade = CascadeType.ALL)
    @Getter
    private final List<PaymentRow> payments = new ArrayList<>();

    public void addPayment(PaymentRow paymentRow) {
        if(!paymentRow.getAmount().currency().equals(totalPayed.currency())) {
            throw new IllegalArgumentException("Cannot add different currencies");
        }
        payments.add(paymentRow);
        totalPayed = totalPayed.add(paymentRow.getAmount());
    }

}
