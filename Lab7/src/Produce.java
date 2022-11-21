public class Produce implements MethodRequest{

    final private Servant servant;
    final private int productSize;
    final private Future result;

    public Produce(Servant servant, int productSize, Future result) {
        this.servant = servant;
        this.productSize = productSize;
        this.result = result;
    }

    @Override
    public boolean guard() {
        return servant.canProduce(productSize);
    }

    @Override
    public void call() {
        if (servant.produce(productSize)) {
            result.success();
        }
    }

    @Override
    public String toString() {
        return "Produce " + Integer.toString(productSize);
    }
}
