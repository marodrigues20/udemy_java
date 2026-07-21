package uk.co.heap.min_heap;

import java.util.ArrayList;
import java.util.List;

public class MinHeapSolution {

    private List<Integer> heap;

    public MinHeapSolution() {
        heap = new ArrayList<>();
    }

    public List<Integer> getHeap() {
        return new ArrayList<>(this.heap);
    }

    private int parent(int index) {
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return (index * 2) + 1;
    }

    private int     rightChild(int index) {
        return (index * 2) + 2;
    }

    private void swap(int index1, int index2) {
        int temp = this.heap.get(index2);
        this.heap.set(index2, this.heap.get(index1));
        this.heap.set(index1, temp);
    }

    public boolean insert(Integer value) {
        this.heap.add(value);
        int current = this.heap.size() - 1;

        while (current > 0 && this.heap.get(current) < this.heap.get(this.parent(current))) {
            int parent = this.parent(current);
            swap(current, parent);
            current = parent;
        }
        return true;
    }

    private void sinkDown(int index) {

        if (index < 0 || index > this.heap.size() - 1) throw new IllegalArgumentException();

        int minCurrentIndex = index;

        while (true) {
            int leftChildIndex = this.leftChild(index);
            int rightChildIndex = this.rightChild(index);

            if ( (leftChildIndex < this.heap.size()) && this.heap.get(minCurrentIndex) > this.heap.get(leftChildIndex)) {
                minCurrentIndex = leftChildIndex;
            }

            if ( (rightChildIndex < this.heap.size()) && this.heap.get(minCurrentIndex) > this.heap.get(rightChildIndex)) {
                minCurrentIndex = rightChildIndex;
            }


            if (index != minCurrentIndex) {
                swap(index, minCurrentIndex);
                index = minCurrentIndex;
            } else {
                return;
            }
        }
    }


    public Integer remove() {

        if (this.heap.size() == 0) return null;

        if (this.heap.size() == 1) return this.heap.remove(0);

        Integer temp = this.heap.get(0);
        this.heap.set(0, this.heap.remove(this.heap.size() - 1));
        sinkDown(0);

        return temp;
    }

}

