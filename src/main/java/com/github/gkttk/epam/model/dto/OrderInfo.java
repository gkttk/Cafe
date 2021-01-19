package com.github.gkttk.epam.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Current class represents data from orders and dishes tables.
 */
public class OrderInfo {

    private final BigDecimal cost;
    private final LocalDateTime date;
    private final List<String> dishNames;

    public OrderInfo(BigDecimal cost, LocalDateTime date, List<String> dishNames) {
        this.cost = cost;
        this.date = date;
        this.dishNames = Collections.unmodifiableList(dishNames);
    }


    public BigDecimal getCost() {
        return cost;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<String> getDishNames() {
        return dishNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderInfo orderInfo = (OrderInfo) o;
        return Objects.equals(cost, orderInfo.cost) &&
                Objects.equals(date, orderInfo.date) &&
                Objects.equals(dishNames, orderInfo.dishNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cost, date, dishNames);
    }


}
