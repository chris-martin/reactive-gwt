package org.codeswarm.reactivegwt;

import com.google.common.base.Function;
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

}
