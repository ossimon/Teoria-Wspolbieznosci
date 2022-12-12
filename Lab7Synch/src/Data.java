import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Data {

    private int buffer;
    final private int bufferCapacity;
    final private int sleepLength;

    ReentrantLock bufferLock = new ReentrantLock();
    ReentrantLock consumerLock = new ReentrantLock();
    ReentrantLock producerLock = new ReentrantLock();
    Condition bufferCondition = bufferLock.newCondition();

    public Data(int bufferCapacity, int startBufferValue, int sleepLength) {
        this.buffer = startBufferValue;
        this.bufferCapacity = bufferCapacity;
        this.sleepLength = sleepLength;
    }

    public boolean produce(int productSize, int id) {
        try {
            producerLock.lock();
            bufferLock.lock();
            while (buffer > bufferCapacity - productSize) {
                bufferCondition.await();
            }
            Thread.sleep(sleepLength);
            buffer += productSize;
            bufferCondition.signal();
            return true;
        } catch (InterruptedException e) {
            return false;
        } finally {
            bufferLock.unlock();
            producerLock.unlock();
        }
    }

    public boolean consume(int productSize, int id) {

        try {
            consumerLock.lock();
            bufferLock.lock();
            while (buffer < productSize) {
                bufferCondition.await();
            }
            Thread.sleep(sleepLength);
            buffer -= productSize;
            bufferCondition.signal();
            return true;
        } catch (InterruptedException e) {
            return false;
        } finally {
            bufferLock.unlock();
            consumerLock.unlock();
        }
    }

}