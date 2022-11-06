import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Data {

    private int buffer = 0;
    private int bufferCapacity = 9;
    private int productSizeCapacity = 5;
    private boolean producerWaits = false;
    private boolean consumerWaits = false;

    ReentrantLock lock = new ReentrantLock();
    Condition bufferFullCondition = lock.newCondition();
    Condition bufferEmptyCondition = lock.newCondition();
    Condition producerHungryCondition = lock.newCondition();
    Condition consumerHungryCondition = lock.newCondition();


    public void produce(int id) {
        int productSize = (int) (Math.random() * (productSizeCapacity - 1)) + 1;
        try {
            lock.lock();
            int counter = 0;
            while (producerWaits) {
                producerHungryCondition.await();
            }
            while (buffer > bufferCapacity - productSize) {
                producerWaits = true;
                counter++;
                System.out.print("Couldn't produce ");
                System.out.print(productSize);
                System.out.print(" products ");
                System.out.print(counter);
                System.out.print(" times. Id: ");
                System.out.println(id);

                bufferFullCondition.await();
            }
            producerWaits = false;
            producerHungryCondition.signal();
            buffer += productSize;
            bufferEmptyCondition.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
//        System.out.print("Produced. Buffer: ");
        System.out.println(buffer);
//        System.out.print(". My id: ");
//        System.out.println(id);
    }

    public void consume() {
        int productSize = (int) (Math.random() * (productSizeCapacity - 1)) + 1;
        try {
            lock.lock();
            while (consumerWaits) {
                consumerHungryCondition.await();
            }
            while (buffer < productSize) {
                consumerWaits = true;
                bufferEmptyCondition.await();
            }
            consumerWaits = false;
            consumerHungryCondition.signal();
            buffer -= productSize;
            bufferFullCondition.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
//        System.out.print("Consumed. Buffer: ");
        System.out.println(buffer);
    }
}