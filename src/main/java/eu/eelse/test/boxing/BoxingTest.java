package eu.eelse.test.boxing;

import eu.eelse.test.Tester;

public class BoxingTest {
  public static void main(String[] args) {
    long iterations = 10_000;

    Runnable automatic =
        () -> {
          {
            long sum = 0L;
            Long i = 1L;
            sum += i;
          }
          {
            int sum = 0;
            Integer i = 1;
            sum += i;
          }
          {
            double sum = 0D;
            Double i = 1D;
            sum += i;
          }
          {
            char sum = '0';
            Character i = '1';
            sum += i;
          }
          {
            boolean test = false;
            Boolean i = true;
            test |= i;
          }
        };

    Runnable explicit =
        () -> {
          {
            long sum = 0L;
            Long i = Long.valueOf(1L);
            sum += i.longValue();
          }
          {
            int sum = 0;
            Integer i = Integer.valueOf(1);
            sum += i.intValue();
          }
          {
            double sum = 0D;
            Double i = Double.valueOf(1D);
            sum += i.doubleValue();
          }
          {
            char sum = '0';
            Character i = Character.valueOf('1');
            sum += i.charValue();
          }
          {
            boolean test = false;
            Boolean i = Boolean.valueOf(true);
            test |= i.booleanValue();
          }
        };

    new Tester(
            "Test autoboxing (eg. Long l = 1L;) versus explicit value assignment (eg. Long l = Long.valueOf(1L);)",
            iterations)
        .addTest("autoboxing", automatic)
        .addTest("explicit boxing", explicit)
        .runTests();
  }
}
