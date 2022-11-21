import java.util.Random;

public class ProducerConsumer implements Runnable {

    final private ClientType type;
    final private Proxy proxy;
    final private int bufferCapacity;
    final private int maxProductSize;
    final private int id;
    private boolean running;
    final private int sleepLength;

    public ProducerConsumer(ClientType type, Proxy proxy, int bufferCapacity, int maxProductSize, int id, int sleepLength) {
        this.type = type;
        this.proxy = proxy;
        this.bufferCapacity = bufferCapacity;
        this.maxProductSize = maxProductSize;
        this.id = id;
        this.running = true;
        this.sleepLength = sleepLength;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        Random generator = new Random(id);
        Future result = new Future();
        result.success();
        int productSize;
        int numberOfTries = 0;
        double averageNumberOfTries = 0;
        int numberOfProductions = 0;
        for (int i = 0; i < id + 5; i++) productSize = generator.nextInt(maxProductSize - 1) + 1;

        while (running) {
            if (result.rendezvous() != 0) {
                numberOfTries = 0;
                numberOfProductions++;
                productSize = generator.nextInt(maxProductSize - 1) + 1;
//                System.out.printf("ProducerConsumer Id: %d Trying to %s: %d%n", id, this.type.activity(), productSize);

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

            numberOfTries++;
            if (result.rendezvous() == 1) {
                averageNumberOfTries *= (double) numberOfProductions / (numberOfProductions + 1);
                averageNumberOfTries += (double) numberOfTries / (numberOfProductions + 1);
//                System.out.printf("Managed to %s in %d tries.%n", this.type.activity(), numberOfTries);
            }
//            System.out.printf("%s Id: %d result: %d%n", this.type.toString(), id, result.rendezvous());
        }
        System.out.printf("Average number of tries to %s: %.2f%n", this.type.activity(), averageNumberOfTries);
    }
    public void stop() {
        running = false;
    }
}