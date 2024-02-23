package com.jack.chen.thread;

/**
 * 自定义函数接口
 * @param <T>
 */
@FunctionalInterface
public interface ConsumerInterface<T> {

    void accept(T t);

}
