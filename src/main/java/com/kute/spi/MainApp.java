package com.kute.spi;

import com.kute.spi.service.Command;

import java.util.ServiceLoader;

/**
 * created by bailong001 on 2019/09/27 14:32
 */
public class MainApp {

    public static void main(String[] args) {
        ServiceLoader<Command> loader = ServiceLoader.load(Command.class);
        for (Command command : loader) {
            command.execute();
        }
        System.out.println(Integer.valueOf("#".toCharArray()[0]).intValue());
    }
}
