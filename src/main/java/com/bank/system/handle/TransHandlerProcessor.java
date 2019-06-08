package com.bank.system.handle;

import com.bank.system.annoation.HandlerTrans;
import com.bank.system.strategys.TransStrategy;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author taylor
 * @ClassName: TransHandlerProcessor
 * @Description:
 * @date: 2019-06-08 20:00
 */
@Service
public class TransHandlerProcessor implements ApplicationContextAware {

    /**
     * get all strategy Bean.
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,Object> transStrategyMap=applicationContext.getBeansWithAnnotation(HandlerTrans.class);
        transStrategyMap.forEach((k,v)->{
            Class<TransStrategy> transStrategyClass= (Class<TransStrategy>) v.getClass();
            int type = transStrategyClass.getAnnotation(HandlerTrans.class).value();
            //将class加入map中，type作为key
            TransContextHandler.transStrategyBeanMap.put(type,transStrategyClass);
        });
    }

}
