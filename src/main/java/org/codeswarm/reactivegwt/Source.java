package org.codeswarm.reactivegwt;

public interface Source<T> {

  T getValue();

  interface Composition<T> {
    Source<T> compose(Iterable<? extends Source<T>> sources);
  }

}
