
class VirtualThread {
    public static void main(String[]args){
        stackOverFlowErrorExample()
    }
    private static Thread virtualThread(String name, Runnable runnable) {
        return Thread.ofVirtual()
                .name(name)
                .start(runnable);
    }
    private static void stackOverFlowErrorExample() {
        for (int i = 0; i < 100_000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(Duration.ofSeconds(1L));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }
}
