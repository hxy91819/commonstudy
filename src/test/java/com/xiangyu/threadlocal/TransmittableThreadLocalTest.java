package com.xiangyu.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * ThreadLocal是绑定到Thread上的，所以在开启一个子线程时，需要处理值传递的问题。
 * <p>
 * InheritableThreadLocal jdk提供的，可以在new 子线程的时候，成功传递值，但是遇到线程池这种复用线程的情况，将不会传递
 * <p>
 * 可以使用阿里巴巴的TransmittableThreadLocal来完成复用线程时的值传递
 *
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

    /**
     * 直接使用的情况下，在直接启动子线程时是可以的。因为继承了InheritableThreadLocal
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();
        Assert.assertEquals(new Integer(0), futureTask.get());
    }

    /**
     * 使用线程池时，只有第一次执行时，新建线程时能够传递，后续修改了theadLocal的值，无法传递到线程池的线程中（仍旧是第一次新建时的值）
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
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

    /**
     * 装饰线程池。使用ttl线程池修饰，在后续每次复用线程池的时候，均可以传递theadLocal的值过去
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
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
