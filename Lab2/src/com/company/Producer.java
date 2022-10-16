package com.company;

public class Producer implements Runnable {

    private Data data;
    private int id;

    public Producer(Data data, int id) {
        this.data = data;
        this.id = id;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            data.produce(id);
        }
        System.out.print("################Finished working (producer) id: ");
        System.out.print(id);
    }
}