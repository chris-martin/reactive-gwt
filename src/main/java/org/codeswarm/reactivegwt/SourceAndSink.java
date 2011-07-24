package org.codeswarm.reactivegwt;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public class SourceAndSink<T> implements HasValue<T> {

  HandlerManager handlerManager = new HandlerManager(this);

  Source<T> source;
  Sink<T> sink;

  public static <T> SourceAndSink<T> create(Source<T> source, Sink<T> sink) {
    return new SourceAndSink<T>(source, sink);
  }

  public SourceAndSink(Source<T> source, Sink<T> sink) {
    this.source = source;
    this.sink = sink;
  }

  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
    return handlerManager.addHandler(ValueChangeEvent.getType(), handler);
  }

  @Override
  public void fireEvent(GwtEvent<?> event) {
    handlerManager.fireEvent(event);
  }

  @Override
  public T getValue() {
    return source.getValue();
  }

  @Override
  public void setValue(T value) {
    setValue(value, false);
  }

  @Override
  public void setValue(T value, boolean fireEvents) {
    sink.setValue(value);
    if (fireEvents) {
      ValueChangeEvent.fire(this, getValue());
    }
  }

}
