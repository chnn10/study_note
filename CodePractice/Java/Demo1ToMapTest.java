/**
 * 测试toMap的方法
 */
public class Demo2ToMapTest {
    public static void main(String[] args) {
        List<Person> personList = Arrays.asList(new Person("小米", "清华", "北京"),
                                                new Person("小明", "北大", "北京"),
                                                new Person("小花", "复旦", "上海"));

        Map<String,Person> personMap = personList.stream().collect(Collectors.toMap(Person::getName,person -> person));
        System.out.println(personMap);

        personList.forEach(person-> System.out.println(person));

        List<String> nameList = personList.stream().map(Person::getName).collect(Collectors.toList());
        System.out.println(nameList);

    }
}



