package org.codeswarm.reactivegwt;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public final class MergeFunctions {

  private MergeFunctions() { }

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

}
