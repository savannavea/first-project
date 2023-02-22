import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    CustomLinkedList customLinkedList = new CustomLinkedList();

    protected static Map<Integer, Node> history = new HashMap<>();

    public void setHistory(Map<Integer, Node> history) {
        this.history = history;
    }

    @Override
    public List<Task> getHistory() {
        return customLinkedList.getTasks();
    }

    @Override
    public void addTask(Task task) {

        if (task == null) {
            return;
        }
        customLinkedList.linkLast(task);
    }

    @Override
    public void remove(int id) {
        customLinkedList.removeNode(history.get(id));
    }

    public static class CustomLinkedList {

        public Node first = null;
        public Node last = null;

        public void linkLast(Task task) {

            Node node = new Node(task, last, null);

            if (history.size() == 0) {
                first = node;
            }

            history.put(task.getId(), node);

            Node preLast = last;
            if (preLast != null) {
                preLast.next = node;

                node.next = null;
            }
            last = node;
        }

        public ArrayList<Task> getTasks() {

            ArrayList<Task> tasks = new ArrayList<>();
            ArrayList<Node> nodes = new ArrayList<>(history.values());
            for (Node node : nodes) {
                tasks.add(node.task);
            }
            return tasks;
        }

        private void removeNode(Node node) {

            if (node == null) {
                return;
            }

            history.remove(node.task.getId());

            if (node.prev == null) {
                first = node.next;
                if (node.next != null) {
                    node.next.prev = null;
                }
            } else if (node.next != null) {

                Node prevNode = node.prev;
                Node nextNode = node.next;
                prevNode.next = nextNode;
                nextNode.prev = node.prev;

            } else {
                last = node.prev;
                node.prev.next = null;
            }
        }
    }
}
