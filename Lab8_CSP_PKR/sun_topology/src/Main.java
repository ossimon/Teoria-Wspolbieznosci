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
    private static final int bufferCapacity = 10;
    private static final int maxProductSize = 5;
    private static final int[] buffer = new int[bufferSize];

    private static One2OneChannelInt[] secChan;
    private static One2OneChannelInt[] reqChan; // Requests
    private static One2OneChannelInt[] inChan; // Accesses
    private static CSProcess[] procList;
    private static Secretary[] secretaries;

    public static void main (String[] args) throws InterruptedException {
        for (int i = 0; i < bufferSize; i++)
            buffer[i] = 0;

        // Create channels
        secChan = createChannels(numberOfProducers + numberOfConsumers);
        reqChan = createChannels(numberOfProducers + numberOfConsumers);
        inChan = createChannels(numberOfProducers + numberOfConsumers);

        // Create parallel construct
        procList = createProcList(); // Processes
        Parallel par = new Parallel(procList); // PAR construct
        par.run(); // Execute processes in parallel

        System.out.println("trying to sleep?");

        Thread.sleep(500);

        System.out.println("trying to shut everything down");

//        for (int i = 0; i < numberOfConsumers + numberOfProducers; i++) {
//            secretaries[i].shutdown();
//        }
    }

    private static One2OneChannelInt[] createChannels(int numberOfChannels) {

        // Initiate and create the channels
        One2OneChannelInt[] channels = new One2OneChannelInt[numberOfChannels];
        for (int i = 0; i < numberOfChannels; i++)
            channels[i] = Channel.one2oneInt();

        return channels;
    }

    private static CSProcess[] createProcList() {
        CSProcess[] processes = new CSProcess[2 * (numberOfProducers + numberOfConsumers)];
        secretaries = new Secretary[numberOfProducers + numberOfConsumers];

        // Create producers
        for (int i = 0; i < numberOfProducers; i++)
            processes[i] =
                    new Client(ClientType.PRODUCER, buffer, bufferCapacity,
                            maxProductSize, reqChan[i], inChan[i], i);

        // Create consumers
        for (int i = 0; i < numberOfConsumers; i++)
            processes[numberOfProducers + i] =
                    new Client(ClientType.CONSUMER, buffer, bufferCapacity,
                            maxProductSize, reqChan[numberOfProducers + i], inChan[numberOfProducers + i], i);

        // Create producer secretaries
        for (int i = 0; i < numberOfProducers; i++) {
            secretaries[i] =
                    new Secretary(ClientType.PRODUCER,
                            reqChan[i], inChan[i],
                            secChan[i], secChan[(i + 1)],
                            i);
            processes[numberOfConsumers + numberOfProducers + i] = secretaries[i];
        }

        // Create consumer secretaries
        for (int i = 0; i < numberOfConsumers; i++) {
            secretaries[numberOfProducers + i] =
                    new Secretary(ClientType.CONSUMER,
                            reqChan[numberOfProducers + i], inChan[numberOfProducers + i],
                            secChan[numberOfProducers + i], secChan[(numberOfProducers + i + 1) % (numberOfConsumers + numberOfProducers)],
                            numberOfProducers + i);
            processes[numberOfConsumers + 2 * numberOfProducers + i] = secretaries[numberOfProducers + i];
        }

        for (int i = 0; i < bufferSize; i++)
            secretaries[numberOfProducers + numberOfConsumers - 1].addAccess(i);

        return processes;
    }
}