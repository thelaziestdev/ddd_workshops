package com.example.command;

import com.example.Money;
import com.example.order.OrdersRepository;
import com.example.payment.*;
import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.UUID;

public record PayForOrderCommand(String orderId, Money amount, PaymentMethod method) {}

@ApplicationScoped
class Handler {

    private final PaymentService paymentService;
    private final OrdersRepository ordersRepository;

    private final PaymentsRepository paymentsRepository;
    private final EventBus eventBus;

    Handler(PaymentService paymentService, OrdersRepository ordersRepository, PaymentsRepository paymentsRepository, EventBus eventBus) {
        this.paymentService = paymentService;
        this.ordersRepository = ordersRepository;
        this.paymentsRepository = paymentsRepository;
        this.eventBus = eventBus;
    }

    @Transactional
    public void handle(PayForOrderCommand command) {
        var order = ordersRepository.findById(command.orderId());
        var payment = createIfNotExists(command.orderId(), order.getTotalAmount().currency());
        paymentService.addPayment(payment, order, new PaymentRow(UUID.randomUUID().toString(), command.amount(), command.method()));
        paymentsRepository.persist(payment);
        ordersRepository.persist(order);
        order.getEvents().forEach( event -> eventBus.send(event.getEventName(), event));
    }


    private Payment createIfNotExists(String orderId, String currency) {
        return paymentsRepository.findByIdOptional(orderId).orElseGet(() -> new Payment(UUID.randomUUID().toString(), orderId, currency));
    }
}
