package com.bank.system.strategys.impl;

import com.bank.system.annoation.HandlerTrans;
import com.bank.system.strategys.TransStrategy;
import org.springframework.stereotype.Service;

/**
 * @author taylor
 * @ClassName: DQStrategyImpl
 * @Description:
 * @date: 2019-06-08 19:54
 */
@Service
@HandlerTrans(1)
public class DQStrategyImpl implements TransStrategy {
    @Override
    public String drawMoney(Integer type) {
         return "定期取款";
    }
}
