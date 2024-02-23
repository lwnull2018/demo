package com.jack.chen.thread.atomic;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderApiDemo {

    public static void main(String[] args) {

        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();

        System.out.println(longAdder.sum());

        LongAccumulator longAccumulator = new LongAccumulator((x, y)->{ return x+y; }, 10);
        longAccumulator.accumulate(5);

        System.out.println(longAccumulator.get());

    }
}
