package com.kute.jooq.joor;

import com.google.common.truth.Truth;
import org.joor.Reflect;
import org.junit.Test;

import java.util.Date;

/**
 * created by bailong001 on 2019/05/07 09:28
 */
public class JoorTest {

    @Test
    public void test() {

        String className = "com.kute.jooq.joor.MyService";

        MyService t1 = Reflect.on(className).create().get();
        Truth.assertThat(t1).isInstanceOf(MyService.class);

        t1 = Reflect.on(className).create("kute").get();
        Truth.assertThat(t1).isInstanceOf(MyService.class);
        Truth.assertThat(t1.getS()).isEqualTo("kute");
        Truth.assertThat(t1.getD()).isNull();

        MyService t2 = Reflect.on(t1).create("bai", new Date()).get();
        Truth.assertThat(t2).isInstanceOf(MyService.class);
        Truth.assertThat(t2.hashCode()).isNotEqualTo(t1.hashCode());
        Truth.assertThat(t2.getS()).isEqualTo("bai");
        Truth.assertThat(t2.getD()).isNotNull();

        String s = Reflect.on(t2).call("getS").get();
        Truth.assertThat(s).isEqualTo("bai");

        s = Reflect.on(t2).field("s").get();
        Truth.assertThat(s).isEqualTo("bai");

        s = Reflect.on(t2).set("s", "bai2").get("s");
        Truth.assertThat(s).isEqualTo("bai2");

        MyService.InnerClass i1 = Reflect.on(t2).field("innerClass").create("p").get();
        Truth.assertThat(i1).isInstanceOf(MyService.InnerClass.class);

        String lash = Reflect.on(i1).call("getLash").get();
        Truth.assertThat(lash).isNotEmpty();

    }

}
