package eu.eelse.test.equals;

import eu.eelse.test.Tester;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EqualsHashcodeTest {

  public static void main(String[] args) {
    long iterations = 100;

    int objectCount = 10;

    String description =
        "Testing reflective equals and hashcode implementations using apache commons "
            + "EqualsBuilder and HashcodeBuilder versus java 7+ explicit implementations. "
            + "\nTested by creating a HashMap containing "
            + objectCount
            + " objects using reflective or explicit methods, then:"
            + "\n   - forcing calls to map.containsKey(object not in map) so every hashcode gets called"
            + "\n   - loop the map and execute equals on every value"
            + "\n\nReflective: org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals(this, o)"
            + "\n\nExplicit: custom checks on identicality and usage of java.util.Objects.equals(var, other.var)";

    Runnable reflective =
        () -> {
          ObjectReflection root = new ObjectReflection("root", -5, null, -5F, -5, true);
          Map<ObjectReflection, ObjectReflection> objectReflections =
              IntStream.range(0, objectCount)
                  .mapToObj(
                      i ->
                          new ObjectReflection(
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
          Map<ObjectJava, ObjectJava> objectReflections =
              IntStream.range(0, objectCount)
                  .mapToObj(
                      i -> new ObjectJava("test" + i, i, root, Float.intBitsToFloat(i), i, false))
                  .collect(Collectors.toMap(o -> o, o -> o));
          // look for an object that is not there so it will pass all keys' hashcode methods
          objectReflections.containsKey(root);
          // perform equals on all keys
          objectReflections.forEach((key, val) -> key.equals(root));
        };

    new Tester(description, iterations)
        .withTest("reflective methods", reflective)
        .withTest("explicit methods", explicit)
        .runTests();
  }
}
