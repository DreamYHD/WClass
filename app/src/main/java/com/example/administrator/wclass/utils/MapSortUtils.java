package com.example.administrator.wclass.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/20.
 */

public class MapSortUtils{
    public static List<Map.Entry<String, Integer>> sort(Map<String,Integer>map){
        List<Map.Entry<String,Integer>>list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> stringIntegerEntry, Map.Entry<String, Integer> t1) {
                return  t1.getValue() - stringIntegerEntry.getValue() ;
            }
        });
        return list;

    }
}
