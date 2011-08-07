package org.codeswarm.reactivegwt;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import java.util.List;

public class GwtTestExample1 extends TestAbstract {

  CheckBox checkBox;
  List<TextBox> textBoxes;
  Label label;

  public void test() throws Exception {

    // The Players
    checkBox = new CheckBox();
    textBoxes = Lists.newArrayList();
    for (int i = 0; i < 3; ++i) textBoxes.add(new TextBox());
    label = new Label();

    // The Set-Up
    Signal<Boolean> boxIsChecked = Signals.valueOf(checkBox);
    Signal<Boolean> anyTextBoxesAreFilled = Signals.or(Signals.transform(
      Signals.valueOf(textBoxes), Functions.not(Predicates.emptyString)));
    Signal<Boolean> labelIsVisible = Signals.and(boxIsChecked, anyTextBoxesAreFilled);
    Signals.connect(labelIsVisible, Sinks.setVisible(label));

    // The Sting
    assertFalse(label.isVisible());
    checkBox.setValue(true, true);
    assertFalse(label.isVisible());
    textBoxes.get(1).setValue("xyzzy", true);
    assertTrue(label.isVisible());
    checkBox.setValue(false, true);
    assertFalse(label.isVisible());

  }

}
