package org.codeswarm.reactivegwt;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public abstract class HasValueAdapter<T> implements HasValue<T> {

  protected abstract HasValue<T> getHasValue();

  @Override
  public T getValue() {
    return getHasValue().getValue();
  }

  @Override
  public void setValue(T value) {
    getHasValue().setValue(value);
  }

  @Override
  public void setValue(T value, boolean fireEvents) {
    getHasValue().setValue(value, fireEvents);
  }

  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
    return getHasValue().addValueChangeHandler(handler);
  }

  @Override
  public void fireEvent(GwtEvent<?> event) {
    getHasValue().fireEvent(event);
  }

}
