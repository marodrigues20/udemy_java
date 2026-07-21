package uk.co.heap.min_heap;

import java.util.List;

public class Main {

    static void main() {
        insertOneItem_Successefully(5);
        insertTwoItems_Successefully(5, 10);
        insertThreeItems_Successefully(5, 10, 2);
        insertManyElements_Successfully(5, 10, 2, 100, 50, 70, 30);
        removeOneItem_Successfully(100, 50, 70, 5, 10, 2, 30);
        removeTwoItems_Successfully(100, 50, 70, 5, 10, 2, 30);
    }

    private static void removeTwoItems_Successfully(int... itens) {
        MinHeapSolution minHeapSolution = new MinHeapSolution();
        List<Integer> expectedResult = List.of(10, 50, 30, 100, 70);
        for (int item : itens) {
            minHeapSolution.insert(item);
        }

        minHeapSolution.remove();
        minHeapSolution.remove();
        myOwnAssertEquals(expectedResult, minHeapSolution.getHeap());
    }

    private static void removeOneItem_Successfully(int... itens) {
        MinHeapSolution minHeapSolution = new MinHeapSolution();
        List<Integer> expectedResult = List.of(5, 10, 30, 100, 50, 70);
        for (int item : itens) {
            minHeapSolution.insert(item);
        }

        minHeapSolution.remove();
        myOwnAssertEquals(expectedResult, minHeapSolution.getHeap());
    }

    private static void insertManyElements_Successfully(int... itens) {
        MinHeapSolution minHeapSolution = new MinHeapSolution();
        List<Integer> expectedResult = List.of(2, 10, 5, 100, 50, 70, 30);
        for (int item : itens) {
            minHeapSolution.insert(item);
        }

        myOwnAssertEquals(expectedResult, minHeapSolution.getHeap());
    }

    private static void insertOneItem_Successefully(int... itens) {
        MinHeapSolution minHeapSolution = new MinHeapSolution();
        List<Integer> expectedResult = List.of(5);
        for (int item : itens) {
            minHeapSolution.insert(item);
        }
        myOwnAssertEquals(expectedResult, minHeapSolution.getHeap());
    }

    private static void insertTwoItems_Successefully(int... itens) {
        MinHeapSolution minHeapSolution = new MinHeapSolution();
        List<Integer> expectedResult = List.of(5, 10);
        for (int item : itens) {
            minHeapSolution.insert(item);
        }

        myOwnAssertEquals(expectedResult, minHeapSolution.getHeap());
    }

    private static void insertThreeItems_Successefully(int... itens) {
        MinHeapSolution minHeapSolution = new MinHeapSolution();
        List<Integer> expectedResult = List.of(2, 10, 5);
        for (int item : itens) {
            minHeapSolution.insert(item);
        }

        myOwnAssertEquals(expectedResult, minHeapSolution.getHeap());
    }

    private static void myOwnAssertEquals(List<Integer> expectedResult, List<Integer> heap) {
        System.out.println("Expected: " + expectedResult);
        System.out.println("Actual:   " + heap);
        assert expectedResult.equals(heap) : "Lists do not match";
    }
}