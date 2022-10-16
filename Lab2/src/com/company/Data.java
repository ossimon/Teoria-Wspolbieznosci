package com.company;

public class Data {
    private boolean buffer = false;

    public synchronized void produce(int id) {
        while (buffer) {
//            notify();
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Thread Interrupted");
            }
        }
        buffer = true;
        notifyAll();
        System.out.print("Produced. Buffer: ");
        System.out.print(buffer);
        System.out.print(". My id: ");
        System.out.println(id);
    }

    public synchronized void consume() {
        while (!buffer) {
//            notify();
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Thread Interrupted");
            }
        }
        buffer = false;
        notifyAll();
        System.out.print("Consumed. Buffer: ");
        System.out.println(buffer);
    }
}
