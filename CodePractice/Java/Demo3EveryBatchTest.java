
package com.huawei.csb.cbccrmbusinessmgmtservice.business.demo;

import org.apache.commons.collections4.ListUtils;
import org.apache.kafka.common.protocol.types.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分批的处理
 * 一个list进去，返回一个map，这个list超过了100，需要进行分批处理
 */
public class Demo5EveryBatchTest {
    public Map<Integer,Integer> getBatchValue(List<Integer> stringList) {

        if (stringList.size() >= 100) {

            List<List<Integer>> partition = ListUtils.partition(stringList,100);
            List<Map<Integer,Integer>> listMap = new ArrayList<>();
            for (List list : partition) {
                Map<Integer,Integer> map = new HashMap<>();
                map = getValue(list);
                listMap.add(map);
            }
            Map<Integer,Integer> returnMap = new HashMap<>();
            listMap.forEach(map -> {
                map.forEach((key,value) -> {
                    returnMap.put(key,value);
                });
            });
            return returnMap;
        } else {
            return getValue(stringList);
        }

    }

    private Map<Integer,Integer> getValue(List<Integer> list) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i), list.get(i));
        }
        return map;
    }
}



