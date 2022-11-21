public class Main {

    public static void main(String[] args) {

        int bufferCapacity = 100;
        int maxProductSize = 50;
        int startBufferValue = 0;
        int numberOfProducers = 5;
        int numberOfConsumers = 5;
        int sleepLength = 100;
        int servantSleepLength = 300;
        int timeToRun = 10000;

        Proxy proxy = new Proxy(bufferCapacity, startBufferValue, servantSleepLength);
        ProducerConsumer[] consumers = new ProducerConsumer[numberOfConsumers];
        ProducerConsumer[] producers = new ProducerConsumer[numberOfProducers];

        for (int i = 0; i < numberOfConsumers; i++) {
            consumers[i] = new ProducerConsumer(ClientType.CONSUMER, proxy, bufferCapacity, maxProductSize, i, sleepLength);
        }
        for (int i = 0; i < numberOfProducers; i++) {
            producers[i] = new ProducerConsumer(ClientType.PRODUCER, proxy, bufferCapacity, maxProductSize, i, sleepLength);
        }

        try {
            Thread.sleep(timeToRun);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < numberOfConsumers; i++) {
            consumers[i].stop();
        }
        for (int i = 0; i < numberOfProducers; i++) {
            producers[i].stop();
        }
        proxy.stop();
        System.out.println("All threads stopped (probably?)");
    }
}