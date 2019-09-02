package com.kute.beanchmark.demo;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 循环 正向 与 逆向 测试，即 正序遍历循环 和 倒序遍历循环，通常 倒序遍历会优于 正序遍历
 * i > 0优于i < length，因此倒序有优势
 * created by bailong001 on 2019/07/14 11:10
 */
public class LoopBenchmark {

    /**
     * 标记为共享对象
     */
    @State(value = Scope.Benchmark)
    public static class Loop {
        volatile int loop = 10_0000_0000;
    }

    @Benchmark
    @BenchmarkMode({Mode.All})
    public void positiveLoop(Loop loop) {
        for (int i = 0; i < loop.loop; i++) {

        }
    }

    @Benchmark
    @BenchmarkMode({Mode.All})
    public void negtiveLoop(Loop loop) {
        for (int i = loop.loop; i > 0; i--) {

        }
    }

    public static void main(String[] args) throws RunnerException {
        new Runner(
                new OptionsBuilder()
                        .include(LoopBenchmark.class.getSimpleName())
                        // 预热5轮
                        .warmupIterations(2)
                        // 正式测量10轮
                        .measurementIterations(2)
                        // 3轮测试
                        .forks(1)
                        .build())
                .run();
    }

    /**
     * 如下为运行测试报告
     */
    /**
     # JMH version: 1.21
     # VM version: JDK 1.8.0_202, Java HotSpot(TM) 64-Bit Server VM, 25.202-b08
     # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/bin/java
     # VM options: -Dvisualvm.id=928550255869878 -Dfile.encoding=UTF-8
     # Warmup: 2 iterations, 10 s each
     # Measurement: 2 iterations, 10 s each
     # Timeout: 10 min per iteration
     # Threads: 1 thread, will synchronize iterations
     # Benchmark mode: Throughput, ops/time
     # Benchmark: com.kute.beanchmark.LoopBenchmark.negtiveLoop

     # Run progress: 0.00% complete, ETA 00:04:00
     # Fork: 1 of 1
     # Warmup Iteration   1: 2409522101.558 ops/s
     # Warmup Iteration   2: 2718287278.446 ops/s
     Iteration   1: 2550521891.729 ops/s
     Iteration   2: 2727684661.040 ops/s


     Result "com.kute.beanchmark.LoopBenchmark.negtiveLoop":
     2639103276.384 ops/s


     # JMH version: 1.21
     # VM version: JDK 1.8.0_202, Java HotSpot(TM) 64-Bit Server VM, 25.202-b08
     # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/bin/java
     # VM options: -Dvisualvm.id=928550255869878 -Dfile.encoding=UTF-8
     # Warmup: 2 iterations, 10 s each
     # Measurement: 2 iterations, 10 s each
     # Timeout: 10 min per iteration
     # Threads: 1 thread, will synchronize iterations
     # Benchmark mode: Throughput, ops/time
     # Benchmark: com.kute.beanchmark.LoopBenchmark.positiveLoop

     # Run progress: 16.67% complete, ETA 00:03:24
     # Fork: 1 of 1
     # Warmup Iteration   1: 2.490 ops/s
     # Warmup Iteration   2: 2.664 ops/s
     Iteration   1: 2.574 ops/s
     Iteration   2: 2.688 ops/s


     Result "com.kute.beanchmark.LoopBenchmark.positiveLoop":
     2.631 ops/s


     # JMH version: 1.21
     # VM version: JDK 1.8.0_202, Java HotSpot(TM) 64-Bit Server VM, 25.202-b08
     # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/bin/java
     # VM options: -Dvisualvm.id=928550255869878 -Dfile.encoding=UTF-8
     # Warmup: 2 iterations, 10 s each
     # Measurement: 2 iterations, 10 s each
     # Timeout: 10 min per iteration
     # Threads: 1 thread, will synchronize iterations
     # Benchmark mode: Average time, time/op
     # Benchmark: com.kute.beanchmark.LoopBenchmark.negtiveLoop

     # Run progress: 33.33% complete, ETA 00:02:44
     # Fork: 1 of 1
     # Warmup Iteration   1: ≈ 10⁻⁹ s/op
     # Warmup Iteration   2: ≈ 10⁻⁹ s/op
     Iteration   1: ≈ 10⁻⁹ s/op
     Iteration   2: ≈ 10⁻⁹ s/op


     Result "com.kute.beanchmark.LoopBenchmark.negtiveLoop":
     ≈ 10⁻⁹ s/op


     # JMH version: 1.21
     # VM version: JDK 1.8.0_202, Java HotSpot(TM) 64-Bit Server VM, 25.202-b08
     # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/bin/java
     # VM options: -Dvisualvm.id=928550255869878 -Dfile.encoding=UTF-8
     # Warmup: 2 iterations, 10 s each
     # Measurement: 2 iterations, 10 s each
     # Timeout: 10 min per iteration
     # Threads: 1 thread, will synchronize iterations
     # Benchmark mode: Average time, time/op
     # Benchmark: com.kute.beanchmark.LoopBenchmark.positiveLoop

     # Run progress: 50.00% complete, ETA 00:02:02
     # Fork: 1 of 1
     # Warmup Iteration   1: 0.383 s/op
     # Warmup Iteration   2: 0.389 s/op
     Iteration   1: 0.340 s/op
     Iteration   2: 0.337 s/op


     Result "com.kute.beanchmark.LoopBenchmark.positiveLoop":
     0.339 s/op


     # JMH version: 1.21
     # VM version: JDK 1.8.0_202, Java HotSpot(TM) 64-Bit Server VM, 25.202-b08
     # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/bin/java
     # VM options: -Dvisualvm.id=928550255869878 -Dfile.encoding=UTF-8
     # Warmup: 2 iterations, 10 s each
     # Measurement: 2 iterations, 10 s each
     # Timeout: 10 min per iteration
     # Threads: 1 thread, will synchronize iterations
     # Benchmark mode: Sampling time
     # Benchmark: com.kute.beanchmark.LoopBenchmark.negtiveLoop

     # Run progress: 66.66% complete, ETA 00:01:22
     # Fork: 1 of 1
     # Warmup Iteration   1: ≈ 10⁻⁷ s/op
     # Warmup Iteration   2: ≈ 10⁻⁷ s/op
     Iteration   1: ≈ 10⁻⁷ s/op
     negtiveLoop·p0.00:   ≈ 10⁻⁷ s/op
     negtiveLoop·p0.50:   ≈ 10⁻⁷ s/op
     negtiveLoop·p0.90:   ≈ 10⁻⁷ s/op
     negtiveLoop·p0.95:   ≈ 10⁻⁷ s/op
     negtiveLoop·p0.99:   ≈ 10⁻⁷ s/op
     negtiveLoop·p0.999:  ≈ 10⁻⁶ s/op
     negtiveLoop·p0.9999: ≈ 10⁻⁴ s/op
     negtiveLoop·p1.00:   0.007 s/op

     Iteration   2: ≈ 10⁻⁷ s/op
     negtiveLoop·p0.00:   ≈ 10⁻⁸ s/op
     negtiveLoop·p0.50:   ≈ 10⁻⁷ s/op
     negtiveLoop·p0.90:   ≈ 10⁻⁷ s/op
     negtiveLoop·p0.95:   ≈ 10⁻⁷ s/op
     negtiveLoop·p0.99:   ≈ 10⁻⁷ s/op
     negtiveLoop·p0.999:  ≈ 10⁻⁶ s/op
     negtiveLoop·p0.9999: ≈ 10⁻⁵ s/op
     negtiveLoop·p1.00:   0.006 s/op



     Result "com.kute.beanchmark.LoopBenchmark.negtiveLoop":
     N = 734209
     mean =     ≈ 10⁻⁷ ±(99.9%) 0.001 s/op

     Histogram, s/op:
     [0.000, 0.001) = 734198
     [0.001, 0.001) = 1
     [0.001, 0.002) = 5
     [0.002, 0.002) = 1
     [0.002, 0.003) = 1
     [0.003, 0.003) = 0
     [0.003, 0.004) = 0
     [0.004, 0.004) = 0
     [0.004, 0.005) = 0
     [0.005, 0.005) = 1
     [0.005, 0.006) = 0
     [0.006, 0.006) = 0
     [0.006, 0.007) = 1
     [0.007, 0.007) = 0
     [0.007, 0.008) = 1

     Percentiles, s/op:
     p(0.0000) =     ≈ 10⁻⁸ s/op
     p(50.0000) =     ≈ 10⁻⁷ s/op
     p(90.0000) =     ≈ 10⁻⁷ s/op
     p(95.0000) =     ≈ 10⁻⁷ s/op
     p(99.0000) =     ≈ 10⁻⁷ s/op
     p(99.9000) =     ≈ 10⁻⁶ s/op
     p(99.9900) =     ≈ 10⁻⁴ s/op
     p(99.9990) =      0.001 s/op
     p(99.9999) =      0.007 s/op
     p(100.0000) =      0.007 s/op


     # JMH version: 1.21
     # VM version: JDK 1.8.0_202, Java HotSpot(TM) 64-Bit Server VM, 25.202-b08
     # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/bin/java
     # VM options: -Dvisualvm.id=928550255869878 -Dfile.encoding=UTF-8
     # Warmup: 2 iterations, 10 s each
     # Measurement: 2 iterations, 10 s each
     # Timeout: 10 min per iteration
     # Threads: 1 thread, will synchronize iterations
     # Benchmark mode: Sampling time
     # Benchmark: com.kute.beanchmark.LoopBenchmark.positiveLoop

     # Run progress: 83.33% complete, ETA 00:00:41
     # Fork: 1 of 1
     # Warmup Iteration   1: 0.357 ±(99.9%) 0.027 s/op
     # Warmup Iteration   2: 0.425 ±(99.9%) 0.077 s/op
     Iteration   1: 0.486 ±(99.9%) 0.103 s/op
     positiveLoop·p0.00:   0.374 s/op
     positiveLoop·p0.50:   0.431 s/op
     positiveLoop·p0.90:   0.727 s/op
     positiveLoop·p0.95:   0.737 s/op
     positiveLoop·p0.99:   0.737 s/op
     positiveLoop·p0.999:  0.737 s/op
     positiveLoop·p0.9999: 0.737 s/op
     positiveLoop·p1.00:   0.737 s/op

     Iteration   2: 0.423 ±(99.9%) 0.051 s/op
     positiveLoop·p0.00:   0.350 s/op
     positiveLoop·p0.50:   0.407 s/op
     positiveLoop·p0.90:   0.519 s/op
     positiveLoop·p0.95:   0.614 s/op
     positiveLoop·p0.99:   0.636 s/op
     positiveLoop·p0.999:  0.636 s/op
     positiveLoop·p0.9999: 0.636 s/op
     positiveLoop·p1.00:   0.636 s/op



     Result "com.kute.beanchmark.LoopBenchmark.positiveLoop":
     N = 45
     mean =      0.452 ±(99.9%) 0.053 s/op

     Histogram, s/op:
     [0.300, 0.350) = 0
     [0.350, 0.400) = 16
     [0.400, 0.450) = 15
     [0.450, 0.500) = 6
     [0.500, 0.550) = 2
     [0.550, 0.600) = 1
     [0.600, 0.650) = 1
     [0.650, 0.700) = 1
     [0.700, 0.750) = 3

     Percentiles, s/op:
     p(0.0000) =      0.350 s/op
     p(50.0000) =      0.422 s/op
     p(90.0000) =      0.644 s/op
     p(95.0000) =      0.725 s/op
     p(99.0000) =      0.737 s/op
     p(99.9000) =      0.737 s/op
     p(99.9900) =      0.737 s/op
     p(99.9990) =      0.737 s/op
     p(99.9999) =      0.737 s/op
     p(100.0000) =      0.737 s/op


     # JMH version: 1.21
     # VM version: JDK 1.8.0_202, Java HotSpot(TM) 64-Bit Server VM, 25.202-b08
     # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/bin/java
     # VM options: -Dvisualvm.id=928550255869878 -Dfile.encoding=UTF-8
     # Warmup: 2 iterations, single-shot each
     # Measurement: 2 iterations, single-shot each
     # Timeout: 10 min per iteration
     # Threads: 1 thread
     # Benchmark mode: Single shot invocation time
     # Benchmark: com.kute.beanchmark.LoopBenchmark.negtiveLoop

     # Run progress: 100.00% complete, ETA 00:00:00
     # Fork: 1 of 1
     # Warmup Iteration   1: 0.005 s/op
     # Warmup Iteration   2: 0.002 s/op
     Iteration   1: ≈ 10⁻⁶ s/op
     Iteration   2: ≈ 10⁻⁶ s/op



     # JMH version: 1.21
     # VM version: JDK 1.8.0_202, Java HotSpot(TM) 64-Bit Server VM, 25.202-b08
     # VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_202.jdk/Contents/Home/jre/bin/java
     # VM options: -Dvisualvm.id=928550255869878 -Dfile.encoding=UTF-8
     # Warmup: 2 iterations, single-shot each
     # Measurement: 2 iterations, single-shot each
     # Timeout: 10 min per iteration
     # Threads: 1 thread
     # Benchmark mode: Single shot invocation time
     # Benchmark: com.kute.beanchmark.LoopBenchmark.positiveLoop

     # Run progress: 100.00% complete, ETA 00:00:00
     # Fork: 1 of 1
     # Warmup Iteration   1: 0.449 s/op
     # Warmup Iteration   2: 0.423 s/op
     Iteration   1: 0.487 s/op
     Iteration   2: 0.417 s/op



     # Run complete. Total time: 00:04:10

     REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
     why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
     experiments, perform baseline and negative tests that provide experimental control, make sure
     the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
     Do not assume the numbers tell you what you want them to tell.

     Benchmark                                          Mode     Cnt           Score    Error  Units
     LoopBenchmark.negtiveLoop                         thrpt       2  2639103276.384           ops/s
     LoopBenchmark.positiveLoop                        thrpt       2           2.631           ops/s
     LoopBenchmark.negtiveLoop                          avgt       2          ≈ 10⁻⁹            s/op
     LoopBenchmark.positiveLoop                         avgt       2           0.339            s/op
     LoopBenchmark.negtiveLoop                        sample  734209          ≈ 10⁻⁷            s/op
     LoopBenchmark.negtiveLoop:negtiveLoop·p0.00      sample                  ≈ 10⁻⁸            s/op
     LoopBenchmark.negtiveLoop:negtiveLoop·p0.50      sample                  ≈ 10⁻⁷            s/op
     LoopBenchmark.negtiveLoop:negtiveLoop·p0.90      sample                  ≈ 10⁻⁷            s/op
     LoopBenchmark.negtiveLoop:negtiveLoop·p0.95      sample                  ≈ 10⁻⁷            s/op
     LoopBenchmark.negtiveLoop:negtiveLoop·p0.99      sample                  ≈ 10⁻⁷            s/op
     LoopBenchmark.negtiveLoop:negtiveLoop·p0.999     sample                  ≈ 10⁻⁶            s/op
     LoopBenchmark.negtiveLoop:negtiveLoop·p0.9999    sample                  ≈ 10⁻⁴            s/op
     LoopBenchmark.negtiveLoop:negtiveLoop·p1.00      sample                   0.007            s/op
     LoopBenchmark.positiveLoop                       sample      45           0.452 ±  0.053   s/op
     LoopBenchmark.positiveLoop:positiveLoop·p0.00    sample                   0.350            s/op
     LoopBenchmark.positiveLoop:positiveLoop·p0.50    sample                   0.422            s/op
     LoopBenchmark.positiveLoop:positiveLoop·p0.90    sample                   0.644            s/op
     LoopBenchmark.positiveLoop:positiveLoop·p0.95    sample                   0.725            s/op
     LoopBenchmark.positiveLoop:positiveLoop·p0.99    sample                   0.737            s/op
     LoopBenchmark.positiveLoop:positiveLoop·p0.999   sample                   0.737            s/op
     LoopBenchmark.positiveLoop:positiveLoop·p0.9999  sample                   0.737            s/op
     LoopBenchmark.positiveLoop:positiveLoop·p1.00    sample                   0.737            s/op
     LoopBenchmark.negtiveLoop                            ss       2          ≈ 10⁻⁶            s/op
     LoopBenchmark.positiveLoop                           ss       2           0.452            s/op

     Process finished with exit code 0
     */

}
