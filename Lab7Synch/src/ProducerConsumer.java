import java.util.Random;

public class ProducerConsumer implements Runnable {

    final private ClientType type;
    final private Data data;
    final private Watch watch;
    final private int bufferCapacity;
    final private long timeToRun;
    final private int id;
    final private int sleepLength;
    public long produced = 0;

    public ProducerConsumer(ClientType type, Data data, Watch watch, int bufferCapacity, long timeToRun, int id, int sleepLength) {
        this.type = type;
        this.data = data;
        this.watch = watch;
        this.bufferCapacity = bufferCapacity;
        this.timeToRun = timeToRun;
        this.id = id;
        this.sleepLength = sleepLength;
    }

    public void run() {
        Random generator = new Random(id);

        while (watch.getElapsedTime() < timeToRun) {
            int productSize = (int) (generator.nextDouble() * (bufferCapacity - 1) / 2);
            produced += productSize;

            switch (this.type) {
                case CONSUMER -> data.produce(productSize, id);
                case PRODUCER -> data.consume(productSize, id);
            }

            try {
                Thread.sleep(sleepLength);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (this.type == ClientType.CONSUMER) data.consume(-1, id);

    }
}