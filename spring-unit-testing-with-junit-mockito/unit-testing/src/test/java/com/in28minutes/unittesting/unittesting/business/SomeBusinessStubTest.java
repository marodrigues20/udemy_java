package com.in28minutes.unittesting.unittesting.business;

import com.in28minutes.unittesting.unittesting.data.SomeDataService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


// Complicated to maintain
// If we use this kind of approach in the Unit tests we gonna have 100 os stub classes.

class SomeDataServiceStub implements SomeDataService {

    public int[] retrieveAllData() {
        return new int[] {1,2,3};
    }
}

class SomeDataServiceEmptyStub implements SomeDataService {

    public int[] retrieveAllData() {
        return new int[] {};
    }
}

class SomeDataServiceOneValueStub implements SomeDataService {

    public int[] retrieveAllData() {
        return new int[] {5};
    }
}

public class SomeBusinessStubTest {

    @Test
    void calculateSumUsingDataService_basic(){
        SomeBusinessImpl  business = new SomeBusinessImpl();
        business.setSomeDataService(new SomeDataServiceStub());
        int actualResult = business.calculateSumUsingDataService();
        int expectedResult = 6;
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void calculateSumUsingDataService_empty(){
        SomeBusinessImpl  business = new SomeBusinessImpl();
        business.setSomeDataService(new SomeDataServiceEmptyStub());
        int actualResult = business.calculateSumUsingDataService();
        int expectedResult = 0;
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void calculateSumUsingDataService_oneValue(){
        SomeBusinessImpl  business = new SomeBusinessImpl();
        business.setSomeDataService(new SomeDataServiceOneValueStub());
        int actualResult = business.calculateSumUsingDataService();
        int expectedResult = 5;
        assertEquals(expectedResult,actualResult);
    }
}
