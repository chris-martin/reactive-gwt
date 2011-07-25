package org.codeswarm.reactivegwt;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public abstract class AbstractHasValue<T> implements HasValue<T>, Signal<T>, Sink<T> {

  private final HandlerManager handlerManager = new HandlerManager(this);

  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
    return handlerManager.addHandler(ValueChangeEvent.getType(), handler);
  }

  @Override
  public void fireEvent(GwtEvent<?> event) {
    handlerManager.fireEvent(event);
  }

  @Override
  public void setValue(T value, boolean fireEvents) {
    setValue(value);
    if (fireEvents) ValueChangeEvent.fire(this, value);
  }

  public void fireValueChangeEvent() {
    ValueChangeEvent.fire(this, getValue());
  }

  @Override
  public void destroy() { }

  public static <T> AbstractHasValue<T> create(Source<T> source, Sink<T> sink) {
    return new Impl<T>(source, sink);
  }

  private static class Impl<T> extends AbstractHasValue<T> {

    final Source<T> source;
    final Sink<T> sink;

    Impl(Source<T> source, Sink<T> sink) {
      this.source = source;
      this.sink = sink;
    }

    @Override
    public T getValue() {
      return source.getValue();
    }

    @Override
    public void setValue(T value) {
      sink.setValue(value);
    }

  }

}
