import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static LongAdder adder = new LongAdder();
    static final int numberOfShops = 3;

    public static void main(String[] args) throws InterruptedException {

        Runnable zReport = (() -> {
            int numberOfPayChecks = (int) (Math.random() * 10) + 1;
            int[] sumsOfPayChecks = new int[numberOfPayChecks];
            int sumTotal = 0;
            for (int i = 0; i < sumsOfPayChecks.length; i++) {
                sumsOfPayChecks[i] = (int) (Math.random() * 2000) + 1;
                sumTotal += sumsOfPayChecks[i];
                adder.add(sumsOfPayChecks[i]);
            }
            System.out.println("Сумма по магазину " + Thread.currentThread().getName() + " в соответствии с z-отчетом: " + sumTotal);
        });

        List<Thread> listOfShops = Stream.generate(() -> new Thread(zReport))
                .limit(numberOfShops)
                .peek(Thread::start)
                .collect(Collectors.toList());

        for (Thread shop : listOfShops) {
            shop.join();
        }

        long total = adder.sum();
        System.out.println("Сумма по магазинам: " + total);
    }
}
