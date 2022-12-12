import org.jcsp.lang.*;

/** Main program class for Producer/Consumer example.
 * Sets up channel, creates one of each process then
 * executes them in parallel, using JCSP.
 */
public final class PCMain {
    public static void main(String[] args) {
        // Create channel object
        final One2OneChannelInt channel = Channel.one2oneInt();
        // Create and run parallel construct with a list of processes
        CSProcess[] procList = {new Producer(channel), new Consumer(channel) };

        // Processes
        Parallel par = new Parallel(procList); // PAR construct
        par.run(); // Execute processes in parallel
    }
}