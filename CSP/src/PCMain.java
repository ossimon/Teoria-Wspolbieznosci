
import org.jcsp.lang.*;

/** Main program class for Producer/Consumer example.
 * Sets up channels, creates processes then
 * executes them in parallel, using JCSP.
 */
public final class PCMain2
{
    public static void main (String[] args)
    {
        new PCMain2();
    } // main

    public PCMain2 ()
    { // Create channel objects
        final One2OneChannelInt[] prodChan = { new
                One2OneChannelInt(), new One2OneChannelInt() }; // Producers
        final One2OneChannelInt[] consReq = { new
                One2OneChannelInt(), new One2OneChannelInt() }; // Consumer requests
        final One2OneChannelInt[] consChan = { new
                One2OneChannelInt(), new One2OneChannelInt() }; // Consumer data
        // Create parallel construct
        CSProcess[] procList = { new Producer2(prodChan[0], 0),
                new Producer2(prodChan[1], 100),
                new Buffer(prodChan, consReq,
                        consChan),
                new Consumer2(consReq[0],
                        consChan[0]),
                new Consumer2(consReq[1],
                        consChan[1]) }; // Processes
        Parallel par = new Parallel(procList); // PAR construct
        par.run(); // Execute processes in parallel
    } // PCMain constructor
} // class PCMain2