package com.bank.system.annoation;

import java.lang.annotation.*;

/**
 * @author taylor
 * @ClassName: HandlerTrans
 * @Description:
 * @date: 2019-06-08 19:49
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HandlerTrans {
    /**
     * 策略类型
     * @return
     */
    int value();
}
