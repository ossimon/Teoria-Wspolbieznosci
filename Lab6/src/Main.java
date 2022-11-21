public class Main {

    public static void main(String[] args) {

//        int bufferCapacity = 10;
//        int numberOfProducers = 1;
//        int numberOfConsumers = 5;
//
//        Proxy proxy = new Proxy(bufferCapacity);
//        Producer[] producers = new Producer[numberOfProducers];
//        Consumer[] consumers = new Consumer[numberOfConsumers];
//
//        for (int i = 0; i < numberOfConsumers; i++) {
//            consumers[i] = new Consumer(proxy, bufferCapacity, i);
//        }
//        for (int i = 0; i < numberOfProducers; i++) {
//            producers[i] = new Producer(proxy, bufferCapacity, i);
//        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        for (int i = 0; i < numberOfConsumers; i++) {
//            consumers[i].stop();
//        }
//        for (int i = 0; i < numberOfProducers; i++) {
//            producers[i].stop();
//        }
//        proxy.stop();
//        System.out.println("All threads stopped (probably?)");
    }
}