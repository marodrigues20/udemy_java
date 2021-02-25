package com.in28minutes.unittesting.unittesting.spike;

import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;



public class AssertJTest {

    @Test
    public void learning(){

        List<Integer> numbers = Arrays.asList(12, 15, 45);

        //assertThat( numbers,hasSize(3));
        Assertions.assertThat(numbers).hasSize(3)
                  .contains(12,15)
                  .allMatch(x -> x > 10)
                  .allMatch(x -> x < 100)
                  .noneMatch(x -> x < 0);

        Assertions.assertThat("").isEmpty();
        Assertions.assertThat("ABCDE")
                  .contains("BCD")
                  .startsWith("ABC")
                  .endsWith("CDE");


        //assertThat(numbers,hasItems(12,45));
        //assertThat(numbers,everyItem(greaterThan(10)));
        //assertThat(numbers,everyItem(lessThan(100)));

        /*assertThat("", isEmptyString());
        assertThat("ABCDE", containsString("ABC"));
        assertThat("ABCDE", startsWith("ABC"));
        assertThat("ABCDE", endsWith("CDE"));*/


    }
}
