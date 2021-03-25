import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main (String[] args) throws Exception {

        Callable<Integer> task = () -> {
            System.out.println("Всем привет!, я " + Thread.currentThread().getName());
            System.out.println("Всем привет!, я " + Thread.currentThread().getName());
            System.out.println("Всем привет!, я " + Thread.currentThread().getName());
            System.out.println("Всем привет!, я " + Thread.currentThread().getName());
            return 4;
        };
        
        ExecutorService serviceFirst = Executors.newFixedThreadPool(4);
        List<Callable<Integer>> tasks = Arrays.asList(
                task,
                task,
                task);
        Integer result = serviceFirst.invokeAny(tasks);
        System.out.println(result);
        serviceFirst.shutdown();

        ExecutorService serviceSecond = Executors.newFixedThreadPool(4);
        serviceSecond.invokeAll(tasks)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    }
                    catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                })
                .forEach(System.out::println);
        serviceSecond.shutdown();
    }
}
