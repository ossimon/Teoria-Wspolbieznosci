import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Data {

    private int buffer = 0;
    final private int bufferCapacity;
    private boolean stop = false;
    final private Watch watch;
    final private int sleepLength;

    ReentrantLock bufferLock = new ReentrantLock();
    ReentrantLock consumerLock = new ReentrantLock();
    ReentrantLock producerLock = new ReentrantLock();
    Condition bufferCondition = bufferLock.newCondition();

    public Data(int bufferCapacity, Watch watch, int sleepLength) {
        this.bufferCapacity = bufferCapacity;
        this.watch = watch;
        this.sleepLength = sleepLength;
    }

    public void produce(int productSize, int id) {
        try {
            producerLock.lock();
            bufferLock.lock();
            while (buffer > bufferCapacity - productSize) {
                if (stop) {
                    return;
                }
                bufferCondition.await();
            }
            Thread.sleep(sleepLength);
            buffer += productSize;
            bufferCondition.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bufferLock.unlock();
            producerLock.unlock();
        }
//        System.out.println(buffer);
    }

    public void consume(int productSize, int id) {

        try {
            consumerLock.lock();
            bufferLock.lock();
            while (buffer < productSize) {
                if (stop) {
                    return;
                }
                bufferCondition.await();
            }
            if (productSize == -1) {
                this.stop = true;
                bufferCondition.signal();
                return;
            }
            Thread.sleep(sleepLength);
            buffer -= productSize;
            bufferCondition.signal();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bufferLock.unlock();
            consumerLock.unlock();
        }
//        System.out.println(buffer);
    }

}