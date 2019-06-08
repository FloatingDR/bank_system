package com.bank.system.strategys;

/**
 * @author taylor
 * @ClassName: TransStrategy
 * @Description:
 * @date: 2019-06-08 19:52
 */
public interface TransStrategy {

    /**
     * 取款或转账
     * @param type
     */
    String drawMoney(Integer type);
}
