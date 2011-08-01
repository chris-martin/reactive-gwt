package org.codeswarm.reactivegwt;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import org.testng.annotations.BeforeMethod;
import org.testng.collections.Lists;

import java.util.List;

import static org.codeswarm.reactivegwt.Signals.*;

public class Example1 {

  CheckBox checkBox;
  List<TextBox> textBoxes;
  Label label;

  @BeforeMethod
  public void setUp() throws Exception {
    checkBox = new CheckBox();
    textBoxes = Lists.newArrayList();
    for (int i = 0; i < 3; ++i) textBoxes.add(new TextBox());
    label = new Label();
    Signal<Boolean> boxIsChecked = valueOf(checkBox);
    Signal<Boolean> anyTextBoxesAreFilled =
      or(transform(valueOf(textBoxes), Functions.not(Predicates.emptyString)));
    and(boxIsChecked, anyTextBoxesAreFilled)
      .addValueChangeHandler(ValueChangeHandlers.setVisible(label));
  }

}
