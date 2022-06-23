import java.util.List;
import java.util.stream.Collectors;

public class Demo3ContainListTest {

    public static void main(String[] args) {
        Info info = new Info();
        info.setId("001");
        School school = new School();
        school.setCity("Beijing");
        school.setScore("100");
        info.setSchool(school);

        List<Person> personList = PersonUtil.getPersonInfo().stream()
                .filter(person -> "北京".equals(person.getCity())).collect(Collectors.toList());

        info.setPersonList(personList);

        System.out.println(info);

    }
}



