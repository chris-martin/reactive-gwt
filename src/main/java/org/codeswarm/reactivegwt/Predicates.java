package org.codeswarm.reactivegwt;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import javax.annotation.Nullable;
import java.util.Collection;

public final class Predicates {

  private Predicates() { }

  public static final Predicate<String> emptyString =
      new Predicate<String>() {
    @Override
    public boolean apply(@Nullable String value) {
      return value == null || value.length() == 0;
    }
  };

  public static final Predicate<Collection> emptyCollection =
      new Predicate<Collection>() {
    @Override
    public boolean apply(@Nullable Collection value) {
      return value == null || value.size() != 0;
    }
  };

  public static <T> Predicate<T> forFunction(
      final Function<T, Boolean> function) {

    return new Predicate<T>() {
      @Override
      public boolean apply(@Nullable T t) {
        return function.apply(t);
      }
    };
  }

}
