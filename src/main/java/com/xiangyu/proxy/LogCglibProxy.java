package com.xiangyu.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author chenjing
 * @date 2020-01-27 13:21
 */
public class LogCglibProxy extends AbstractLogProxy implements MethodInterceptor {
    @Override
    protected Object bind(Object origin) {
        this.origin = origin;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.origin.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("Cglib动态代理开始");
        Object result = method.invoke(this.origin, args);
        System.out.println("Cglib动态代理结束");
        return result;
    }
}
