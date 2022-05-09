package eu.eelse.test.equals;

import java.util.Objects;

public class ObjectJava extends AbstractTestObject<ObjectJava> {

  public ObjectJava(
      String aString,
      Integer anInteger,
      ObjectJava another,
      Float aFloat,
      int aPrimaryInt,
      Boolean aBoolean) {
    super(aString, anInteger, another, aFloat, aPrimaryInt, aBoolean);
  }

  @Override
  public boolean equals(Object o) {
    //    System.out.println("equals hit ObjectJava");
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ObjectJava that = (ObjectJava) o;
    return aPrimaryInt == that.aPrimaryInt
        && Objects.equals(aString, that.aString)
        && Objects.equals(anInteger, that.anInteger)
        && Objects.equals(another, that.another)
        && Objects.equals(aFloat, that.aFloat)
        && Objects.equals(aBoolean, that.aBoolean);
  }

  @Override
  public int hashCode() {
    //    System.out.println("hashCode hit ObjectJava");
    return Objects.hash(aString, anInteger, another, aFloat, aPrimaryInt, aBoolean);
  }
}
