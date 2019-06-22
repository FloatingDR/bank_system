package com.bank.system.thread;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author taylor
 * @ClassName: AsyncService
 * @Description:
 * @date: 2019-06-08 22:59
 */
@Service
public class AsyncService {

    @Async
    public void executeAsync1() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }
        System.out.println("异步任务::1");

    }

    @Async
    public void executeAsync2() {
        System.out.println("异步任务::2");
    }

}
