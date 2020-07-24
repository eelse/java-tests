package eu.eelse.test.equals;

import java.util.Objects;

public class ObjectJava {

  private final String aString;
  private final Integer anInteger;
  private final ObjectJava another;
  private final Float aFloat;
  private final int aPrimaryInt;
  private final Boolean aBoolean;

  public ObjectJava(
      String aString,
      Integer anInteger,
      ObjectJava another,
      Float aFloat,
      int aPrimaryInt,
      Boolean aBoolean) {
    this.aString = aString;
    this.anInteger = anInteger;
    this.another = another;
    this.aFloat = aFloat;
    this.aPrimaryInt = aPrimaryInt;
    this.aBoolean = aBoolean;
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
