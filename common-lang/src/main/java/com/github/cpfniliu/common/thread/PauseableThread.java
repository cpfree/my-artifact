package com.github.cpfniliu.common.thread;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BooleanSupplier;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * Date: 2020/8/13 23:10
 */
@Slf4j
public final class PauseableThread extends Thread {

    private final BooleanSupplier booleanSupplier;

    public PauseableThread(@NonNull BooleanSupplier booleanSupplier) {
        this.booleanSupplier = booleanSupplier;
    }

    /**
     * 用于线程启停的锁
     */
    public final Object lock = new Object();

    /**
     * 线程是否暂停标记
     */
    @Getter
    private volatile boolean suspend;

    /**
     * 多长时间运行一次(while true 中的一个执行sleep多久)
     */
    @Setter
    private int millisecond;

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            while(suspend){
                synchronized (lock) {
                    try {
                        log.info("PauseableThread 已经暂停!!!");
                        lock.wait();
                        log.info("PauseableThread 已经恢复!!!");
                    } catch (InterruptedException e) {
                        log.error("线程暂停发生错误", e);
                        Thread.currentThread().interrupt();
                    }
                }
            }
            try {
                boolean isBreak = booleanSupplier.getAsBoolean();
                if (isBreak) {
                    break;
                }
                // 防止 CPU 过高占用
                if (millisecond > 0) {
                    Thread.sleep(millisecond);
                }
            } catch (InterruptedException e) {
                log.error("线程发生错误", e);
                Thread.currentThread().interrupt();
            } catch (RuntimeException e) {
                // 添加运行时异常, 防止发生运行时异常县城停止
                log.error("异步处理器发生处理异常", e);
            }
        }
        log.info("PauseableThread 已经结束!!!");
    }

    /**
     * 线程暂停
     */
    public void pause() {
        suspend = true;
    }

    /**
     * 线程恢复
     */
    public void wake() {
        if (suspend) {
            synchronized (lock) {
                if (suspend) {
                    suspend = false;
                }
                lock.notifyAll();
            }
        }
    }

}
