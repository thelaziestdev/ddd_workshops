package com.example.payment;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentsRepository implements PanacheRepositoryBase<Payment, String> {
}
