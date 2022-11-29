import org.jcsp.lang.*;
/** Producer class: produces 100 random integers and sends them on
 * output channel, then sends -1 and terminates.
 * The random integers are in a given range [start...start+100)
 */
public class Producer implements CSProcess
{
    private final One2OneChannelInt channel;
    private final int start;
    private final int productsToSend;

    public Producer (One2OneChannelInt out, int start, int productsToSend)
    {
        channel = out;
        this.start = start;
        this.productsToSend = productsToSend;
    }

    public void run ()
    {
        int item = start;
        for (int k = 0; k < productsToSend; k++)
        {
            item += 1;
            channel.out().write(item);
        }
        channel.out().write(-1);
        System.out.println("Producer" + start + " ended.");
    }
}