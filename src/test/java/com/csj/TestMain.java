package com.csj;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TestMain {
    public static void main(String[] args) {
        Map map = new HashMap();
        List<Map> list = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            map.put("id",i);
            list.add(map);
        }
        System.out.println(list);
    }
}
