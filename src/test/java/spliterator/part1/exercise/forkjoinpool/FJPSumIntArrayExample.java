package spliterator.part1.exercise.forkjoinpool;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class FJPSumIntArrayExample {

    @Test
    public void test() {
        int[] data = IntStream.generate(() -> 1)
                              .limit(100)
                              .toArray();

        int result = new ForkJoinPool().invoke(new ForkJoinSumArrayTask(data, 0, data.length));
        assertEquals(100, result);
    }

    private static class ForkJoinSumArrayTask extends RecursiveTask<Integer> {
        private static final int SEQUENTIAL_THRESHOLD = 100;
        private int[] data;
        private int fromInclusive;
        private int toInclusive;

        public ForkJoinSumArrayTask(int[] data,  int fromInclusive, int toInclusive) {
            this.fromInclusive = fromInclusive;
            this.toInclusive = toInclusive;
            this.data = data;
        }

        @Override
        protected Integer compute() {
            if (toInclusive - fromInclusive < SEQUENTIAL_THRESHOLD){
                return Arrays.stream(data)
                        .skip(fromInclusive)
                        .limit(toInclusive - fromInclusive + 1)
                        .sum();
            } else {
                int pivot = (fromInclusive + toInclusive) / 2;
                ForkJoinSumArrayTask left = new ForkJoinSumArrayTask(data, fromInclusive, pivot);
                left.fork();

                return new ForkJoinSumArrayTask(data, pivot + 1, toInclusive).compute() + left.join();
            }
        }

    }
}
