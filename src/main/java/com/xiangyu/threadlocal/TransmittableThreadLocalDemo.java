package com.xiangyu.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal是绑定到Thread上的，所以在开启一个子线程时，需要处理值传递的问题。
 *
 * InheritableThreadLocal jdk提供的，可以在new 子线程的时候，成功传递值，但是遇到线程池这种复用线程的情况，将不会传递
 *
 * 可以使用阿里巴巴的TransmittableThreadLocal来完成复用线程时的值传递
 *
 * @author chenjing
 * @date 2020-02-04 10:19
 */
public class TransmittableThreadLocalDemo {
    private static ThreadLocal<Integer> context = new TransmittableThreadLocal<>();

    private static Runnable runnable = () -> {
        Integer s = context.get();
        System.out.println(s);
    };

    // 使用固定数量的线程池，确保线程池里只有一个线程，才能体现ThreadLocal传递
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) throws InterruptedException {
        context.set(0);

//        demo1();
//        demo2();
        demo3();

        Thread.sleep(2000);

        System.out.println("end");
    }

    // 直接使用的情况下，在直接启动子线程时是可以的。因为继承了InheritableThreadLocal
    private static void demo1() throws InterruptedException {
        Thread thread = new Thread(runnable);
        thread.start();
    }

    // 使用线程池时，只有第一次执行时，新建线程时能够传递，后续修改了theadLocal的值，无法传递到线程池的线程中（仍旧是第一次新建时的值）
    private static void demo2() {
        for (int i = 0; i < 5; i++) {
            executorService.execute(runnable);
            context.set(context.get() + 1);
        }
    }

    // 装饰线程池。使用ttl线程池修饰，在后续每次复用线程池的时候，均可以传递theadLocal的值过去
    private static void demo3() {
        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(executorService);
        for (int i = 0; i < 5; i++) {
            ttlExecutorService.submit(runnable);
            context.set(context.get() + 1);
        }
    }
}
