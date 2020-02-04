package com.xiangyu.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author chenjing
 * @date 2020-01-27 13:06
 */
public class LogProxy extends AbstractLogProxy implements InvocationHandler {

    /**
     * 绑定源对象，返回代理对象
     *
     * @param origin
     * @return
     */
    @Override
    public Object bind(Object origin) {
        this.origin = origin;
        return Proxy.newProxyInstance(origin.getClass().getClassLoader(), origin.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理逻辑 beign");
        Object result = method.invoke(origin, args);
        System.out.println("代理逻辑 end");
        return result;
    }
}
