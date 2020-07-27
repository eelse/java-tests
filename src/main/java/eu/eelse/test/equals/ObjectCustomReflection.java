package eu.eelse.test.equals;

import java.lang.reflect.Field;

public class ObjectCustomReflection {

  private final String aString;
  private final Integer anInteger;
  private final ObjectCustomReflection another;
  private final Float aFloat;
  private final int aPrimaryInt;
  private final Boolean aBoolean;

  public ObjectCustomReflection(
      String aString,
      Integer anInteger,
      ObjectCustomReflection another,
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
    if (!(o instanceof ObjectCustomReflection)) {
      return false;
    } else if (o == this) {
      return true;
    }
    try {
      for (Field declaredField : getClass().getDeclaredFields()) {
        declaredField.setAccessible(true);
        Object a = declaredField.get(this);
        Object b = declaredField.get(o);
        if (a == null && b != null) {
          return false;
        } else if (a != null && !a.equals(b)) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    try {
      for (Field declaredField : getClass().getDeclaredFields()) {
        declaredField.setAccessible(true);
        Object a = declaredField.get(this);
        result = prime * result + ((a == null) ? 0 : a.hashCode());
      }
      return result;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
