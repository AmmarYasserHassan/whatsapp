package singletons;

import config.ApplicationProperties;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    private static ThreadPool threadPool = null;
    protected ExecutorService threadPoolCmds;

    private ThreadPool() {
        threadPoolCmds = Executors.newFixedThreadPool(Integer.parseInt(ApplicationProperties.getProperty("threadPoolMaxSize")));
    }

    public static ThreadPool getInstance() {
        if (threadPool == null)
            threadPool = new ThreadPool();
        return threadPool;
    }

    public ExecutorService getThreadPoolCmds() {
        return threadPoolCmds;
    }

    public void setThreadPoolMaxNumber(int max) {
        this.threadPoolCmds.shutdown();
        threadPoolCmds = Executors.newFixedThreadPool(max);
    }
}
