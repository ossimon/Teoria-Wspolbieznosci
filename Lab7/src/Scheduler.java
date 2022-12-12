import java.util.ArrayDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Scheduler implements Runnable{

    final private int bufferCapacity;
    private boolean running;
    final private LinkedBlockingQueue<MethodRequest> activationQueue;
    final private ArrayDeque<MethodRequest> unhandledRequests;

    public Scheduler(int bufferCapacity) {
        this.bufferCapacity = bufferCapacity;
        this.activationQueue = new LinkedBlockingQueue<>();
        this.unhandledRequests = new ArrayDeque<>();
        Thread schedulerThread = new Thread(this);
        this.running = true;
        schedulerThread.start();
    }

    public void enqueue(MethodRequest methodRequest) {
        try {
            activationQueue.put(methodRequest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void dispatch() {
        MethodRequest methodRequest;
        MethodRequest unhandledRequest;
        while (running) {

            try {
                methodRequest = activationQueue.take();
                handle(methodRequest);

                while (!unhandledRequests.isEmpty()) {
                    unhandledRequest = unhandledRequests.pop();
                    while (!unhandledRequest.guard()) {
                        methodRequest = activationQueue.take();
                        handle(methodRequest);
                    }
                    unhandledRequest.call();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void handle(MethodRequest methodRequest) {
        if (methodRequest.guard()) {
            methodRequest.call();
        }
        else {
            unhandledRequests.add(methodRequest);
        }
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        dispatch();
    }
}
