package com.epam.secKill.utill;

import java.util.Random;

public class KeyUtil {
 
    public static   synchronized String   getUniqueKey(){
        Random random = new Random();
        Integer num = random.nextInt(100000);
        return  num.toString()+System.currentTimeMillis();
    }
}
