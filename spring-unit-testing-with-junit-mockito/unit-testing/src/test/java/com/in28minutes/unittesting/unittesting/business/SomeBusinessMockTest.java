package com.in28minutes.unittesting.unittesting.business;

import com.in28minutes.unittesting.unittesting.data.SomeDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SomeBusinessMockTest {

    @InjectMocks
    SomeBusinessImpl  business;
    @Mock
    SomeDataService someDataService;

    @Test
    void calculateSumUsingDataService_basic(){
        when(someDataService.retrieveAllData()).thenReturn(new int[] {1,2,3} );
        assertEquals(6,business.calculateSumUsingDataService());
    }

    @Test
    void calculateSumUsingDataService_empty(){
        when(someDataService.retrieveAllData()).thenReturn(new int[] {} );
        assertEquals(0,business.calculateSumUsingDataService());
    }

    @Test
    void calculateSumUsingDataService_oneValue(){
        when(someDataService.retrieveAllData()).thenReturn(new int[] {5} );
        assertEquals(5,business.calculateSumUsingDataService());
    }
}
