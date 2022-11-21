import java.util.Random;

public class Consumer implements Runnable {

    final private Proxy proxy;
    final private int bufferCapacity;
    final private int maxProductSize;
    final private int id;
    private boolean running;

    public Consumer(Proxy proxy, int bufferCapacity, int maxProductSize, int id) {
        this.proxy = proxy;
        this.bufferCapacity = bufferCapacity;
        this.maxProductSize = maxProductSize;
        this.id = id;
        this.running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        Random generator = new Random(id);
        Future result = new Future();
        result.success();
        int productSize;
        for (int i = 0; i < id + 5; i++) productSize = generator.nextInt(maxProductSize - 1) + 1;

        while (running) {
            if (result.rendezvous() != 0) {
                productSize = generator.nextInt(maxProductSize - 1) + 1;
//                System.out.printf("Consumer Id: %d Trying to consume: %d%n", id, productSize);
                result = proxy.consume(productSize);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.printf("Consumer Id: %d Result: %d%n", id, result.rendezvous());
        }
    }
    public void stop() {
        running = false;
    }
}