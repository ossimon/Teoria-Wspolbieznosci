public class Main {

    public static void main(String[] args) throws InterruptedException {

        int bufferCapacity = 20000;
        int maxProductSize = 5;
        int startBufferValue = 10000;
        int numberOfProducers = 20;
        int numberOfConsumers = 20;
        int timeToRun = 10000;

        int sleepMin = 100;
        int sleepMax = 1000;
        int sleepJump = 100;
        System.out.print("[");
        for (int a = 0; a < 2; a++) {
            if (a == 1) {
                sleepMin = 100;
                sleepMax = 1000;
                sleepJump = 100;
                break;
            }

        for (int clientSleepLength = sleepMin; clientSleepLength <= sleepMax; clientSleepLength += sleepJump) {
            System.out.print("[");
            for (int servantSleepLength = sleepMin; servantSleepLength <= sleepMax; servantSleepLength += sleepJump) {

                Proxy proxy = new Proxy(bufferCapacity, startBufferValue, servantSleepLength);
                ProducerConsumer[] producers = new ProducerConsumer[numberOfProducers];
                ProducerConsumer[] consumers = new ProducerConsumer[numberOfConsumers];
                Thread[] producerThreads = new Thread[numberOfProducers];
                Thread[] consumerThreads = new Thread[numberOfConsumers];

                for (int i = 0; i < numberOfProducers; i++) {
                    producers[i] = new ProducerConsumer(ClientType.PRODUCER, proxy, maxProductSize, i,
                            clientSleepLength);
                    producerThreads[i] = new Thread(producers[i]);
                }
                for (int i = 0; i < numberOfConsumers; i++) {
                    consumers[i] = new ProducerConsumer(ClientType.CONSUMER, proxy, maxProductSize, i,
                            clientSleepLength);
                    consumerThreads[i] = new Thread(consumers[i]);
                }

                for (int i = 0; i < numberOfConsumers; i++)
                    consumerThreads[i].start();
                for (int i = 0; i < numberOfProducers; i++)
                    producerThreads[i].start();

                try {
                    Thread.sleep(timeToRun);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < numberOfProducers; i++) {
                    producers[i].stop();
                }
                for (int i = 0; i < numberOfConsumers; i++) {
                    consumers[i].stop();
                }
                for (int i = 0; i < numberOfConsumers; i++)
                    consumerThreads[i].join();
                for (int i = 0; i < numberOfProducers; i++)
                    producerThreads[i].join();

                proxy.stop();

                int totalProductions = 0;
                for (int i = 0; i < numberOfProducers; i++)
                    totalProductions += producers[i].productionCount;
                for (int i = 0; i < numberOfConsumers; i++)
                    totalProductions += consumers[i].productionCount;

                int totalWorkCount = 0;
                for (int i = 0; i < numberOfProducers; i++)
                    totalWorkCount += producers[i].workCount;
                for (int i = 0; i < numberOfConsumers; i++)
                    totalWorkCount += consumers[i].workCount;

                System.out.printf("[%d, %d]", totalProductions, totalWorkCount);
                if (servantSleepLength + sleepJump <= sleepMax)
                    System.out.print(", ");
            }
            System.out.print("]");
            if (clientSleepLength + sleepJump <= sleepMax)
                System.out.printf(",%n");
        }

        System.out.println("]");
        }
    }
}