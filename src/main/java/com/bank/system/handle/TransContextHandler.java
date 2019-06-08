package com.bank.system.handle;


import com.bank.system.strategys.TransStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author taylor
 * @ClassName: TransContextHandler
 * @Description:
 * @date: 2019-06-08 19:59
 */
@Service
public class TransContextHandler {
    @Autowired
    private ApplicationContext applicationContext;
    /**
     * 存放所有策略类Bean的map
     */
    public static Map<Integer, Class<TransStrategy>> transStrategyBeanMap= new HashMap<>();

    public TransStrategy getTransStrategy(Integer type){
        Class<TransStrategy> strategyClass = transStrategyBeanMap.get(type);
        if(strategyClass==null){
            throw new IllegalArgumentException("没有对应的订单类型");
        }
        //从容器中获取对应的策略Bean
        return applicationContext.getBean(strategyClass);
    }
}
