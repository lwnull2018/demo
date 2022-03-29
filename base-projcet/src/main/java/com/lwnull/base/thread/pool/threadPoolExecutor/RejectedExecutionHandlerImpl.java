package com.lwnull.base.thread.pool.threadPoolExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 创建我们一个 RejectedExecutionHandler 实现用于处理不能适应工作队列的工作
 * Created by abc@123.com ON 2018/10/8.
 */
public class RejectedExecutionHandlerImpl implements RejectedExecutionHandler {

    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r.toString() + " is rejected");
    }

}
