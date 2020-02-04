package com.xiangyu.threadlocal;

/**
 * @author chenjing
 * @date 2020-02-04 10:15
 */
public class InheritableThreadLocalDemo {
    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<String> context = new InheritableThreadLocal<>();
        context.set("my");

        Runnable runnable = () -> {
            String s = context.get();
            System.out.println(s);
        };

        Thread thread = new Thread(runnable);
        thread.start();

        Thread.sleep(5000);

        System.out.println("end");
    }
}
