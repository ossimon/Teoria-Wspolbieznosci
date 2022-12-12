public class Main {

    public static void main(String[] args) {

        Data data = new Data();
        Thread producer1 = new Thread(new Producer(data, 1));
        Thread producer2 = new Thread(new Producer(data,2));
        Thread consumer = new Thread(new Consumer(data));

        producer1.start();
        producer2.start();
        consumer.start();
    }

}