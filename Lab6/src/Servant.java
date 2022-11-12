public class Servant {

    final private int bufferCapacity;
    private int buffer;

    public Servant(int bufferCapacity) {
        this.bufferCapacity = bufferCapacity;
        this.buffer = 0;
    }
    // Servant methods
    public boolean produce(int productSize) {
        this.buffer += productSize;
//        System.out.printf("Buffer changed from %d to %d%n", buffer - productSize, buffer);
        return true;
    }
    public boolean consume(int productSize) {
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
