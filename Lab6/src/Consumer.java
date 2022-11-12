import java.util.Random;

public class Consumer implements Runnable {

    final private Proxy proxy;
    final private int bufferCapacity;
    final private int id;
    private boolean running;

    public Consumer(Proxy proxy, int bufferCapacity, int id) {
        this.proxy = proxy;
        this.bufferCapacity = bufferCapacity;
        this.id = id;
        this.running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        Random generator = new Random(11 * id);
        Future result = new Future();
        result.success();
        int productSize;

        while (running) {
            if (result.rendezvous() == 1) {
                productSize = (int) (generator.nextDouble() * (bufferCapacity - 1) / 2) + 1;
                System.out.printf("Consumer Id: %d Trying to consume: %d%n", id, productSize);
                result = proxy.consume(productSize);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("Consumer Id: %d Result: %d%n", id, result.rendezvous());
        }
    }
    public void stop() {
        running = false;
    }
}