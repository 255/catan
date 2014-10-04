package shared;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * The unit tests for the shared packages.
 * @author Wyatt
 */
public class SharedUnitTests {
    /**
     * Run the specified tests.
     * @param args don't use these
     */
    public static void main(String[] args) {
        System.out.println("Running shared unit tests.");
        Result result = JUnitCore.runClasses(
                shared.model.CatanMapTest.class,
                shared.model.GameModelFacadeTest.class,
                shared.model.ModelInitializer.class
        );
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("Done.");
    }
}