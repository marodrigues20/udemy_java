package com.in28minutes.unittesting.unittesting.business;

import com.in28minutes.unittesting.unittesting.data.SomeDataService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


public class SomeBusinessTest {



    @Test
    void calculateSum_basic(){
        SomeBusinessImpl  business = new SomeBusinessImpl();
        int actualResult = business.calculateSum(new int[] {1,2,3});
        int expectedResult = 6;
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void calculateSum_empty(){
        SomeBusinessImpl  business = new SomeBusinessImpl();
        int actualResult = business.calculateSum(new int[] {});
        int expectedResult = 0;
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void calculateSum_oneValue(){
        SomeBusinessImpl  business = new SomeBusinessImpl();
        int actualResult = business.calculateSum(new int[] {5});
        int expectedResult = 5;
        assertEquals(expectedResult,actualResult);
    }

}
