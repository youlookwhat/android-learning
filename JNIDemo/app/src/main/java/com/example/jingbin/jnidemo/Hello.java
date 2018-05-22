package com.example.jingbin.jnidemo;

/**
 * @author jingbin
 * @data 2018/5/21
 * @Description
 */

public class Hello {

    static {
        // 导入静态库
        System.loadLibrary("hello");
    }

    /**
     * 声明一个native方法 让C语言来实现里面的逻辑
     */
    public static native String helloFromC();

    public static native int add(int x, int y);

}
