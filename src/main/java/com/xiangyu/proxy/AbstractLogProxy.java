package com.xiangyu.proxy;

/**
 * @author chenjing
 * @date 2020-01-27 13:27
 */
public abstract class AbstractLogProxy {
    protected Object origin;

    protected abstract Object bind(Object origin);

}
