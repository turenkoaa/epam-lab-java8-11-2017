package lambda.part3.exercise;

import com.google.common.collect.Lists;
import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.part3.example.Example1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"unused", "ConstantConditions"})
public class Exercise4 {

    private static class LazyFlatMapHelper<T, R> {

        private List<T> source;
        private Function<T, List<R>> flatMapper;

        private LazyFlatMapHelper(List<T> list, Function<T, List<R>> remapping){
            source = list;
            flatMapper = remapping;
        }

        public static <T> LazyFlatMapHelper<T, T> from(List<T> list) {
            return new LazyFlatMapHelper<>(list, Collections::singletonList);
        }

        public <U> LazyFlatMapHelper<T, U> flatMap(Function<R, List<U>> flatMapping) {
            return new LazyFlatMapHelper<>(source, flatMapper.andThen(transformUsing(flatMapping)));
        }

        public <U> LazyFlatMapHelper<T, U> map(Function<R, U> mapping) {
            return new LazyFlatMapHelper<>(source, flatMapper.andThen(transformUsing(mapping.andThen(Collections::singletonList))));
        }

        public List<R> force() {
            return transformUsing(flatMapper).apply(source);
        }

        private <FROM, TO> Function<List<FROM>, List<TO>> transformUsing(Function<FROM, List<TO>> mapping){
            return source -> {
                List<TO> list = new ArrayList<>(source.size());
                source.forEach(mapping.andThen(list::addAll)::apply);
                return list;
            };
        }
    }

    @Test
    public void mapEmployeesToCodesOfLetterTheirPositionsUsingLazyFlatMapHelper() {
        List<Employee> employees = Example1.getEmployees();

        List<Integer> codes = LazyFlatMapHelper.from(employees)
                .flatMap(Employee::getJobHistory)
                .map(JobHistoryEntry::getPosition)
                .flatMap(Lists::charactersOf)
                .map(letter -> (int) letter)
                .force();

        assertEquals(calcCodes("dev", "dev", "tester", "dev", "dev", "QA", "QA", "dev", "tester", "tester", "QA", "QA", "QA", "dev"), codes);
    }

    private static List<Integer> calcCodes(String...strings) {
        List<Integer> codes = new ArrayList<>();
        for (String string : strings) {
            for (char letter : string.toCharArray()) {
                codes.add((int) letter);
            }
        }
        return codes;
    }
}
