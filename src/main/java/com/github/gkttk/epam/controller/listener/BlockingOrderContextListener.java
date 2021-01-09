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

public class BlockingOrderContextListener implements ServletContextListener {

    private OrderService orderService = new OrderServiceImpl();
    private ScheduledExecutorService scheduledExecutorService;

    private final static int DEFAULT_INIT_DELAY = 0;
    private final static int DEFAULT_DELAY = 1;

    private final static Logger LOGGER = LogManager.getLogger(BlockingOrderContextListener.class);


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Runnable runnable = initRunnable();
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(runnable, DEFAULT_INIT_DELAY, DEFAULT_DELAY, TimeUnit.MINUTES);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        this.scheduledExecutorService.shutdown();
    }

    private void blockOrders(List<Order> orders) throws ServiceException {
        for (Order order : orders) {
            orderService.blockOrder(order);
            LOGGER.info("Block order {}", order);
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


}
