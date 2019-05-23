package com.bank.system.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author taylor
 * @ClassName: ResponseBean
 * @Description:
 * @date: 2019-04-15 10:31
 */
@Data
@AllArgsConstructor
public class ResponseBean {
    private int code;
    private String msg;
    private Object data;

}
