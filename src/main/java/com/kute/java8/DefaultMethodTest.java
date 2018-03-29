package com.kute.java8;

/**
 * created by kute on 2018/03/29 22:37
 *
 * https://juejin.im/post/5abc9c546fb9a028b61797d8
 */
public class DefaultMethodTest {


    public static void main(String[] args) {
        new DefaultMethodTest().test();
    }

    public void test() {
        Son son = new Son();
        son.money();
    }

    public class Son implements Father, GrandFather {

        @Override
        public void money() {
            System.out.println(this.getClass().getSimpleName() + " Son money method");
        }
    }

    public interface Father {
        default void money() {
            System.out.println(this.getClass().getSimpleName() + " Father money method");
        }
    }

    public interface GrandFather {
//        default void money() {
//            System.out.println(this.getClass().getSimpleName() + " GrandFather money method");
//        }

        void money();
    }


}
