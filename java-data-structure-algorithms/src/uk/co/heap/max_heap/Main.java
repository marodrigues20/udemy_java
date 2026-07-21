package uk.co.heap.max_heap;

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
        MaxHeapSolution maxHeapSolution = new MaxHeapSolution();
        List<Integer> expectedResult = List.of(50, 10, 30, 5, 2);
        for(int item: itens){
            maxHeapSolution.insert(item);
        }

        maxHeapSolution.remove();
        maxHeapSolution.remove();
        myOwnAssertEquals(expectedResult, maxHeapSolution.getHeap());
    }

    private static void removeOneItem_Successfully(int... itens) {
        MaxHeapSolution maxHeapSolution = new MaxHeapSolution();
        List<Integer> expectedResult = List.of(70, 50, 30, 5, 10, 2);
        for(int item: itens){
            maxHeapSolution.insert(item);
        }

        maxHeapSolution.remove();
        myOwnAssertEquals(expectedResult, maxHeapSolution.getHeap());
    }

    private static void insertManyElements_Successfully(int... itens) {
        MaxHeapSolution maxHeapSolution = new MaxHeapSolution();
        List<Integer> expectedResult = List.of(100, 50, 70, 5, 10, 2, 30);
        for(int item: itens){
            maxHeapSolution.insert(item);
        }

        myOwnAssertEquals(expectedResult, maxHeapSolution.getHeap());
    }

    private static void insertOneItem_Successefully(int... itens) {
        MaxHeapSolution maxHeapSolution = new MaxHeapSolution();
        List<Integer> expectedResult = List.of(5);
        for(int item: itens ){
            maxHeapSolution.insert(item);
        }
        myOwnAssertEquals(expectedResult, maxHeapSolution.getHeap());
    }

    private static void insertTwoItems_Successefully(int... itens) {
        MaxHeapSolution maxHeapSolution = new MaxHeapSolution();
        List<Integer> expectedResult = List.of(10, 5);
        for(int item: itens ){
            maxHeapSolution.insert(item);
        }

        myOwnAssertEquals(expectedResult, maxHeapSolution.getHeap());
    }

    private static void insertThreeItems_Successefully(int... itens) {
        MaxHeapSolution maxHeapSolution = new MaxHeapSolution();
        List<Integer> expectedResult = List.of(10, 5, 2);
        for(int item: itens ){
            maxHeapSolution.insert(item);
        }

        myOwnAssertEquals(expectedResult, maxHeapSolution.getHeap());
    }

    private static void myOwnAssertEquals(List<Integer> expectedResult, List<Integer> heap) {
        System.out.println("Expected: " + expectedResult);
        System.out.println("Actual:   " + heap);
        assert expectedResult.equals(heap) : "Lists do not match";
    }
}
