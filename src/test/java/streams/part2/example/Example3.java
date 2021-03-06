package streams.part2.example;

import lambda.data.Employee;
import lambda.data.Person;
import lambda.part3.example.Example1;
import org.junit.Test;
import streams.part2.example.data.PersonPositionDuration;

import java.util.*;
import java.util.function.Function;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertEquals;

@SuppressWarnings("ConstantConditions")
public class Example3 {

    /*
      * Вход:
      * [
      *     {
      *         {Иван Мельников 30},
      *         [
      *             {2, dev, "EPAM"},
      *             {1, dev, "google"}
      *         ]
      *     },
      *     {
      *         {Александр Дементьев 28},
      *         [
      *             {2, tester, "EPAM"},
      *             {1, dev, "EPAM"},
      *             {1, dev, "google"}
      *         ]
      *     },
      *     {
      *         {Дмитрий Осинов 40},
      *         [
      *             {3, QA, "yandex"},
      *             {1, QA, "EPAM"},
      *             {1, dev, "mail.ru"}
      *         ]
      *     },
      *     {
      *         {Анна Светличная 21},
      *         [
      *             {1, tester, "T-Systems"}
      *         ]
      *     }
      * ]
      *
      * Выход:
      * [
      *     "dev" -> {Иван Мельников 30}
      *     "QA" -> {Дмитрий Осинов 40}
      *     "tester" -> {Александр Дементьев 28}
      * ]
      */
    @Test
    public void getTheCoolestByPositionUsingToMap() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Person> coolest = null;

        assertEquals(prepareExpected(employees), coolest);
    }

    @Test
    public void getTheCoolestByPositionUsingGroupingBy() {
        List<Employee> employees = Example1.getEmployees();

        Map<String, Person> coolest = null;
    }

    private static Map<String, Person> prepareExpected(List<Employee> employees) {
        Map<String, Person> expected = new HashMap<>();
        expected.put("dev", employees.get(0).getPerson());
        expected.put("tester", employees.get(4).getPerson());
        expected.put("QA", employees.get(4).getPerson());
        return expected;
    }
}
