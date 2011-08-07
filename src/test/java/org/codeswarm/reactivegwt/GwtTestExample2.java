package org.codeswarm.reactivegwt;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import java.util.List;

public class GwtTestExample2 extends TestAbstract {

  List<TextBox> textBoxes;
  Label label;

  public void test() throws Exception {

    // The Players
    textBoxes = Lists.newArrayList();
    for (int i = 0; i < 3; ++i) textBoxes.add(new TextBox());
    label = new Label();

    // The Set-Up
    MergeFunction<String, String> mergeFunction = Functions.filterThenMerge(
      Functions.not(Predicates.emptyString), Functions.join(", "));
    Signal<String> labelText = Signals.merge(Signals.valueOf(textBoxes), mergeFunction);
    Signals.connect(labelText, Sinks.setText(label));

    // The Sting
    assertEquals("", label.getText());
    textBoxes.get(0).setValue("abc", true);
    assertEquals("abc", label.getText());
    textBoxes.get(1).setValue("def", true);
    assertEquals("abc, def", label.getText());

  }

}
