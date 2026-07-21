package uk.co.stack;

public class MainStackTest {

    static void main() {

        Stack stack = new Stack(1);
        stack.push(2);
        stack.push(3);

        stack.printStack();

        System.out.println("************** 2 pop() **************");
        stack.pop();
        stack.pop();
        stack.printStack();

    }
}
