package eu.eelse.test.equals;

public class ObjectApacheLang3Reflection extends AbstractTestObject<ObjectApacheLang3Reflection> {

  public ObjectApacheLang3Reflection(
      String aString,
      Integer anInteger,
      ObjectApacheLang3Reflection another,
      Float aFloat,
      int aPrimaryInt,
      Boolean aBoolean) {
    super(aString, anInteger, another, aFloat, aPrimaryInt, aBoolean);
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
