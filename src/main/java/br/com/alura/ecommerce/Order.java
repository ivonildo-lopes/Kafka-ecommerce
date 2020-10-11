package br.com.alura.ecommerce;

import lombok.Getter;

import java.math.BigDecimal;

public class Order {

    @Getter
    private final String userId;

    @Getter
    private final String orderId;

    @Getter
    private final BigDecimal amount;

    public Order(String userId, String orderId, BigDecimal amount) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
    }
}
