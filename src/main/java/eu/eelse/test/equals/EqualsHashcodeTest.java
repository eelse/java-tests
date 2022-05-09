package eu.eelse.test.equals;

import eu.eelse.test.Tester;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EqualsHashcodeTest {

  public static void main(String[] args) {
    long iterationsPerRun = 10_000;
    int runsForAverage = 10;

    int objectCount = 10;

    String description =
        "Testing reflective equals and hashcode implementations using apache commons "
            + "EqualsBuilder and HashcodeBuilder versus java 7+ explicit implementations. "
            + "\nTested by creating a HashMap containing "
            + objectCount
            + " objects using reflective or explicit methods, then:"
            + "\n   - forcing calls to map.containsKey(object not in map) so every hashcode gets called"
            + "\n   - loop the map and execute equals on every value";

    Runnable reflectiveApache =
        () -> {
          ObjectApacheLangReflection root =
              new ObjectApacheLangReflection("root", -5, null, -5F, -5, true);
          Map<TestObject, TestObject> objectReflections =
              IntStream.range(0, objectCount)
                  .mapToObj(
                      i ->
                          new ObjectApacheLangReflection(
                              "test" + i, i, root, Float.intBitsToFloat(i), i, false))
                  .collect(Collectors.toMap(o -> o, o -> o));
          // look for an object that is not there so it will pass all keys' hashcode methods
          objectReflections.containsKey(root);
          // perform equals on all keys
          objectReflections.forEach((key, val) -> key.equals(root));
        };

    Runnable reflectiveApache3 =
        () -> {
          ObjectApacheLang3Reflection root =
              new ObjectApacheLang3Reflection("root", -5, null, -5F, -5, true);
          Map<TestObject, TestObject> objectReflections =
              IntStream.range(0, objectCount)
                  .mapToObj(
                      i ->
                          new ObjectApacheLang3Reflection(
                              "test" + i, i, root, Float.intBitsToFloat(i), i, false))
                  .collect(Collectors.toMap(o -> o, o -> o));
          // look for an object that is not there so it will pass all keys' hashcode methods
          objectReflections.containsKey(root);
          // perform equals on all keys
          objectReflections.forEach((key, val) -> key.equals(root));
        };

    Runnable reflectiveCustom =
        () -> {
          ObjectCustomReflection root = new ObjectCustomReflection("root", -5, null, -5F, -5, true);
          Map<TestObject, TestObject> objectReflections =
              IntStream.range(0, objectCount)
                  .mapToObj(
                      i ->
                          new ObjectCustomReflection(
                              "test" + i, i, root, Float.intBitsToFloat(i), i, false))
                  .collect(Collectors.toMap(o -> o, o -> o));
          // look for an object that is not there so it will pass all keys' hashcode methods
          objectReflections.containsKey(root);
          // perform equals on all keys
          objectReflections.forEach((key, val) -> key.equals(root));
        };

    Runnable explicit =
        () -> {
          ObjectJava root = new ObjectJava("root", -5, null, -5F, -5, true);
          Map<TestObject, TestObject> objectReflections =
              IntStream.range(0, objectCount)
                  .mapToObj(
                      i -> new ObjectJava("test" + i, i, root, Float.intBitsToFloat(i), i, false))
                  .collect(Collectors.toMap(o -> o, o -> o));
          // look for an object that is not there so it will pass all keys' hashcode methods
          objectReflections.containsKey(root);
          // perform equals on all keys
          objectReflections.forEach((key, val) -> key.equals(root));
        };

    new Tester(description, iterationsPerRun, runsForAverage)
        .addTest("reflective methods Apache commons lang 2", reflectiveApache)
        .addTest("reflective methods Apache commons lang 3", reflectiveApache3)
        .addTest("reflective methods custom", reflectiveCustom)
        .addTest("explicit methods", explicit)
        .runTests();
  }
}
