package com.kute.google.guava.eventbus.event;

import java.io.Serializable;

/**
 * created by kute on 2018/02/04 09:48
 * 订单 事件
 */
public class OrderMessageEvent implements Serializable {

    private static final long serialVersionUID = 4255568092523575372L;
    private Integer orderId;

    private Double price;

    private String message;

    public OrderMessageEvent() {
    }

    public OrderMessageEvent(String message, Integer orderId, Double price) {
        this.message = message;
        this.orderId = orderId;
        this.price = price;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "OrderMessageEvent{" +
                "orderId=" + orderId +
                ", price=" + price +
                ", message='" + message + '\'' +
                '}';
    }
}
