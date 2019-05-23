package com.bank.system.model.require;

import com.bank.system.model.TransInfo;
import lombok.Data;

/**
 * @author taylor
 * @ClassName: TransBean
 * @Description:
 * @date: 2019-05-22 14:27
 */
@Data
public class TransBean {
    private TransInfo transInfo;
    private String bankCardId;
    private String payPassword;
}
