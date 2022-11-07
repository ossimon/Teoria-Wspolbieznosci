public class Watch {
    private long startTime;
    private long stopTime;

    public void start(){
        startTime = System.nanoTime();
        System.out.println(startTime);
    }
    public void stop(){
        stopTime = System.nanoTime();
        System.out.println(stopTime);
    }
    public long getElapsedTime(){
        long currentTime = System.nanoTime();
        return currentTime - startTime;
    }
    public long getFinalTime(){
        return stopTime - startTime;
    }
}
