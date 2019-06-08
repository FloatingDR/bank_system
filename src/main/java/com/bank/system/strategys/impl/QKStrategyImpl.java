package com.bank.system.strategys.impl;

import com.bank.system.annoation.HandlerTrans;
import com.bank.system.strategys.TransStrategy;
import org.springframework.stereotype.Service;

/**
 * @author taylor
 * @ClassName: QKStrategyImpl
 * @Description:
 * @date: 2019-06-08 19:53
 */
@Service
@HandlerTrans(2)
public class QKStrategyImpl implements TransStrategy {
    @Override
    public String drawMoney(Integer type) {
        return "活期取款";
    }
}
