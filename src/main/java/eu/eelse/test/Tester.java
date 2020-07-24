package eu.eelse.test;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Tester {

  private final String description;
  private final long iterations;

  private final Map<String, Runnable> tests;

  public Tester(String description, long iterations) {
    super();
    this.description = description;
    this.iterations = iterations;
    this.tests = new HashMap<>();
  }

  public Tester withTest(String name, Runnable test) {
    tests.put(name, test);
    return this;
  }

  public void runTests(PrintStream outputStream) {
    final PrintStream out = outputStream != null ? outputStream : System.out;
    out.println(description);
    out.println();
    out.println("Environment:");

    Stream.of(
            "java.version", "java.vendor", "java.class.version", "os.name", "os.arch", "os.version")
        .forEach(prop -> out.printf("   %s=%s\n", prop, System.getProperty(prop)));

    out.println();
    out.println("Running " + tests.size() + " tests with " + iterations + " iterations each");

    // to ensure equal playground for all tests we run each in a separate dedicated thread
    ExecutorService executor = Executors.newFixedThreadPool(Math.max(tests.size(), 10));

    tests.forEach((name, test) -> executor.submit(getTestRunner(name, test, iterations, out)));
    executor.shutdown();
    try {
      if (!executor.awaitTermination(120, TimeUnit.SECONDS)) {
        out.println("Timout encountered, shutting down all threads");
        executor.shutdownNow();
      }
    } catch (InterruptedException e) {
      executor.shutdownNow();
    }
  }

  public void runTests() {
    this.runTests(System.out);
  }

  private static Runnable getTestRunner(
      String name, Runnable test, long iterations, PrintStream outputStream) {
    return () -> {
      outputStream.println("Test '" + name + "' started");
      long start = System.nanoTime();
      try {
        for (long i = 0; i < iterations; i++) {
          test.run();
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      } finally {
        long end = System.nanoTime();
        outputStream.println(
            "Test '"
                + name
                + "' took: "
                + TimeUnit.MILLISECONDS.convert(end - start, TimeUnit.NANOSECONDS)
                + "ms");
      }
    };
  }
}
