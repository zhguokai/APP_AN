package org;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kuajie on 15/7/25.
 */
public class Test {

    public static void main(String[] args) {
        Map map = new HashMap<Integer, Integer>();
        System.out.println("start:" + new Date().getTime());
        long st = new Date().getTime();
        for (int i = 0; i < 100000; i++) {
            map.put(i, i);
        }
        long st2 = new Date().getTime();
        System.out.println("end:" + new Date().getTime());
        System.out.println("sub:" + (st2 - st));
    }

}
