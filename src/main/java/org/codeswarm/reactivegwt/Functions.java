package org.codeswarm.reactivegwt;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import javax.annotation.Nullable;

public final class Functions {

  private Functions() { }

  public static final Function<Boolean, Boolean> not =
      new Function<Boolean, Boolean>() {

    @Override
    public Boolean apply(@Nullable Boolean x) {
      return x == null ? null : !x;
    }
  };

  public static <F, T> MergeFunction<F, T> filterThenMerge(
      final Predicate<F> predicate, final MergeFunction<F, T> merge) {

    return new MergeFunction<F, T>() {
      @Override
      public T apply(Iterable<? extends F> iterable) {
        return merge.apply(Iterables.filter(iterable, predicate));
      }
    };
  }

  public static <F, T> MergeFunction<F, T> filterThenMerge(
      final Function<F, Boolean> function, final MergeFunction<F, T> merge) {
    return filterThenMerge(Predicates.forFunction(function), merge);
  }

  public static <F> Function<F, Boolean> not(
      final Function<F, Boolean> function) {

    return com.google.common.base.Functions.compose(not, function);
  }

  public static <F> Function<F, Boolean> not(Predicate<F> predicate) {

    return not(com.google.common.base.Functions.forPredicate(predicate));
  }

  public static MergeFunction<Boolean, Boolean> or() {
    return or;
  }

  private static MergeFunction<Boolean, Boolean> or =
      new MergeFunction<Boolean, Boolean>() {
    @Override
    public Boolean apply(final Iterable<? extends Boolean> values) {
      return Iterables.any(values, identityPredicate);
    }
  };

  public static MergeFunction<Boolean, Boolean> and() {
    return and;
  }

  private static MergeFunction<Boolean, Boolean> and =
      new MergeFunction<Boolean, Boolean>() {
    @Override
    public Boolean apply(Iterable<? extends Boolean> values) {
      return Iterables.all(values, identityPredicate);
    }
  };

  private static final Predicate<Boolean> identityPredicate =
      new Predicate<Boolean>() {
    @Override
    public boolean apply(Boolean value) {
      return value;
    }
  };

  public static MergeFunction<String, String> join(Joiner joiner) {
    return new JoinerFunction(joiner);
  }

  public static MergeFunction<String, String> join(String separator) {
    return new JoinerFunction(Joiner.on(separator));
  }

  public static MergeFunction<String, String> join(char separator) {
    return new JoinerFunction(Joiner.on(separator));
  }

  private static final class JoinerFunction
      implements MergeFunction<String, String> {

    private final Joiner joiner;

    public JoinerFunction(Joiner joiner) {
      this.joiner = joiner;
    }

    @Override
    public String apply(Iterable<? extends String> input) {
      return joiner.join(input);
    }

  }

}
