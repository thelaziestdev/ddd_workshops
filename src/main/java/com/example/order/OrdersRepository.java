package com.example.order;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrdersRepository implements PanacheRepositoryBase<Order, String> {
}
