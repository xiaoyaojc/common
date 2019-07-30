package com.xiaoyao.thread;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.xiaoyao.task.TaskFunction;
import com.xiaoyao.task.TaskRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author jincong
 * @date 16/3/14
 */
@Service("ThreadPool")
public class ThreadPool {

    private static Logger logger = LoggerFactory.getLogger(ThreadPool.class);

    private static final int DEFAULT_MIN_THREAD_COUNT = 16;

    private static final int DEFAULT_MAX_THREAD_COUNT = 64;

    private static final int DEFAULT_KEEP_ALIVE_TIME = 60;

    private static final int DEFAULT_QUEUE_SIZE = 32;

    private static final long DEFAULT_THREAD_PROCESS_TIME_OUT = 60000L;

    private static final long DEFAULT_FUTURE_GET_TIME_OUT = 10L;

    /**
     * 线程池所使用的缓冲队列
     */
    private BlockingQueue<Runnable> workQueue;

    private ListeningExecutorService service;

    @PostConstruct
    public void initialize() {
        /**
         * 线程池所使用的缓冲队列的最大数量,用于创建有界的缓冲队列
         */
        workQueue = new ArrayBlockingQueue(DEFAULT_QUEUE_SIZE);
        ThreadFactory threadFactory = new NamedThreadFactory("Parallel-Processor", null, true);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(DEFAULT_MIN_THREAD_COUNT, DEFAULT_MAX_THREAD_COUNT, DEFAULT_KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue, threadFactory, handler);
        service = MoreExecutors.listeningDecorator(threadPoolExecutor);
    }

    @PreDestroy
    public void stop() {
        service.shutdownNow();
        workQueue.clear();
    }

    /**
     * 提交并发请求
     *
     * @param taskRequest
     */
    public List<Object> execute(final TaskRequest taskRequest) {
        return execute(taskRequest, DEFAULT_THREAD_PROCESS_TIME_OUT);
    }

    /**
     * 提交并发请求
     *
     * @param taskRequest
     * @param
     */
    public  List<Object> execute(final TaskRequest taskRequest, long threadProcessTimeout) {
        if (logger.isInfoEnabled()) {
            logger.info("Try to parallel process Parallel process com.xiaoyao.task count :" + taskRequest.getTaskCount());
        }
        final CountDownLatch latch = new CountDownLatch(taskRequest.getTaskCount());

        List<ListenableFuture<Object>> futureList = new ArrayList<>(taskRequest.getTaskCount());
        for (int i = 0; i < taskRequest.getTaskCount(); i++) {
            final int index = i;
            ListenableFuture<Object> futureTaskResult = service.submit(() -> {
                Object result;
                try {
                    long startTime = System.currentTimeMillis();
                    TaskFunction taskFunction = taskRequest.getTaskList().get(index);
                    result = taskFunction.apply();
                    long endTime = System.currentTimeMillis();
                    if (logger.isInfoEnabled()) {
                        logger.info("Try to parallel process Parallel process com.xiaoyao.task totalTime :" + (endTime - startTime));
                    }
                    return result;
                } catch (Throwable e) {
                    logger.error("com.xiaoyao.thread pool process future com.xiaoyao.task failed:", e);
                    throw new RuntimeException();
                } finally {
                    latch.countDown();//计数器减一
                }
            });
            futureList.add(futureTaskResult);
        }
        ListenableFuture<List<Object>> finalFuture=
                taskRequest.getIgnoreError()?Futures.successfulAsList(futureList):Futures.allAsList(futureList);
        if (taskRequest.getCallback() != null) {
            Futures.addCallback(finalFuture, taskRequest.getCallback());
        }
        try {
            latch.await(threadProcessTimeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.error("latch.await parallel process failed, maybe timeout:", e);
        }
        List<Object> taskResultList = new ArrayList<>(taskRequest.getTaskCount());
        try {
            taskResultList=finalFuture.get(DEFAULT_FUTURE_GET_TIME_OUT, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            logger.error("com.xiaoyao.thread pool get result exception:", e);
            if (finalFuture.cancel(true)) {
                logger.warn("com.xiaoyao.task execution time out, com.xiaoyao.thread pool cancel com.xiaoyao.task success!");
            } else {
                logger.error("com.xiaoyao.thread pool cancel com.xiaoyao.task failed!");
            }
        }
        return taskResultList;
    }
}
