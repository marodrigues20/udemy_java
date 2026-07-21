package uk.co.queue;

public class MainQueueTest {

    static void main() {

        System.out.println("=========== Scenario 1: basic enqueue/dequeue ===========");
        Queue queue = new Queue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.printQueue();

        Queue.Node node = queue.dequeue();
        System.out.println("First Person: " + node.value);
        queue.printQueue();

        System.out.println("=========== Scenario 2: drain the queue completely ===========");
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.printQueue(); // should print "empty" message, no exception

        System.out.println("=========== Scenario 3: dequeue on an already empty queue ===========");
        Queue.Node emptyNode = queue.dequeue();
        System.out.println("Result of dequeue on empty queue: " + emptyNode); // expect null

        System.out.println("=========== Scenario 4: enqueue again after being fully drained ===========");
        queue.enqueue(99);
        queue.printQueue(); // should show just 99, first/last must both point to it

        queue.enqueue(100);
        queue.printQueue(); // should show 99, 100 in order

        System.out.println("=========== Scenario 5: peek on non-empty and empty queue ===========");
        Queue.Node peeked = queue.peek();
        System.out.println("Peek (should be 99): " + peeked.value);

        Queue single = new Queue(42);
        single.dequeue();
        Queue.Node peekEmpty = single.peek(); // should print "Queue is empty." and return null
        System.out.println("Peek on empty queue result: " + peekEmpty);
    }
}