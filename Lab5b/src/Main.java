import java.lang.management.ThreadMXBean;

public class Main {

    public static void main(String[] args) {

        int bufferCapacity = 9;
        long timeToRun = (long) Math.pow(10., 10.);
        Watch watch = new Watch();
        Data data = new Data(bufferCapacity, watch);

        int numberOfProducers = 5;
        int numberOfConsumers = 5;
        Producer[] producers = new Producer[numberOfProducers];
        Consumer[] consumers = new Consumer[numberOfConsumers];
        Thread[] producerThreads = new Thread[numberOfProducers];
        Thread[] consumerThreads = new Thread[numberOfConsumers];

        for (int i = 0; i < numberOfConsumers; i++) {
            consumers[i] = new Consumer(data, watch, bufferCapacity, timeToRun, i);
            consumerThreads[i] = new Thread(consumers[i]);
        }
        for (int i = 0; i < numberOfProducers; i++) {
            producers[i] = new Producer(data, watch, bufferCapacity, timeToRun, i);
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
            consumedSum += consumers[i].consumed;
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