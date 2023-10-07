import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

class VirtualThread {
    public static void main(String[] args) {
        // 1. time : 1,024 ms
        long start = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 1_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("1. time : " + (elapsedTime));

        // 2. time : 100,382 ms
        start = System.currentTimeMillis();
        try (var executor = Executors.newFixedThreadPool(10)) {
            IntStream.range(0, 1_000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }
        elapsedTime = System.currentTimeMillis() - start;
        System.out.println("2. time : " + (elapsedTime));
    }
}

/*
[0.534s][warning][os,thread] Failed to start thread "Unknown thread" - pthread_create failed (EAGAIN) for attributes: stacksize: 2048k, guardsize: 16k, detached.
[0.534s][warning][os,thread] Failed to start the native thread for java.lang.Thread "Thread-4066"
Exception in thread "main" java.lang.OutOfMemoryError: unable to create native thread: possibly out of memory or process/resource limits reached
	at java.base/java.lang.Thread.start0(Native Method)
	at java.base/java.lang.Thread.start(Thread.java:1526)
	at VirtualThread.stackOverFlowErrorExample(VirtualTread.java:21)
	at VirtualThread.main(VirtualTread.java:5)
* */