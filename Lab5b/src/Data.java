import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Data {

    private int buffer = 0;
    final private int bufferCapacity;
    private boolean stop = false;
    final private Watch watch;
    private boolean producerWaits = false;
    private boolean consumerWaits = false;

    ReentrantLock lock = new ReentrantLock();
    Condition bufferFullCondition = lock.newCondition();
    Condition bufferEmptyCondition = lock.newCondition();
    Condition producerHungryCondition = lock.newCondition();
    Condition consumerHungryCondition = lock.newCondition();

    public Data(int bufferCapacity, Watch watch) {
        this.bufferCapacity = bufferCapacity;
        this.watch = watch;
    }

    public void produce(int productSize, int id) {
        try {
            lock.lock();
            while (producerWaits) {
                if (stop) {
                    return;
                }
                producerHungryCondition.await();
            }
            while (buffer > bufferCapacity - productSize) {
                if (stop) {
                    producerWaits = false;
                    producerHungryCondition.signal();
                    return;
                }
                producerWaits = true;
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
//        System.out.println(buffer);
    }

    public void consume(int productSize, int id) {
        try {
            lock.lock();
            while (consumerWaits) {
                if (stop) {
                    return;
                }
                consumerHungryCondition.await();
            }
            while (buffer < productSize) {
                if (stop) {
                    return;
                }
                consumerWaits = true;
                bufferEmptyCondition.await();
            }
            if (productSize == -1) {
                consumerWaits = false;
                this.stop = true;
                consumerHungryCondition.signal();
                bufferFullCondition.signal();
                return;
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
//        System.out.println(buffer);
    }

}