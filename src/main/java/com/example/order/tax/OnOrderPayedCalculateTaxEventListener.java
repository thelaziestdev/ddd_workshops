package com.example.order.tax;

import com.example.order.OrderPayedEvent;
import com.example.order.OrdersRepository;
import io.quarkus.vertx.ConsumeEvent;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OnOrderPayedCalculateTaxEventListener {

    OrdersRepository ordersRepository;

    public OnOrderPayedCalculateTaxEventListener(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @ConsumeEvent("order-payed")
    public void onOrderPayed(OrderPayedEvent event) {
        var order = ordersRepository.findById(event.getOrderId());
        order.calculateTax();
        ordersRepository.persist(order);
    }

}
