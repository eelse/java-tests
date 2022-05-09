package eu.eelse.test.equals;

public abstract class AbstractTestObject <T extends TestObject> implements TestObject {

  protected final String aString;
  protected final Integer anInteger;
  protected final T another;
  protected final Float aFloat;
  protected final int aPrimaryInt;
  protected final Boolean aBoolean;

  protected AbstractTestObject(String aString, Integer anInteger, T another, Float aFloat,
      int aPrimaryInt, Boolean aBoolean) {
    this.aString = aString;
    this.anInteger = anInteger;
    this.another = another;
    this.aFloat = aFloat;
    this.aPrimaryInt = aPrimaryInt;
    this.aBoolean = aBoolean;
  }
}
