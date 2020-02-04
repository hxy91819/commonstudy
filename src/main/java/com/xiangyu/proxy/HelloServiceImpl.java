package com.xiangyu.proxy;

/**
 * @author chenjing
 * @date 2020-01-27 13:05
 */
public class HelloServiceImpl implements IHelloService {
    @Override
    public void sayHello(String name) {
        System.out.println("Hello, " + name + "!");
    }
}
