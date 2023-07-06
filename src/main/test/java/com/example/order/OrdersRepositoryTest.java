package com.example.order;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

@QuarkusTest
public class OrdersRepositoryTest {

    @BeforeEach
    @Transactional
    void cleanUp() {
        ordersRepository.deleteAll();
    }

    @Inject
    OrdersRepository ordersRepository;

    @Test
    @Transactional
    void canPersistOrder() {
        // given
        var order = new Order(UUID.randomUUID().toString(), "USD");

        // when
        ordersRepository.persist(order);

        // test
        Assertions.assertEquals(order.getId(), ordersRepository.findAll().list().stream().findFirst().get().getId());

    }

    @Test
    @Transactional
    void canPersistOrderWithLines() {
        // given
        var order = new Order(UUID.randomUUID().toString(), "USD");
        var orderLine = new OrderLine(UUID.randomUUID().toString(), "2", "Beer", 1, new Money(BigDecimal.TEN, "USD"));
        order.addLine(orderLine);

        // when
        ordersRepository.persist(order);

        // test
        var persistedOrder = ordersRepository.findAll().list().stream().findFirst().get();
        Assertions.assertEquals(order.getId(), persistedOrder.getId());
        Assertions.assertEquals(orderLine.getId(), persistedOrder.getOrderLines().stream().findFirst().get().getId());

    }

}
