package com.github.gkttk.epam.model.builder;

import com.github.gkttk.epam.model.entities.Order;
import com.github.gkttk.epam.model.entities.User;
import com.github.gkttk.epam.model.enums.OrderStatus;
import com.github.gkttk.epam.model.enums.UserRole;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderBuilder implements Builder<Order> {

    private final Long id; //immutable even here
    private  BigDecimal cost;
    private  LocalDateTime date;
    private  OrderStatus status;
    private  Long userId;

    public OrderBuilder(Order order) {
        this.id = order.getId();
        this.cost = order.getCost();
        this.date = order.getDate();
        this.status = order.getStatus();
        this.userId = order.getUserId();
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public Order build() {
        return new Order(
                this.id,
                this.cost,
                this.date,
                this.status,
                this.userId
        );
    }
}
