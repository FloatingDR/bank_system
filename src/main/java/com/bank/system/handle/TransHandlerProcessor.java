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
 * @Description: 策略核心功能, 获取所有策略注解的类型
 *               并将对应的class初始化到HandlerOrderContext中
 *
 *               当一个类实现了这个接口（ApplicationContextAware）之后，
 *               这个类就可以方便获得ApplicationContext中的所有bean
 *               换句话说，就是这个类可以直接获取spring配置文件中，所有有引用到的bean对象.
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
