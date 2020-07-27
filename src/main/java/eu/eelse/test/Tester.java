package eu.eelse.test;

import java.io.PrintStream;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Tester {

  private static final int DEFAULT_NUMBER_OF_RUNS = 5;

  private final String description;
  private final long iterations;
  private final long runs;

  private final Map<String, Runnable> tests;

  public Tester(String description, long iterations) {
    this(description, iterations, DEFAULT_NUMBER_OF_RUNS);
  }

  public Tester(String description, long iterations, int runs) {
    super();
    this.description = description;
    this.iterations = iterations;
    this.runs = runs;
    this.tests = new HashMap<>();
  }

  public Tester addTest(String name, Runnable test) {
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
    out.printf(
        "Running %d tests with %d tries and %d iterations each\n", tests.size(), runs, iterations);

    // to ensure equal playground for all tests we run each in a separate dedicated thread
    ExecutorService executor = Executors.newFixedThreadPool(Math.max(tests.size(), 10));

    Map<String, Future<Long>> collect =
        tests.entrySet().stream()
            .map(
                entry ->
                    new SimpleEntry<>(
                        entry.getKey(),
                        testFull(entry.getKey(), entry.getValue(), iterations, runs, out)))
            .collect(Collectors.toMap(Entry::getKey, entry -> executor.submit(entry.getValue())));
    executor.shutdown();
    try {
      if (!executor.awaitTermination(120, TimeUnit.SECONDS)) {
        out.println("Timout encountered, shutting down all threads");
        executor.shutdownNow();
      }
    } catch (InterruptedException e) {
      executor.shutdownNow();
    }
    // report
    out.println();
    out.printf("Results for running the tests with %d iterations %d times:\n\n", iterations, runs);
    collect.entrySet().stream()
        .map(
            entry -> {
              try {
                return new SimpleEntry<>(entry.getKey(), entry.getValue().get());
              } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
              }
            })
        .sorted(Map.Entry.comparingByValue())
        .forEachOrdered(
            entry -> out.printf("Test '%s' average: %d ms\n", entry.getKey(), entry.getValue()));
  }

  public void runTests() {
    this.runTests(System.out);
  }

  private static Callable<Long> testFull(
      String name, Runnable test, long iterations, long runs, PrintStream outputStream) {
    outputStream.printf("Doing %d runs of test '%s\n'", runs, name);
    return () -> {
      long sum =
          LongStream.range(0, runs)
              .mapToObj(i -> testSingle(name, test, iterations, outputStream))
              .map(
                  c -> {
                    try {
                      return c.call();
                    } catch (Exception e) {
                      throw new RuntimeException(e);
                    }
                  })
              .mapToLong(value -> value)
              .sum();
      return Math.floorDiv(sum, runs);
    };
  }

  private static Callable<Long> testSingle(
      String name, Runnable test, long iterations, PrintStream outputStream) {
    return () -> {
      outputStream.printf("Test '%s' started for %d iterations\n", name, iterations);
      long duration;
      long start = System.nanoTime();
      try {
        for (long i = 0; i < iterations; i++) {
          test.run();
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      } finally {
        long end = System.nanoTime();
        duration = toMillis(end - start);
        outputStream.printf("Test '%s' DONE in %d ms\n", name, duration);
      }
      return duration;
    };
  }

  private static long toMillis(long nanos) {
    return TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS);
  }
}
