public class Servant {

    final private int bufferCapacity;
    private int buffer;
    final private int sleepLength;

    public Servant(int bufferCapacity, int startBufferValue, int sleepLength) {
        this.bufferCapacity = bufferCapacity;
        this.buffer = startBufferValue;
        this.sleepLength = sleepLength;
    }
    // Servant methods
    public boolean produce(int productSize) {
        this.buffer += productSize;
//        System.out.printf("Buffer changed from %d to %d%n", buffer - productSize, buffer);
        return true;
    }
    public boolean consume(int productSize) {
        try {
            Thread.sleep(sleepLength);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.buffer -= productSize;
//        System.out.printf("Buffer changed from %d to %d%n", buffer + productSize, buffer);
        return true;
    }
    // Predicates
    public boolean canProduce(int productSize) {
        return buffer + productSize <= bufferCapacity;
    }
    public boolean canConsume(int productSize) {
        return productSize < buffer;
    }

}
