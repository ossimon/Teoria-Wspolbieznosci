import java.util.Random;

public class ProducerConsumer implements Runnable {

    public long produced = 0;
    public long workCount = 0;
    public long productionCount = -1;
    private final ClientType type;
    private final Proxy proxy;
    private final int maxProductSize;
    private final int id;
    private final int sleepLength;
    private boolean running;

    public ProducerConsumer(ClientType type, Proxy proxy, int maxProductSize, int id, int sleepLength) {
        this.type = type;
        this.proxy = proxy;
        this.maxProductSize = maxProductSize;
        this.id = id;
        this.running = true;
        this.sleepLength = sleepLength;
    }

    public void run() {
        int productSize;

        Random generator = new Random(id);
        Future result = new Future();
        result.success();
        for (int i = 0; i < id + 5; i++) productSize = generator.nextInt(maxProductSize) + 1;
        productSize = 0;

        while (running) {
            if (result.rendezvous() != 0) {
                this.productionCount++;
                produced += productSize;
                productSize = generator.nextInt(maxProductSize) + 1;

                result = switch (this.type) {
                    case PRODUCER -> proxy.produce(productSize);
                    case CONSUMER -> proxy.consume(productSize);
                };
            }
            try {
                Thread.sleep(sleepLength);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            workCount++;
        }
    }
    public void stop() {
        running = false;
    }
}