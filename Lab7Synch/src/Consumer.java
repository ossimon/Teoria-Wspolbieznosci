import java.util.Random;

public class Consumer implements Runnable {

    final private Data data;
    final private Watch watch;
    final private int bufferCapacity;
    final private long timeToRun;
    final private int id;
    final private int sleepLength;
    public long consumed = 0;

    public Consumer(Data data, Watch watch, int bufferCapacity, long timeToRun, int id, int sleepLength) {
        this.data = data;
        this.watch = watch;
        this.bufferCapacity = bufferCapacity;
        this.timeToRun = timeToRun;
        this.id = id;
        this.sleepLength = sleepLength;
    }

    public void run() {
        Random generator = new Random(id);
        while (watch.getElapsedTime() < timeToRun) {
            int productSize = (int) (generator.nextDouble() * (bufferCapacity - 1) / 2);
            consumed += productSize;
            data.consume(productSize, id);
            try {
                Thread.sleep(sleepLength);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        data.consume(-1, id);
        System.out.print("################Finished working (consumer) id: ");
        System.out.print(id);
    }
}