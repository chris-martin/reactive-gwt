package org.codeswarm.reactivegwt;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import java.util.List;

public class Example2 extends Test {

  List<TextBox> textBoxes;
  Label label;

  public void test() throws Exception {
    textBoxes = Lists.newArrayList();
    for (int i = 0; i < 3; ++i) textBoxes.add(new TextBox());
    label = new Label();
    Signals.merge(Signals.valueOf(textBoxes), Functions.join(", "))
      .addValueChangeHandler(ValueChangeHandlers.setText(label));
    assertEquals("", label.getText());
    textBoxes.get(0).setValue("abc", true);
    assertEquals("abc", label.getText());
    textBoxes.get(1).setValue("def", true);
    assertEquals("abc,def", label.getText());
  }

}
