import java.time.Duration;

class VirtualThread {
    public static void main(String[]args){
        virtualThread("test-virtual-thread", () -> {
            for (int i = 0; i < 100_000; i++) {
                new Thread(() -> {
                    try {
                        Thread.sleep(Duration.ofSeconds(1L));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        });
    }

    private static Thread virtualThread(String name, Runnable runnable) {
        return Thread.ofVirtual()
                .name(name)
                .start(runnable);
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