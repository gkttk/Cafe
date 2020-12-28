package com.github.gkttk.epam.controller.listener;

import com.github.gkttk.epam.exceptions.ServiceException;
import com.github.gkttk.epam.logic.service.OrderService;
import com.github.gkttk.epam.logic.service.impl.OrderServiceImpl;
import com.github.gkttk.epam.model.entities.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyListener implements ServletContextListener {

    private ScheduledExecutorService scheduledExecutorService;
    private OrderService orderService = new OrderServiceImpl();

    private final static Logger LOGGER = LogManager.getLogger(MyListener.class);




    private void blockOrders(List<Order> orders) throws ServiceException {
        for (Order order : orders) {
            orderService.blockOrder(order);
            LOGGER.info("Change order" + order.toString());
        }
    }


    private Runnable initRunnable() {
        return () -> {
            try {
                List<Order> ordersWithExpiredDate = orderService.getAllActiveWithExpiredDate();
                if (!ordersWithExpiredDate.isEmpty()) {
                    blockOrders(ordersWithExpiredDate);

                }
            } catch (ServiceException e) {
                LOGGER.error("Can't init runnable in listener", e);
            }
        };
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Runnable runnable = initRunnable();
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(runnable, 0, 1, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        this.scheduledExecutorService.shutdown();
    }
}
