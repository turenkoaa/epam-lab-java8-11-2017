package lambda.part3.exercise;

import lambda.data.Employee;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Exercise1 {

    @Test
    public void mapEmployeesToLengthOfTheirFullNames() {
        List<Employee> employees = Example1.getEmployees();

        Function<Employee, String> fullNameExtractor =
                employee -> employee.getPerson().getFirstName() + ' ' + employee.getPerson().getLastName();
        ToIntFunction<String> stringLengthExtractor = String::length;

        Function<Employee, Integer> fullNameLengthExtractor = e -> stringLengthExtractor.applyAsInt(fullNameExtractor.apply(e));

        List<Integer> lengths = employees.stream().map(fullNameLengthExtractor).collect(Collectors.toList());

        assertEquals(Arrays.asList(14, 19, 14, 14), lengths);
    }
}
