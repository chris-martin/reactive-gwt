package org.codeswarm.reactivegwt;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gwt.user.client.TakesValue;

public final class Sources {

  private Sources() { }

  public static <T> Source<T> valueOf(final TakesValue<T> takesValue) {
    return new Source<T>() {
      @Override
      public T getValue() {
        return takesValue.getValue();
      }
    };
  }

  public static <F, T> Source<T> transform(final Source<F> source,
      final Function<? super F, ? extends T> transformation) {

    return new Source<T>() {
      @Override
      public T getValue() {
        return transformation.apply(source.getValue());
      }
    };
  }

  public static Function<Iterable<? extends Boolean>, Boolean> or() {
    return or;
  }

  private static Function<Iterable<? extends Boolean>, Boolean> or =
      new Function<Iterable<? extends Boolean>, Boolean>() {
    @Override
    public Boolean apply(final Iterable<? extends Boolean> values) {
      return Iterables.any(values, identityPredicate);
    }
  };

  public static Function<Iterable<? extends Boolean>, Boolean> and() {
    return and;
  }

  private static Function<Iterable<? extends Boolean>, Boolean> and =
      new Function<Iterable<? extends Boolean>, Boolean>() {
    @Override
    public Boolean apply(Iterable<? extends Boolean> values) {
      return Iterables.all(values, identityPredicate);
    }
  };

  private static final Predicate<Boolean> identityPredicate = new Predicate<Boolean>() {
    @Override
    public boolean apply(Boolean value) {
      return value;
    }
  };

}
