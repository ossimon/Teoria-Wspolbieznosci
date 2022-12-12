import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

import java.util.Random;

public class Client implements CSProcess
{
    private final ClientType type;
    private final One2OneChannelInt in;
    private final One2OneChannelInt req;
    private final int[] buffer;
    private final int bufferCapacity;
    private final int maxProductSize;
    private final int id;

    public Client (ClientType type, int[] buffer,
                   int bufferCapacity, int maxProductSize,
                   One2OneChannelInt req, One2OneChannelInt in,
                   int id)
    {
        this.buffer = buffer;
        this.bufferCapacity = bufferCapacity;
        this.maxProductSize = maxProductSize;
        this.type = type;
        this.req = req;
        this.in = in;
        this.id = id;
    }

    public void run ()
    {
        System.out.println(id);
        long start = System.nanoTime();

        int access;
        int productSize = 1;
        int newBufferValue;
        int no_blocks = 0;

        boolean print = true;

        while (true)
        {
            if (print) {
                System.out.println(id);
                print = false;
            }
//            if (System.nanoTime() - start > 500000000) {
//                System.out.printf("Id: %d, Blocked %d times.%n", id, no_blocks);
//            }
            req.out().write(-1); // Request access - blocks until access is available
            access = in.in().read();
            if (access < 0)
                break;

            if (type == ClientType.CONSUMER) {
                productSize *= -1;
            }
            newBufferValue = buffer[access] + productSize;

            if (newBufferValue >= 0 && newBufferValue <= bufferCapacity) {
                buffer[access] += productSize;
//                System.out.printf("%s %d, buffer number: %d, new value: %d%n", type.toString(), id, access, buffer[access]);
            }
            else
                no_blocks++;

            req.out().write(access);
        }
    }
}