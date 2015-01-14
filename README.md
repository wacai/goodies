# Goodies项目介绍

提供一些小而美的工具类， 作为二方库，该项目尽量不依赖任何其它第三方库

# 都有哪些Goodies？


## 可以使用非ASCII配置的properties文件 (since 1.0.0)

SimpleMessageSource提供了一种访问可以任何字符编辑的properties文件的服务接口， 你现在可以**直接**在properties文件中输入中文 ， 而不用非要像过去那样，输入中文然后通过工具转换为ASCII字符， 古老的方式显然很不友好 ；）

在sample.properties文件中:
<pre>
simple.message.source.author=陨石
</pre>

通过SimpleMessageSource， 则可以直接访问：

~~~~~~~ {.java}
SimpleMessageSource messageSource = new SimpleMessageSource("sample");
String message = messageSource.getMessage("simple.message.source.author");
String defaultMessage = messageSource.getMessage("non.existing.key", "缺省值");
~~~~~~~

更多使用细节可以参考单元测试代码。


## 服务进程生命周期控制

### ShutdownLatch (since 1.0.0)

某些场景下(比如单一启动的dubbo服务)，如果你不希望主线程退出，可以使用ShutdownLatch：

~~~~~~~ {.java}
ShutdownLatch latch = new ShutdownLatch("your_domain_for_mbeans");
// latch.checkIntervalInSeconds=5 (sleep的秒数，视响应需求决定，默认10秒)
latch.await();
~~~~~~~


### ShutdownHook (since 1.0.0)

另外一种场景(比如使用netty的时候)，当要进入block状态之前，注册一个ShutdownHook， 依然可以给你一种优雅的结束自己服务的方式， 而不用粗鲁的CTRL+C。

使用ShutdownHook之前：


~~~~~~~ {.java}
ChannelFuture f = b.bind(port).sync(); // block here
f.channel().closeFuture().sync();
~~~~~~~


使用ShutdownHook之后：

~~~~~~~ {.java}
final ChannelFuture future = b.bind(port);
ShutdownHook hook = new ShutdownHook();
hook.attach(new Runnable(){
	@Override
	public void run() {
		future.channel().close();
	}
});
ChannelFuture f = future.sync();

f.channel().closeFuture().sync();
~~~~~~~

在bind之后， sync操作进入block状态之前，首先注册一个ShutdownHook， 然后再进入sync并一直等待channel关闭退出。



## 线程相关

### NamedThreadFactory (since 1.0.1)

在需要初始化线程池的时候(ExecutorServices)， 最好是传入自定义的ThreadFactory用于标识线程， 这个时候，可以传入一个自己命名的NamedThreadFactory实例：

~~~~~~~ {.java}
Executors.newFixedThreadPool(maxPoolSize,new NamedThreadFactory("xxxx worker thread"))
~~~~~~~








