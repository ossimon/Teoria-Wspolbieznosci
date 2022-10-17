
public class Consumer implements Runnable {

    private Data data;

    public Consumer(Data data) {
        this.data = data;
    }

    public void run() {
        for (int i = 0; i < 2000; i++) {
            data.consume();
        }
        System.out.println("################Finnished working (consumer)");
    }
}