package eu.eelse.test.equals;

public class ObjectApacheLangReflection extends AbstractTestObject<ObjectApacheLangReflection> {

  public ObjectApacheLangReflection(
      String aString,
      Integer anInteger,
      ObjectApacheLangReflection another,
      Float aFloat,
      int aPrimaryInt,
      Boolean aBoolean) {
    super(aString, anInteger, another, aFloat, aPrimaryInt, aBoolean);
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
