import org.jcsp.lang.*;


/** Consumer class: reads one int from input channel, displays it, then
 * terminates.
 */
public class Consumer implements CSProcess
{
    private final One2OneChannelInt channel;

    public Consumer (One2OneChannelInt in) {
        channel = in;
    }

    public void run () {
        int item = channel.in().read();
        System.out.println(item);
    }
}