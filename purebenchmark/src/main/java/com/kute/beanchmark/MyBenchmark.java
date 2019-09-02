/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.kute.beanchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class MyBenchmark {

    // 所有线程共享
    @State(Scope.Benchmark)
    public static class ShareObject {
        volatile AtomicInteger x = new AtomicInteger(0);
    }

    // 每个线程独有一份copy
    @State(Scope.Thread)
    public static class ThreadObject {
        volatile AtomicInteger x = new AtomicInteger(0);
    }


    @Benchmark
    @BenchmarkMode({Mode.All})
    // 预热20轮，正式测量10轮，而每次都是先执行完预热再执行正式计量，也可以用 OptionsBuilder 做全局配置，这里是单个方法的
    @Fork(value = 10, warmups = 20)
    // 单位
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int testMethod(ShareObject shareObject) {
        return shareObject.x.incrementAndGet();
    }

    @Benchmark
    @BenchmarkMode({Mode.Throughput})
    // 预热20轮，正式测量10轮，而每次都是先执行完预热再执行正式计量，也可以用 OptionsBuilder 做全局配置，这里是单个方法的
    @Fork(value = 10, warmups = 20)
    public int testMethod2(ThreadObject threadObject) {
        return threadObject.x.incrementAndGet();
    }

    public static void main(String[] args) throws RunnerException {
        new Runner(
                new OptionsBuilder()
                        // 测试范围
                        .include(MyBenchmark.class.getSimpleName())
                        // 预热5轮
                        .warmupIterations(2)
                        // 正式测量10轮
                        .measurementIterations(2)
                        // 3轮测试
                        .forks(1)
                        .build())
                .run();
    }

}
