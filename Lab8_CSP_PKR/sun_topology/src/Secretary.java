import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

import java.util.ArrayDeque;

public class Secretary implements CSProcess
{
    private final ClientType type;
    private final One2OneChannelInt in;
    private final One2OneChannelInt req;
    private final One2OneChannelInt left;
    private final One2OneChannelInt right;
    private final ArrayDeque<Integer> accessQueue;
    private final int id;

    public Secretary (ClientType type,
                      One2OneChannelInt req, One2OneChannelInt in,
                      One2OneChannelInt left, One2OneChannelInt right,
                      int id)
    {
        this.type = type;
        this.req = req;
        this.in = in;
        this.left = left;
        this.right = right;
        this.accessQueue = new ArrayDeque<>();
        this.id = id;
    }

    public void run ()
    {
        int item;
        while (true)
        {
            if (id % 2 == 0) {
                item = left.in().read();
//                System.out.printf("%s, id %d got %d%n", type, id, item);
                if (item != -1) {
                    accessQueue.addLast(item);
                }
            }

            handleClient();

            if (!accessQueue.isEmpty()) {
                item = accessQueue.removeFirst();
                right.out().write(item);
//                System.out.printf("%s, id %d sent %d%n", type, id, item);
            }
            else {
                right.out().write(-1);
//                System.out.printf("%s, id %d sent %d%n", type, id, -1);
            }

            if (id % 2 == 1) {
                item = left.in().read();
//                System.out.printf("%s, id %d got %d%n", type, id, item);
                if (item != -1) {
                    accessQueue.addLast(item);
                }
            }
        }
    }

    private void handleClient() {
        if (!req.in().pending())
            return;

        int item = req.in().read();

        if (item == -1) {
            if (accessQueue.isEmpty())
                return;
            int access = accessQueue.removeFirst();
            in.out().write(access);
        }
        else {
            accessQueue.addFirst(item);
        }
    }

    public void addAccess(int access) {
        accessQueue.addLast(access);
    }

}