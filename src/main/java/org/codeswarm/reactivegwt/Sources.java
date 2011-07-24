package org.codeswarm.reactivegwt;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gwt.user.client.TakesValue;

public final class Sources {

  private Sources() { }

  public static <T> Source<T> fromTakesValue(final TakesValue<T> takesValue) {
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

  public static Source.Composition<Boolean> or() {
    return or;
  }

  private static Source.Composition<Boolean> or = new Source.Composition<Boolean>() {

    @Override
    public Source<Boolean> compose(final Iterable<? extends Source<Boolean>> sources) {
      return new Source<Boolean>() {
        @Override
        public Boolean getValue() {
          return Iterables.any(sources, booleanPredicate);
        }
      };
    }

  };

  public static Source.Composition<Boolean> and() {
    return and;
  }

  private static Source.Composition<Boolean> and = new Source.Composition<Boolean>() {

    @Override
    public Source<Boolean> compose(final Iterable<? extends Source<Boolean>> sources) {
      return new Source<Boolean>() {
        @Override
        public Boolean getValue() {
          return Iterables.all(sources, booleanPredicate);
        }
      };
    }

  };

  private static final Predicate<Source<Boolean>> booleanPredicate =
      new Predicate<Source<Boolean>>() {
    @Override
    public boolean apply(Source<Boolean> value) {
      return value.getValue();
    }
  };

}
