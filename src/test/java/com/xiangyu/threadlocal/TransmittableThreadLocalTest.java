package com.xiangyu.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author chenjing
 * @date 2020-02-04 10:57
 */
public class TransmittableThreadLocalTest {
    private static ThreadLocal<Integer> context = new TransmittableThreadLocal<>();

    private static Callable<Integer> callable = () -> {
        Integer s = context.get();
        System.out.println(s);
        return s;
    };

    // 使用固定数量的线程池，确保线程池里只有一个线程，才能体现ThreadLocal传递
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Before
    public void before() {
        System.out.println("before");
        context.set(0);
    }

    @After
    public void after() throws InterruptedException {
        System.out.println("after");
    }

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();
        Assert.assertEquals(new Integer(0), futureTask.get());
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 5; i++) {
            Future<Integer> submit = executorService.submit(callable);
            Assert.assertEquals(new Integer(0), submit.get()); // 子线程的值只会保留第一次的
            context.set(context.get() + 1);
        }
        // 主线程的值会更新
        Assert.assertEquals(new Integer(5), context.get());
    }

    @Test
    public void test3() throws ExecutionException, InterruptedException {
        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(executorService);
        for (int i = 0; i < 5; i++) {
            Future<Integer> submit = ttlExecutorService.submit(callable);
            Assert.assertEquals(new Integer(i), submit.get());
            context.set(context.get() + 1);
        }
        Assert.assertEquals(new Integer(5), context.get());
    }
}
