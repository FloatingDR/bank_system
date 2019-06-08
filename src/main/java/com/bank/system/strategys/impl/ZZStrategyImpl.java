package com.bank.system.strategys.impl;

import com.bank.system.annoation.HandlerTrans;
import com.bank.system.strategys.TransStrategy;
import org.springframework.stereotype.Service;

/**
 * @author taylor
 * @ClassName: ZZStrategyImpl
 * @Description:
 * @date: 2019-06-08 19:53
 */
@Service
@HandlerTrans(3)
public class ZZStrategyImpl implements TransStrategy {

    @Override
    public String drawMoney(Integer type) {
        return "转账!";
    }
}
