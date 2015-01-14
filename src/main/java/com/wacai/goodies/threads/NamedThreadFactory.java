package com.wacai.goodies.threads;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A thread factory that will produce named threads with sequence numbers.
 * <p/>
 * Created by yunshi on 1/14/15.
 */
public class NamedThreadFactory implements ThreadFactory {

    private AtomicInteger counter = new AtomicInteger();
    private String name;

    public NamedThreadFactory(String name) {
        this.name = name;
    }


    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, name + counter.getAndIncrement());
    }
}
