import org.jcsp.lang.One2OneChannelInt;

public class Buffer {

    private final One2OneChannelInt[] inChannels;
    private final One2OneChannelInt[] outChannels;

    public Buffer(One2OneChannelInt[] inChannels, One2OneChannelInt[] outChannels) {
        this.inChannels = inChannels;
        this.outChannels = outChannels;
    }

    public void run () {

    }
}
