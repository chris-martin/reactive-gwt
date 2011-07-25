package org.codeswarm.reactivegwt;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.UIObject;

public final class ValueChangeHandlers {

  private ValueChangeHandlers() { }

  public static ValueChangeHandler<Boolean> setVisible(final UIObject uiObject) {
    return new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        uiObject.setVisible(event.getValue());
      }
    };
  }

  public static <T> ValueChangeHandler<T> setValue(final TakesValue<T> takesValue) {
    return new ValueChangeHandler<T>() {
      @Override
      public void onValueChange(ValueChangeEvent<T> event) {
        takesValue.setValue(event.getValue());
      }
    };
  }

  public static ValueChangeHandler<String> setText(final HasText hasText) {
    return new ValueChangeHandler<String>() {
      @Override
      public void onValueChange(ValueChangeEvent<String> event) {
        hasText.setText(event.getValue());
      }
    };
  }

}
