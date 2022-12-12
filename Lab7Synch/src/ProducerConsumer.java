import java.util.Random;

public class ProducerConsumer implements Runnable {

    public long produced = 0;
    public long workCount = 0;
    public long productionCount = 0;
    private final ClientType type;
    private final Data data;
    private final int maxProductSize;
    private final int id;
    private final int sleepLength;
    private boolean running;

    public ProducerConsumer(ClientType type, Data data, int maxProductSize, int id, int sleepLength) {
        this.type = type;
        this.data = data;
        this.maxProductSize = maxProductSize;
        this.id = id;
        this.sleepLength = sleepLength;
        this.running = true;
    }

    public void run() {
        Random generator = new Random(id);

        while (running) {
            int productSize = generator.nextInt(maxProductSize) + 1;

            boolean result = switch (this.type) {
                case CONSUMER -> data.produce(productSize, id);
                case PRODUCER -> data.consume(productSize, id);
            };
            if (result) {
                productionCount++;
                produced += productSize;
            }

            try {
                Thread.sleep(sleepLength);
                this.workCount++;
            } catch (InterruptedException ignored) {

            }
        }
    }

    public void stop(Thread thisThread) {
        this.running = false;
        thisThread.interrupt();
    }
}