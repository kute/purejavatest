package com.kute.util.thread.execute;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2239704704179219526L;

    private int id;
    
    private String name;

    private int count;

    public User() {
    }

    public User(int id, String name, int count) {
        super();
        this.id = id;
        this.name = name;
        this.count = count;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
