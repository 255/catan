package client;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * The unit tests for the shared packages.
 * @author Wyatt
 */
public class ClientUnitTests {
    /**
     * Run the specified tests.
     * @param args don't use these
     */
    public static void main(String[] args) {
        System.out.println("Running client unit tests.");
        Result result = JUnitCore.runClasses(
                //client.poller.ServerPollerTest.class, //FIXME: this test hangs
                client.network.ServerProxyTest.class
        );
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("Done.");
    }
}
