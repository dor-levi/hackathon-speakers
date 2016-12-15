package com.hackathon.speakers.util.thread;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(TaskExecutor.class);

    private final String name;
    private final ThreadPoolExecutor threadPool;

    private final int warningQueueSize;
    private final int criticalQueueSize;

    private final QueueStateCallback queueStateCallback;

    public TaskExecutor(String executorName, int poolSize, int warningQueueSize, int criticalQueueSize,
            int threadPriority, QueueStateCallback queueStateCallback) {
        this.name = executorName;

        this.threadPool = new ThreadPoolExecutor(poolSize, poolSize, 0l, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), HackathonThreadFactory.getThreadFactory(executorName,
                        threadPriority));

        this.warningQueueSize = warningQueueSize;
        this.criticalQueueSize = criticalQueueSize;

        this.queueStateCallback = queueStateCallback;
    }

    public void execute(Runnable task) {
        threadPool.execute(task);

        if (queueStateCallback != null) {
            int queueSize = getQueueSize();

            if (queueSize >= warningQueueSize) {
                if (queueSize >= criticalQueueSize) {
                    queueStateCallback.onCritical(this, queueSize);
                } else {
                    queueStateCallback.onWarning(this, queueSize);
                }
            }
        }
    }

    public void shutdown() {
        threadPool.shutdown();
    }

    public void awaitTermination(long timeout, TimeUnit unit) {
        try {
            threadPool.awaitTermination(timeout, unit);
        } catch (InterruptedException e) {
            logger.warn("Task executor interrupted while awaiting termination.", e);
        }
    }

    public List<Runnable> shutdownNow() {
        return threadPool.shutdownNow();
    }

    public int getApproxAvailableThreads() {
        return (threadPool.getMaximumPoolSize() - threadPool.getActiveCount());
    }

    public boolean isCritical() {
        return (getQueueSize() >= criticalQueueSize);
    }

    public boolean isWarning() {
        return (getQueueSize() >= warningQueueSize);
    }

    public int getQueueSize() {
        return threadPool.getQueue().size();
    }

    public int getCriticalQueueSize() {
        return criticalQueueSize;
    }

    public int getWarningQueueSize() {
        return warningQueueSize;
    }

    public String getName() {
        return name;
    }

    public int getCorePoolSize() {
        return threadPool.getCorePoolSize();
    }

    public int getMaximumPoolSize() {
        return threadPool.getMaximumPoolSize();
    }

    public void setThreadPoolSize(int corePoolSize, int maximumPoolSize) {
        threadPool.setCorePoolSize(corePoolSize);
        threadPool.setMaximumPoolSize(maximumPoolSize);
    }

    public static interface QueueStateCallback {
        public void onWarning(TaskExecutor target, int queueSize);

        public void onCritical(TaskExecutor target, int queueSize);
    }
}
