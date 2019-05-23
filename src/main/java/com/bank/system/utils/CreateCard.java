package com.bank.system.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author taylor
 * @ClassName: CreateCard
 * @Description:
 * @date: 2019-05-20 23:54
 */
public class CreateCard {

    public static String createBankCardId(){
        Random random=new Random();
        StringBuffer sbf=new StringBuffer();
        List<Integer>headNum=new ArrayList<>(5);
        headNum.add(955288);
        headNum.add(622848);
        headNum.add(623061);
        headNum.add(622689);
        headNum.add(370285);
        int i1=random.nextInt(5);
        sbf.append(headNum.get(i1));
        int i2=random.nextInt(999999);
        sbf.append(i2);
        int i3=random.nextInt(999999);
        sbf.append(i3);
        int i4=random.nextInt(10);
        sbf.append(i4);
        return sbf.toString();
    }
}
