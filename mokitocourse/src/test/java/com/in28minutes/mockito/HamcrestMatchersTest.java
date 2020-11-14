package com.in28minutes.mockito;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.lessThan;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

// we have to import through maven hamcrest-library to use the matchers like
// greaterThan, lessThan.
// The library hamcrest-core we don't have the matchers.
public class HamcrestMatchersTest {

  @Test
  public void test(){
    List<Integer> scores = Arrays.asList(99,100,101,105);
    //scores has 4 items
    assertThat(scores, hasSize(4));
    assertThat(scores,hasItems(99,100));

    //every item : > 90
    assertThat(scores, everyItem(greaterThan(90)));
    assertThat(scores, everyItem(lessThan(90)));

    //String
    assertThat("", isEmptyString()); // some program receive "" we check isEmptyString();
    assertThat(null,isEmptyOrNullString());

    //Arrays
    Integer[] marks = {1,2,3};
    assertThat(marks,arrayWithSize(3));
    assertThat(marks,arrayContaining(1,2,3));
    assertThat(marks,arrayContainingInAnyOrder(2,1,3));






  }

}
