import java.util.List;

public class Main {

    public static void main(String[] args) {

        TaskManager manager = new FileBackedTasksManager();

        Task task1 = new Task("Task #1", "Task #1 description", TaskStatus.NEW);
        manager.addNewTask(task1);

        Task task2 = new Task("Task #2", "Task #2 description", TaskStatus.IN_PROGRESS);
        manager.addNewTask(task2);

        Epic epic1 = new Epic("Epic #1", "Epic #1 description", TaskStatus.NEW);
        manager.addNewEpic(epic1);

        Epic epic2 = new Epic("Epic #2", "Epic #2 description", TaskStatus.NEW);
        manager.addNewEpic(epic2);

        Subtask subtask1 = new Subtask("Subtask #1", "Subtask #1 description", TaskStatus.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Subtask #2", "Subtask #2 description", TaskStatus.NEW, epic1.getId());
        Subtask subtask3 = new Subtask("Subtask #3", "Subtask #3 description", TaskStatus.DONE, epic1.getId());


        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.addNewSubtask(subtask3);


        manager.getTask(1);
        manager.getTask(2);
        manager.getEpic(4);
        manager.getSubtask(5);
        manager.getSubtask(6);
        manager.getEpic(3);
        manager.getTask(1);
        manager.getTask(2);

        manager.deleteTask(1);

        TaskManager manager1 = new FileBackedTasksManager();
        printHistory(manager1.getHistory());

    }

    static void printHistory(List<Task> history) {

        System.out.println("History: ");
        for (Task h : history) {
            System.out.println(h);
        }

    }
}