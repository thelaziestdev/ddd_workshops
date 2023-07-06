package com.example.payment;

import com.example.order.Order;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentService {

    public void addPayment(Payment payment, Order order, PaymentRow paymentRow) {
        payment.addPayment(paymentRow);
        order.pay(paymentRow.getAmount());
    }

}
