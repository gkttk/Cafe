package com.github.gkttk.epam.model.entities;

import com.github.gkttk.epam.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order extends Entity {
    private final BigDecimal cost;
    private final LocalDateTime date;
    private final OrderStatus status;
    private final Long userId;


    public Order(Long id, BigDecimal cost, LocalDateTime date, Long userId){
        super(id);
        this.cost = cost;
        this.date = date;
        this.userId = userId;
        this.status = OrderStatus.ACTIVE; //default value
    }

    public Order(Long id, BigDecimal cost, LocalDateTime date, OrderStatus status, Long userId) {
        super(id);
        this.cost = cost;
        this.date = date;
        this.status = status;
        this.userId = userId;
    }


    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return super.getId();
    }

    public BigDecimal getCost() {
        return cost;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(cost, order.cost) &&
                Objects.equals(date, order.date) &&
                Objects.equals(status, order.status) &&
                Objects.equals(userId, order.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cost, date, status, userId);
    }

    @Override
    public String toString() {
        return String.format("Order id: %d, cost: %f, date: %3$td-%3$tm-%3$tY %3$tH:%3$tM:%3$tS, status: %s",
                getId(), cost, date, status.name());
    }
}
