package org.codeswarm.reactivegwt;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;

public interface Signal<T> extends Source<T>, HasValueChangeHandlers<T> {

  /**
   * Releases all resources (typically, event handler registrations)
   * held by this signal. Call this method only if you are sure that
   * no one else is using the signal.
   */
  void destroy();

}
