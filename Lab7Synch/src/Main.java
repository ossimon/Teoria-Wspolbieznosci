import java.lang.management.ThreadMXBean;

public class Main {

    public static void main(String[] args) {

        int servantSleepLength = 300;
        int clientSleepLength = 100;
        int bufferCapacity = 9;
        long timeToRun = (long) Math.pow(10., 10.);
        Watch watch = new Watch();
        Data data = new Data(bufferCapacity, watch, servantSleepLength);

        int numberOfProducers = 5;
        int numberOfConsumers = 5;
        ProducerConsumer[] producers = new ProducerConsumer[numberOfProducers];
        ProducerConsumer[] consumers = new ProducerConsumer[numberOfConsumers];
        Thread[] producerThreads = new Thread[numberOfProducers];
        Thread[] consumerThreads = new Thread[numberOfConsumers];

        for (int i = 0; i < numberOfConsumers; i++) {
            consumers[i] = new ProducerConsumer(ClientType.CONSUMER, data, watch, bufferCapacity, timeToRun, i, clientSleepLength);
            consumerThreads[i] = new Thread(consumers[i]);
        }
        for (int i = 0; i < numberOfProducers; i++) {
            producers[i] = new ProducerConsumer(ClientType.PRODUCER, data, watch, bufferCapacity, timeToRun, i, clientSleepLength);
            producerThreads[i] = new Thread(producers[i]);
        }

        watch.start();
        for (int i = 0; i < numberOfConsumers; i++) {
            consumerThreads[i].start();
        }
        for (int i = 0; i < numberOfProducers; i++) {
            producerThreads[i].start();
        }

        try {
            for (int i = 0; i < numberOfConsumers; i++) {
                consumerThreads[i].join();
            }
            for (int i = 0; i < numberOfProducers; i++) {
                producerThreads[i].join();
            }
            watch.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final time (in seconds):");
        System.out.println(watch.getFinalTime() / Math.pow(10., 9.));

        long consumedSum = 0;
        System.out.println("Average consumption:");
        for (int i = 0; i < numberOfConsumers; i++) {
            consumedSum += consumers[i].produced;
        }
        System.out.println(consumedSum / numberOfConsumers);

        long producedSum = 0;
        System.out.println("Average production:");
        for (int i = 0; i < numberOfProducers; i++) {
            producedSum += producers[i].produced;
        }
        System.out.println(producedSum / numberOfProducers);
    }
}