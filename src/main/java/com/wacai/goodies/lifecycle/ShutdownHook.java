package com.wacai.goodies.lifecycle;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ShutdownHook is just give you a way to register a jmx shutdown mbean so that you won't block in the main thread and have to CTRL+C to put your service to end brutally.
 *
 * @author yunshi@wacai.com
 * @since 2014-12-13
 */
public class ShutdownHook implements ShutdownHookMBean {

    protected String domain;

    protected AtomicReference<Runnable> shutdownHookHolder = new AtomicReference<Runnable>();

    public ShutdownHook() {
        this("com.wacai.lifecycles");
    }

    public ShutdownHook(String domainName) {
        this.domain = domainName;
    }

    public void attach(Runnable shutdownHook) throws Exception {
        shutdownHookHolder.set(shutdownHook);
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        mBeanServer.registerMBean(this, new ObjectName(domain, "name", "ShutdownHook"));
    }


    @Override
    public void shtudown() {
        Runnable hook = shutdownHookHolder.get();
        if (hook != null) hook.run();
    }

}
