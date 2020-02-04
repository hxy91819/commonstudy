package com.xiangyu.proxy;

/**
 * @author chenjing
 * @date 2020-01-27 13:07
 */
public class Client {
    public static void main(String[] args) {
        IHelloService helloService = new HelloServiceImpl();
        LogProxy logProxy = new LogProxy();
        IHelloService proxy = (IHelloService) logProxy.bind(helloService);
        proxy.sayHello("Xiangyu");

        ISleepService sleepService = new SleepServiceImpl();
        ISleepService proxySleepService = (ISleepService) logProxy.bind(sleepService);
        proxySleepService.sleep("Xiangyu");


        AbstractLogProxy abstractLogProxy = new LogCglibProxy();
        HelloServiceImpl objectHelloService = new HelloServiceImpl();
        IHelloService cgLibHelloServiceProxy = (IHelloService) abstractLogProxy.bind(objectHelloService);
        cgLibHelloServiceProxy.sayHello("Xiangbin");
    }
}
