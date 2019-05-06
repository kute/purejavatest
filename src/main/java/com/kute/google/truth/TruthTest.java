package com.kute.google.truth;

import com.google.common.collect.Lists;
// for assertions on Java 8 types
import com.google.common.truth.Truth8;
import org.junit.Test;

import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;


/**
 * created by bailong001 on 2019/05/06 20:28
 */
public class TruthTest {


    @Test
    public void test() {

        String s = "asd";

        List<String> list = Lists.newArrayList("a", "b");

        assertThat(s).contains("s");

        IntStream intStream = IntStream.empty();
        Truth8.assertThat(intStream).isEmpty();

        assertWithMessage("hahah")
                .that(s)
                .contains("a");

        assertThat(list).containsExactly("a", "b")
                .inOrder();


    }


}
