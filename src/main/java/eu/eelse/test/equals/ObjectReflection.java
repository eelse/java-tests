package eu.eelse.test.equals;

public class ObjectReflection {

  private final String aString;
  private final Integer anInteger;
  private final ObjectReflection another;
  private final Float aFloat;
  private final int aPrimaryInt;
  private final Boolean aBoolean;

  public ObjectReflection(
      String aString,
      Integer anInteger,
      ObjectReflection another,
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
    return org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode(this);
  }
}
