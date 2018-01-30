package com.kute.util.date;

import org.joda.time.DateTime;

public class DateUtil {

    public static void main(String[] args) {
        System.out.println(new DateTime().minusDays(1).toDate());
    }

}
