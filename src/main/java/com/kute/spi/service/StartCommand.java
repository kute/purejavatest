package com.kute.spi.service;

/**
 * created by bailong001 on 2019/09/27 14:31
 */
public class StartCommand implements Command {
    @Override
    public void execute() {
        System.out.println("start command");
    }
}
