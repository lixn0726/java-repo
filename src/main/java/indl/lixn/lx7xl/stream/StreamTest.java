package indl.lixn.lx7xl.stream;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author listen
 **/
public class StreamTest {

    public static void main(String[] args) {

        IntStream.of(1, 2, 3)
                .peek(x -> System.out.print("x" + x))
                .forEach(x -> System.out.print("x" + x));
        // x1x1x2x2x3x3

        System.out.println();
        double d = IntStream.of(5, 12, 8, 6, 15, 10, 7, 14)
                .filter(x -> x % 2 == 0)
                .map(x -> x * 3)
                .distinct()
                .limit(3)
                .sorted()
                .average()
                .orElse(0.0);
        System.out.println(d);

    }

}
