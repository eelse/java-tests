package eu.eelse.test.equals;

import java.lang.reflect.Field;

public class ObjectCustomReflection extends AbstractTestObject<ObjectCustomReflection> {

  public ObjectCustomReflection(
      String aString,
      Integer anInteger,
      ObjectCustomReflection another,
      Float aFloat,
      int aPrimaryInt,
      Boolean aBoolean) {
    super(aString, anInteger, another, aFloat, aPrimaryInt, aBoolean);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ObjectCustomReflection)) {
      return false;
    } else if (o == this) {
      return true;
    }
    try {
      for (Field declaredField : getClass().getSuperclass().getDeclaredFields()) {
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
