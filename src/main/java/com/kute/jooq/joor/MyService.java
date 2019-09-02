package com.kute.jooq.joor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

/**
 * created by bailong001 on 2019/05/07 09:40
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class MyService {

    private String s;
    private Date d;
    private InnerClass innerClass;

    public MyService(String s) {
        this.s = s;
    }

    public MyService(Date d) {
        this.d = d;
    }

    public MyService(String s, Date d) {
        this.s = s;
        this.d = d;
    }

    public InnerClass getInnerClass() {
        return new InnerClass(RandomStringUtils.randomAlphabetic(10));
    }

    @Data
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InnerClass {
        private String lash;
    }

}
