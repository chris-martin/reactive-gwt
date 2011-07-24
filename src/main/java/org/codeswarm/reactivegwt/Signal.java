package org.codeswarm.reactivegwt;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;

public interface Signal<T> extends Source<T>, HasValueChangeHandlers<T> { }
