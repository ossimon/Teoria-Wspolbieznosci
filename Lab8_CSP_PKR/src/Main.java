import org.jcsp.lang.*;
/** Main program class for Producer/Consumer example.
 * Sets up channels, creates processes then
 * executes them in parallel, using JCSP.
 */
public final class Main
{
    private static final int numberOfProducers = 5;
    private static final int numberOfConsumers = 5;
    private static final int bufferSize = 10;
    private static final int productsToSend = 10;

    private static One2OneChannelInt[] prodChan;
    private static One2OneChannelInt[] consReq; // Consumer requests
    private static One2OneChannelInt[] consChan; // Consumer data
    private static CSProcess[] procList;

    public static void main (String[] args)
    {
        prodChan = createChannels(numberOfProducers);
        consReq = createChannels(numberOfConsumers);
        consChan = createChannels(numberOfConsumers);

        // Create parallel construct
        procList = createProcList(); // Processes
        Parallel par = new Parallel(procList); // PAR construct
        par.run(); // Execute processes in parallel
    }

    private static One2OneChannelInt[] createChannels(int numberOfChannels) {

        // Initiate and create the channels
        One2OneChannelInt[] channels = new One2OneChannelInt[numberOfChannels];
        for (int i = 0; i < numberOfChannels; i++)
            channels[i] = Channel.one2oneInt();

        return channels;
    }

    private static CSProcess[] createProcList() {
        CSProcess[] processes = new CSProcess[numberOfProducers + numberOfConsumers + 1];

        // Create producers
        for (int i = 0; i < numberOfProducers; i++)
            processes[i] = new Producer(prodChan[i], 1000 * i, productsToSend);

        // Create consumers
        for (int i = 0; i < numberOfConsumers; i++)
            processes[numberOfProducers + i] = new Consumer(consReq[i], consChan[i]);

        // Create buffer
        processes[numberOfProducers + numberOfConsumers] =
                new Buffer(bufferSize, prodChan, consReq, consChan, numberOfProducers, numberOfConsumers);

        return processes;
    }
}