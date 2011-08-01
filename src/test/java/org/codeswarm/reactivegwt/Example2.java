package org.codeswarm.reactivegwt;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
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
    Signals.merge(
      Signals.valueOf(textBoxes),
      new MergeFunction<String, String>() {
        public String apply(Iterable<? extends String> values) {
          return Joiner.on(", ").join(removeEmptyStrings(values));
        }
        Iterable<? extends String> removeEmptyStrings(Iterable<? extends String> values) {
          return Iterables.filter(values, new Predicate<String>() {
            public boolean apply(String value) {
              return value.length() != 0;
            }
          });
        }
      }
    ).addValueChangeHandler(ValueChangeHandlers.setText(label));
  }

}
