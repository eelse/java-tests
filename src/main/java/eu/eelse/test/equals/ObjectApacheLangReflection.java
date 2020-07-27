package eu.eelse.test.equals;

public class ObjectApacheLangReflection {

  private final String aString;
  private final Integer anInteger;
  private final ObjectApacheLangReflection another;
  private final Float aFloat;
  private final int aPrimaryInt;
  private final Boolean aBoolean;

  public ObjectApacheLangReflection(
      String aString,
      Integer anInteger,
      ObjectApacheLangReflection another,
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
    return org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode(this);
  }
}
