package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;

import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet extends Observable {

    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    private final int number;

    public Tablet(int number) {
        this.number = number;
    }

    public Order createOrder(){ // было void ??
        Order order;
        try {
            order = new Order(this);
            if (!order.isEmpty()) {
                ConsoleHelper.writeMessage(order.toString());
                setChanged();
                notifyObservers(order);
                try {
                    new AdvertisementManager(order.getTotalCookingTime() * 60).processVideos();
                } catch (NoVideoAvailableException ex) {
                    logger.log(Level.INFO, "No video is available for the order" + order);
                }
            }
        } catch (IOException e) { //??
            logger.log(Level.SEVERE, "Console is unavailable.");
            return null;
        }
        return order;
    }

    @Override
    public String toString() {
        return "Tablet{number=" + number + "}";
    }
}
