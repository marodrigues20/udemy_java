package uk.co.singly_linked_list.section_04;

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
