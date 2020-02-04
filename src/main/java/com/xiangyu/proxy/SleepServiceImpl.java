package com.xiangyu.proxy;

/**
 * @author chenjing
 * @date 2020-01-27 13:24
 */
public class SleepServiceImpl implements ISleepService {
    @Override
    public void sleep(String name) {
        System.out.println(name +  " wants sleep!!");
    }
}
