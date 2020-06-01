package com.github.sinjar.common.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * <b>Description : </b> 异步处理器
 *
 * @author CPF
 * @date 2019/9/19 16:48
 **/
@Slf4j
public class AsynchronousProcessor<T>{

    /**
     * 有消息的消息函数处理接口
     */
    private Predicate<T> disposeFun;

    /**
     * 出错时的消费函数接口
     */
    private Consumer<T> errFun;

    /**
     * 存放待处理的消息
     */
    private LinkedBlockingQueue<T> linkedBlockingQueue = new LinkedBlockingQueue<>();

    /**
     * 当前运行的线程
     */
    private Thread thread;

    public AsynchronousProcessor(Predicate<T> disposeFun, boolean autoStart) {
        this(disposeFun, null, autoStart);
    }

    public AsynchronousProcessor(Predicate<T> disposeFun, Consumer<T> errFun, boolean autoStart) {
        this.disposeFun = disposeFun;
        this.errFun = errFun;
        if (autoStart) {
            start();
        }
    }

    /**
     * 线程调度方法类
     */
    private class ProcessorRunnable implements Runnable {
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                try {
                    // 获取处理对象
                    T t = linkedBlockingQueue.take();
                    boolean isSuccess = false;
                    // 处理对象
                    if (disposeFun != null) {
                        isSuccess = disposeFun.test(t);
                    }
                    // 如果失败则执行错误消费函数接口
                    if (!isSuccess && errFun != null) {
                        errFun.accept(t);
                    }
                } catch (InterruptedException e) {
                    log.error("线程发生错误", e);
                    Thread.currentThread().interrupt();
                } catch (RuntimeException e) {
                    // 添加运行时异常, 防止发生运行时异常县城停止
                    log.error("异步处理器发生处理异常", e);
                }
            }
        }
    }

    /**
     * 启动线程
     */
    public void start(){
        if (thread == null || thread.isInterrupted()) {
            synchronized (this) {
                if (thread == null || thread.isInterrupted()) {
                    thread = new Thread(new ProcessorRunnable());
                    thread.start();
                }
            }
            return;
        }
        log.warn("线程已启动, 请勿重复调用");
    }

    public void add(T t) {
        if (t == null) {
            return;
        }
        linkedBlockingQueue.add(t);
    }

    public void closeThread() {
        if (thread == null || thread.isInterrupted()) {
            log.warn("线程不存在或已经调用线程关闭方法, 请勿重复调用");
            return;
        }
        thread.interrupt();
        log.info("AsynchronousProcessor Thread 已经手动停止!!!");
    }

}
