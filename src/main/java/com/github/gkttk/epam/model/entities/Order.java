package com.github.gkttk.epam.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order extends Entity {
    private final BigDecimal cost;
    private final LocalDateTime time;
    private final boolean active;
    private final Long userId;

    public Order(Long id, BigDecimal cost, LocalDateTime time, boolean active, Long userId) {
        super(id);
        this.cost = cost;
        this.time = time;
        this.active = active;
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

    public LocalDateTime getTime() {
        return time;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return active == order.active &&
                Objects.equals(cost, order.cost) &&
                Objects.equals(time, order.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cost, time, active);
    }

    @Override
    public String toString() {
        return String.format("Order id: %d, cost: %f, time: %3$td-%3$tm-%3$tY %3$tH:%3$tM:%3$tS, is active: %b", getId(), cost, time, active);
    }
}
