package com.euler59;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class DecipherEngine
{
    private final ExecutorService taskPool = Executors.newFixedThreadPool(8,
	    new ThreadFactory());

    public void submitAndWait(Collection<XORDeciperTask> tasks) throws InterruptedException, ExecutionException
    {
	List<Future<Void>> invokedTasks = taskPool.invokeAll(tasks);
	
	for(Future<Void> fut : invokedTasks)
	{
	    fut.get();
	}
    }
    
    public final void shutDown()
    {
	taskPool.shutdown();
    }
    
    private static final class ThreadFactory implements
	    java.util.concurrent.ThreadFactory
    {
	private final AtomicInteger counter = new AtomicInteger();

	@Override
	public Thread newThread(Runnable r)
	{
	    Thread t = new Thread(r, "Deciper thread #"
		    + counter.incrementAndGet());
	    t.setDaemon(true);
	    return t;
	}
    }
}
