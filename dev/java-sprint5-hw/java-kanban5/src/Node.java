public class Node {

    Task task;
    Node prev;
    Node next;

    public Node(Task task, Node prev, Node next) {
        this.task = task;
        this.prev = prev;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node {" +
                "task " + task +
                ", prev '" + prev + '\'' +
                ", next '" + next + '\'' +
                '}';
    }
}
