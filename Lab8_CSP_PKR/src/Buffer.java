import org.jcsp.lang.*;
/** Buffer class: Manages communication between Producer2
 * and Consumer2 classes.
 */
public class Buffer implements CSProcess
{
    private final One2OneChannelInt[] in; // Input from Producer
    private final One2OneChannelInt[] req; // Request for data from Consumer
    private final One2OneChannelInt[] out; // Output to Consumer
    // The buffer itself
    private final int[] buffer;
    // Subscripts for buffer
    int hd = -1;
    int tl = -1;
    int numberOfProducers;
    int numberOfConsumers;

    public Buffer (int bufferSize, One2OneChannelInt[] in, One2OneChannelInt[] req, One2OneChannelInt[] out,
                   int numberOfProducers, int numberOfConsumers)
    {
        this.buffer = new int[bufferSize];
        this.in = in;
        this.req = req;
        this.out = out;
        this.numberOfProducers = numberOfProducers;
        this.numberOfConsumers = numberOfConsumers;
    }

    public void run ()
    {
        Guard[] guards = createGuardList();
        Alternative alt = new Alternative(guards);

        int countdown = numberOfProducers + numberOfConsumers; // Number of processes running
        while (countdown > 0)
        {
            int index = alt.select();
            if (index < numberOfProducers) {
                if (hd < tl + 11) // Space available
                {
                    int item = in[index].in().read();
                    if (item < 0)
                        countdown--;
                    else {
                        hd++;
                        buffer[hd % buffer.length] = item;
                    }
                }
            }
            else {
                if (tl < hd) // Item(s) available
                {
                    req[index - numberOfProducers].in().read(); // Read and discard request
                    tl++;
                    int item = buffer[tl%buffer.length];
                    out[index - numberOfProducers].out().write(item);
                }
                else if (countdown <= numberOfProducers) // Signal consumer to end
                {
                    req[index - numberOfProducers].in().read(); // Read and discard request
                    out[index - numberOfProducers].out().write(-1); // Signal end
                    countdown--;
                }
            }
        }
        System.out.println("Buffer ended.");
    }

    private Guard[] createGuardList() {
        Guard[] guards = new Guard[numberOfProducers + numberOfConsumers];
        for (int i = 0; i < numberOfProducers; i++) {
            guards[i] = in[i].in();
        }
        for (int i = 0; i < numberOfConsumers; i++) {
            guards[numberOfProducers + i] = req[i].in();
        }
        return guards;
    }
}