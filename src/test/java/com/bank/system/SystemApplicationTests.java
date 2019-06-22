package com.bank.system;


import com.bank.system.thread.AsyncService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.Calendar;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemApplicationTests {

    @Test
    public void dateTransTest(){
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.add(2, 24);
        // calendar的time转成java.util.Date格式日期
        java.util.Date utilDate = calendar.getTime();
        //java.util.Date日期转换成转成java.sql.Date格式
        Date newDate =new Date(utilDate.getTime());
        System.out.println(newDate);
    }

    @Resource
    private AsyncService asyncService;

    @Test
    public void asyncTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            asyncService.executeAsync1();
            asyncService.executeAsync2();
        }
        Thread.sleep(1000);
    }
}
