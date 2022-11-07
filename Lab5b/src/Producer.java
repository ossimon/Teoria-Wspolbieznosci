import java.util.Random;

public class Producer implements Runnable {

    final private Data data;
    final private Watch watch;
    final private int bufferCapacity;
    final private long timeToRun;
    final private int id;
    public long produced = 0;

    public Producer(Data data, Watch watch, int bufferCapacity, long timeToRun, int id) {
        this.data = data;
        this.watch = watch;
        this.bufferCapacity = bufferCapacity;
        this.timeToRun = timeToRun;
        this.id = id;
    }

    public void run() {
        Random generator = new Random(id);
        while (watch.getElapsedTime() < timeToRun) {
            int productSize = (int) (generator.nextDouble() * (bufferCapacity - 1) / 2);
            produced += productSize;
            data.produce(productSize, id);
        }
        System.out.print("################Finished working (producer) id: ");
        System.out.print(id);
    }
}