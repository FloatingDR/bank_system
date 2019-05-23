package com.bank.system.model.require;

import com.bank.system.model.DrawMoneyView;
import com.bank.system.model.SavingMoneyView;
import lombok.Data;

import java.util.List;

/**
 * @author taylor
 * @ClassName: TransLog
 * @Description:
 * @date: 2019-05-23 13:33
 */
@Data
public class TransLog {
    private List<SavingMoneyView> savingLog;
    private List<DrawMoneyView> drawLong;
}
