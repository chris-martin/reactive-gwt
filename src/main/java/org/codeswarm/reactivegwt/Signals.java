package org.codeswarm.reactivegwt;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

public final class Signals {

  private Signals() { }

  public static <T> Signal<T> create(Source<T> source,
      Iterable<? extends HasValueChangeHandlers> xs) {

    SignalImpl<T> signal = new SignalImpl<T>(source);
    for (HasValueChangeHandlers<T> x : xs) {
      signal.handlerRegistrations.add(x.addValueChangeHandler(signal.valueChangeHandler));
    }
    return signal;
  }

  public static <T> Signal<T> valueOf(HasValue<T> hasValue) {

    return create(Sources.valueOf(hasValue), ImmutableList.of(hasValue));
  }

  public static <F, T> Signal<T> transform(
      Signal<F> from, Function<? super F, ? extends T> transformation) {

    return create(Sources.transform(from, transformation), ImmutableList.of(from));
  }

  public static <F> Signal<Boolean> transform(
      Signal<F> from, final Predicate<? super F> predicate) {

    return transform(from, new Function<F, Boolean>() {
      @Override
      public Boolean apply(@Nullable F f) {
        return predicate.apply(f);
      }
    });
  }

  @SuppressWarnings("unchecked")
  public static <T> Signal<T> compose(
      Iterable<Signal<T>> signals,
      final Function<Iterable<? extends T>, T> composition) {

    final Iterable<T> values = Iterables.transform(signals, (Function) getSourceValue);
    Source<T> source = new Source<T>() {
      @Override
      public T getValue() {
        return composition.apply(values);
      }
    };
    return create(source, signals);
  }

  private static final Function<? extends Source<?>, Object> getSourceValue =
      new Function<Source<?>, Object>() {
    @Override
    public Object apply(@Nullable Source<?> input) {
      return input == null ? null : input.getValue();
    }
  };

  public static Signal<Boolean> not(Signal<Boolean> signal) {
    return transform(signal, not);
  }

  private static final Function<Boolean, Boolean> not = new Function<Boolean, Boolean>() {
    @Override
    public Boolean apply(@Nullable Boolean input) {
      return input == null ? null : !input;
    }
  };

  public static Signal<Boolean> or(Iterable<Signal<Boolean>> signals) {
    return compose(signals, Sources.or());
  }

  public static Signal<Boolean> and(Iterable<Signal<Boolean>> signals) {
    return compose(signals, Sources.and());
  }

  public static Signal<Boolean> emptyString(Signal<String> stringSignal) {
    return transform(stringSignal, emptyString);
  }

  private static final Predicate<String> emptyString = new Predicate<String>() {
    @Override
    public boolean apply(@Nullable String value) {
      return value == null || value.length() == 0;
    }
  };

  public static Signal<Boolean> emptyCollection(
      Signal<? extends Collection> collectionSignal) {

    return transform(collectionSignal, emptyCollection);
  }

  private static final Predicate<Collection> emptyCollection = new Predicate<Collection>() {
    @Override
    public boolean apply(@Nullable Collection value) {
      return value == null || value.size() != 0;
    }
  };

  private static class SignalImpl<T> implements Signal<T> {

    final HandlerManager handlerManager = new HandlerManager(this);
    final Source<T> source;
    List<HandlerRegistration> handlerRegistrations = Lists.newArrayList();

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

    @Override
    public void destroy() {
      if (handlerRegistrations == null) {
        throw new IllegalStateException("Signal has already been destroyed");
      }
      for (HandlerRegistration registration : handlerRegistrations) {
        registration.removeHandler();
      }
      handlerRegistrations = null;
    }

  }

}
