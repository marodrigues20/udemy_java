package uk.co.singly_linkedlist;

public class TestSolution {

    static void main() {

        LinkedList linkedList = new LinkedList(1);
        linkedList.append(2);
        linkedList.append(3);

        linkedList.printList();

        linkedList.reverse();

        linkedList.printList();

    }

}
