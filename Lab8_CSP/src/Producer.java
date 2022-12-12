import org.jcsp.lang.*;

public class Producer implements CSProcess {

    final One2OneChannelInt channel;

    public Producer (final One2OneChannelInt out) {
        channel = out;
    }

    public void run () {
        int item = (int)(Math.random()*100)+1;
        channel.out().write(item);
    }
}