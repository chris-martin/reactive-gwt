package org.codeswarm.reactivegwt;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.UIObject;

public final class Sinks {

  private Sinks() { }

  public static <T> ValueChangeHandler<T> valueChangeHandler(final Sink<T> sink) {
    return new ValueChangeHandler<T>() {
      @Override
      public void onValueChange(ValueChangeEvent<T> event) {
        sink.setValue(event.getValue());
      }
    };
  }

  public static Sink<Boolean> setVisible(final UIObject uiObject) {
    return new Sink<Boolean>() {
      @Override
      public void setValue(Boolean value) {
        uiObject.setVisible(value);
      }
    };
  }

  public static <T> Sink<T> setValue(final TakesValue<T> takesValue) {
    return new Sink<T>() {
      @Override
      public void setValue(T value) {
        takesValue.setValue(value);
      }
    };
  }

  public static Sink<String> setText(final HasText hasText) {
    return new Sink<String>() {
      @Override
      public void setValue(String value) {
        hasText.setText(value);
      }
    };
  }

}
