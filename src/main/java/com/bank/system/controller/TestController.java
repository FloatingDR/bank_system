package com.bank.system.controller;

import com.bank.system.handle.TransContextHandler;
import com.bank.system.strategys.TransStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author taylor
 * @ClassName: TestController
 * @Description:
 * @date: 2019-06-08 21:23
 */
@RestController
@RequestMapping("/api/test")
@CrossOrigin
public class TestController {

    @Autowired
    TransContextHandler transContextHandler;

    @GetMapping("/drawMoney_by_type/{type}")
    public String drawMoney(@PathVariable int type){
        TransStrategy transStrategy=transContextHandler.getTransStrategy(type);
        return transStrategy.drawMoney(type);
    }
}
