package com.kute.po;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import java.util.Comparator;

/**
 * Created by kute on 2017/10/14.
 */
public class Book implements Comparable<Book> {

    private int id;
    private String name;
    private Double price;
    private String address;

    public Book() {
    }

    public Book(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Book(int id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public int compareTo(Book o) {
        // 直到找到一个非0结果就返回
        return ComparisonChain.start()
                .compare(this.id, o.id)
                .compare(this.price, o.price)
                .compare(this.name, o.name, Ordering.natural().nullsFirst())
                .result();

    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                '}';
    }
}
