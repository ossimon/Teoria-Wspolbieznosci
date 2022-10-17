import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Data {

    private int buffer = 0;
    private int bufferCapacity = 9;
    private int productSizeCapacity = 5;
    ReentrantLock lock = new ReentrantLock();
    Condition bufferFullCondition = lock.newCondition();
    Condition bufferEmptyCondition = lock.newCondition();
    public void produce(int id) {
        int productSize = (int) (Math.random() * (productSizeCapacity - 1)) + 1;
        try {
            lock.lock();
            while(buffer > bufferCapacity - productSize) {
                bufferFullCondition.await();
            }
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
            while(buffer < productSize) {
                bufferEmptyCondition.await();
            }
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
