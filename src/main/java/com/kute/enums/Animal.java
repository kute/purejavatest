package com.kute.enums;

/**
 * created by kute on 2018/03/29 23:04
 */
public enum  Animal {

    DOG("dog") {
        @Override
        void behavior() {
            System.out.println("wa wa");
        }
    },


    CAT("cat") {
        @Override
        void behavior() {
            System.out.println("miao miao");
        }
    };


    private String type;

    Animal(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    abstract void behavior();

}
