public class Consume implements MethodRequest{

    final private Servant servant;
    final private int productSize;
    final private Future result;

    public Consume(Servant servant, int productSize, Future result) {
        this.servant = servant;
        this.productSize = productSize;
        this.result = result;
    }

    @Override
    public boolean guard() {
        return servant.canConsume(productSize);
    }

    @Override
    public void call() {
        if (servant.consume(productSize)) {
            result.success();
        }
    }

    @Override
    public String toString() {
        return "Consume " + Integer.toString(productSize);
    }
}
