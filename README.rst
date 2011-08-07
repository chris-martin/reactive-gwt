Reactive GWT
============

This library supports `reactive programming`_ in a way that integrates nicely
with Google Web Toolkit.

AbstractHasValue<T>
-------------------

The standard GWT way to implement HasValue_ is to extend Widget_
(usually Composite_), which provides ``fireEvent(GwtEvent<?>):void``
(required by HasHandlers_, a supertype of HasValue).
If you want to design a widget that has more than one piece of observable state,
you're out of luck, because your widget can only implement HasValue once.

::

 // Sorry, no can do.
 class StatefulWidget extends Composite
     implements HasValue<Integer>,
                HasValue<String> {

An alternative is to let the HasValue implementations be components of your widget:

::

 class StatefulWidget extends Composite {
   HasValue<Integer> getIntegerHasValue() { ... }
   HasValue<String> getStringHasValue() { ... }

Reactive GWT provides **AbstractHasValue** to use for these HasValue implementations.
AbstractHasValue also implements Signal.

Signal<T>
---------

A **Signal** is a variable observable value. Signal<T> extends:

* Source<T> (for retrieving the signal's current value)

* HasValueChangeHandlers<T> (for observing changes to the signal's value)

This is very similar to HasValue.
The difference is that the Signal interface does *not* not have methods that mutate its state.
This allows us to derive signals from other signals.
The main purpose of Reactive GWT is to support Signal composition,
because it is an incredibly useful and intuitive concept.

Example 1: Boolean signal composition
-------------------------------------

Suppose you have a CheckBox_, a list of TextBox_ widgets, and a Label_.
The label needs to be visible only when the checkbox is checked and at least
one of the text boxes is nonempty.
This can be expressed naturally by declaring the label's visibility change as a reaction
to the composite state of the other widgets::

 // Represent the checkbox's value as a boolean signal
 Signal<Boolean> boxIsChecked = Signals.valueOf(checkBox);

 // Create a signal indicating whether each text box is nonempty, then
 // OR them to get a signal that is true when any of them are nonempty
 Signal<Boolean> anyTextBoxesAreFilled = Signals.or(Signals.transform(
   Signals.valueOf(textBoxes), Functions.not(Predicates.emptyString)));

 // Declare that the label is visible exactly when both of these signals are true
 Signal<Boolean> labelIsVisible = Signals.and(boxIsChecked, anyTextBoxesAreFilled);
 Signals.connect(labelIsVisible, Sinks.setVisible(label));

Example 2: String signal composition
------------------------------------

Example 1 demonstrated some of the commonly-used composition
operations directly supported by the Signals class (such as and/or).
You can also merge signals using arbitrary functions.
Suppose you want the label to always display the contents of the
nonempty text boxes, separated by commas::

 MergeFunction<String, String> mergeFunction = Functions.filterThenMerge(
   Functions.not(Predicates.emptyString), Functions.join(", "));
 Signal<String> labelText = Signals.merge(Signals.valueOf(textBoxes), mergeFunction);
 Signals.connect(labelText, Sinks.setText(label));

.. _`reactive programming`: http://en.wikipedia.org/wiki/Reactive_programming
.. _HasValue: http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/user/client/ui/HasValue.html
.. _Widget: http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/user/client/ui/Widget.html
.. _Composite: http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/user/client/ui/Composite.html
.. _HasHandlers: http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/event/shared/HasHandlers.html
.. _CheckBox: http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/user/client/ui/CheckBox.html
.. _TextBox: http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/user/client/ui/TextBox.html
.. _Label: http://google-web-toolkit.googlecode.com/svn/javadoc/latest/com/google/gwt/user/client/ui/Label.html
