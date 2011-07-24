package org.codeswarm.reactivegwt;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

import javax.annotation.Nullable;
import java.util.Collection;

public final class Signals {

  private Signals() { }

  public static <T> Signal<T> create(Source<T> source,
      Iterable<? extends HasValueChangeHandlers> xs) {

    SignalImpl<T> signal = new SignalImpl<T>(source);
    for (HasValueChangeHandlers<T> x : xs) {
      x.addValueChangeHandler(signal.valueChangeHandler);
    }
    return signal;
  }

  public static <T> Signal<T> hasValueSignal(HasValue<T> hasValue) {

    return create(Sources.fromTakesValue(hasValue), ImmutableList.of(hasValue));
  }

  public static <F, T> Signal<T> transform(
      Signal<F> from,
      Function<? super F, ? extends T> transformation) {

    return create(Sources.transform(from, transformation), ImmutableList.of(from));
  }

  public static <T> Signal<T> compose(Source.Composition<T> composer,
                                      Iterable<Signal<T>> signals) {

    return create(composer.compose(signals), signals);
  }

  public static Signal<Boolean> or(Iterable<Signal<Boolean>> signals) {

    return compose(Sources.or(), signals);
  }

  public static Signal<Boolean> and(Iterable<Signal<Boolean>> signals) {

    return compose(Sources.and(), signals);
  }

  public static Signal<Boolean> nonEmptyString(HasValue<String> hasStringValue) {

    return transform(hasValueSignal(hasStringValue), nonEmptyString);
  }

  public static Signal<Boolean> nonEmptyCollection(
      HasValue<? extends Collection> hasCollectionValue) {

    return transform(hasValueSignal(hasCollectionValue), nonEmptyCollection);
  }

  private static final Function<String, Boolean> nonEmptyString = new Function<String, Boolean>() {
    @Override
    public Boolean apply(@Nullable String value) {
      return value != null && value.length() != 0;
    }
  };

  private static final Function<Collection, Boolean> nonEmptyCollection = new Function<Collection, Boolean>() {
    @Override
    public Boolean apply(@Nullable Collection value) {
      return value != null && value.size() != 0;
    }
  };

  private static class SignalImpl<T> implements Signal<T> {

    final HandlerManager handlerManager = new HandlerManager(this);

    final Source<T> source;

    final ValueChangeHandler<T> valueChangeHandler = new ValueChangeHandler<T>() {
      @Override
      public void onValueChange(ValueChangeEvent event) {
        ValueChangeEvent.fire(SignalImpl.this, getValue());
      }
    };

    SignalImpl(Source<T> source) {
      this.source = source;
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

  }

}
