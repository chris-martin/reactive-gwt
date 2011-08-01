package org.codeswarm.reactivegwt;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import org.testng.annotations.BeforeMethod;
import org.testng.collections.Lists;

import java.util.List;

public class Example2 {

  List<TextBox> textBoxes;
  Label label;

  @BeforeMethod
  public void setUp() throws Exception {
    textBoxes = Lists.newArrayList();
    for (int i = 0; i < 3; ++i) textBoxes.add(new TextBox());
    label = new Label();
    Signals.merge(Signals.valueOf(textBoxes), Functions.join(", "))
      .addValueChangeHandler(ValueChangeHandlers.setText(label));
  }

}
