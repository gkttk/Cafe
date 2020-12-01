package com.github.gkttk.epam.model;

import com.github.gkttk.epam.model.enums.UserRole;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order extends Entity {
    private final Long id;
    private final BigDecimal cost;
    private final LocalDateTime time;
    private boolean active;

    public Order(Long id, BigDecimal cost, LocalDateTime time, boolean active) {
        this.id = id;
        this.cost = cost;
        this.time = time;
        this.active = active;
    }

    public Long getId() {
        return id;
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
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Order order = (Order) o;
        return active == order.active &&
                Objects.equals(id, order.id) &&
                Objects.equals(cost, order.cost) &&
                Objects.equals(time, order.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cost, time, active);
    }

    @Override
    public String toString() {
        return String.format("Order id: %d, cost: %f, time: %Tc, is active: %b", id, cost, time, active);
    }
}
