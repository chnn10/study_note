
import org.apache.commons.collections4.ListUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分批的处理-------------> 需要进行优化
 * 一个list进去，返回一个map，这个list超过了100，需要进行分批处理
 */
public class Demo5EveryBatchTest {
    public static Map<Integer,Integer> getBatchValue(List<Integer> stringList) {

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

    private static Map<Integer,Integer> getValue(List<Integer> list) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i), list.get(i));
        }
        return map;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0;i <= 200; i++) {
            list.add(i);
        }
        Map<Integer,Integer> map = getBatchValue(list);
        map.forEach((k,v) ->{
            System.out.println("k: "+k+", v: "+v);
        });
    }
}





