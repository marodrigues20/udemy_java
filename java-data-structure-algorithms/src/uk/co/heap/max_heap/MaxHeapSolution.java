package uk.co.heap.max_heap;

import java.util.ArrayList;
import java.util.List;

public class MaxHeapSolution {

    private List<Integer> heap;

    public MaxHeapSolution() {
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
        this.heap.add(value); // Add item into the tree.
        int current = this.heap.size() - 1; // Get the index from the last item added.

        while (current > 0 && this.heap.get(current) > this.heap.get(this.parent(current))) {
            int parent = this.parent(current); // Get the parent from the item included.
            swap(current, parent); // Swap child and parent.
            current = parent;      // Current point to Parent.
        }
        return true;
    }

    private void sinkDown(int index) {

        if (index < 0 || index > this.heap.size() - 1) throw new IllegalArgumentException();

        int maxCurrentIndex = index;

        while (true) {
            int leftChildIndex = this.leftChild(index);
            int rightChildIndex = this.rightChild(index);

            if ( (leftChildIndex < this.heap.size()) && this.heap.get(maxCurrentIndex) < this.heap.get(leftChildIndex)) {
                maxCurrentIndex = leftChildIndex;
            }

            if ( (rightChildIndex < this.heap.size()) && this.heap.get(maxCurrentIndex) < this.heap.get(rightChildIndex)) {
                maxCurrentIndex = rightChildIndex;
            }


            if (index != maxCurrentIndex) {
                swap(index, maxCurrentIndex);
                index = maxCurrentIndex;
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
