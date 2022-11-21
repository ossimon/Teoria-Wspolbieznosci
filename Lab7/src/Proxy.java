public class Proxy {

    final private int bufferCapacity;
    final private Servant servant;
    final private Scheduler scheduler;

    public Proxy(int bufferCapacity, int startBufferValue, int servantSleepLength) {
        this.bufferCapacity = bufferCapacity;
        this.servant = new Servant(bufferCapacity, startBufferValue, servantSleepLength);
        this.scheduler = new Scheduler(bufferCapacity);
    }
    public Future produce(int productSize) {
        return request(productSize, true);
    }
    public Future consume(int productSize) {
        return request(productSize, false);
    }
    private Future request(int productSize, boolean produce) {

        if (productSizeBad(productSize)) {
            return new Future(true);
        }
        Future result = new Future();
        MethodRequest methodRequest;
        if (produce) {
            methodRequest = new Produce(servant, productSize, result);
        }
        else {
            methodRequest = new Consume(servant, productSize, result);
        }
        scheduler.enqueue(methodRequest);
        return result;
    }
    private boolean productSizeBad(int productSize) {
        return 2 * productSize - 1 > bufferCapacity;
    }
    public void stop() {
        scheduler.stop();
    }
}
