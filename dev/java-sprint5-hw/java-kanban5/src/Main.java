import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager();
        //-- 21.01
        // List<Task> history = manager.getHistory();

        Task task1 = new Task("Task #1", "Task #1 description", TaskStatus.NEW);
        final Integer taskId1 = manager.addNewTask(task1);

        Task task2 = new Task("Task #2", "Task #2 description", TaskStatus.IN_PROGRESS);
        final Integer taskId2 = manager.addNewTask(task2);

        Epic epic1 = new Epic("Epic #1", "Epic #1 description", TaskStatus.NEW);
        final int epicId1 = manager.addNewEpic(epic1);

        Epic epic2 = new Epic("Epic #2", "Epic #2 description", TaskStatus.NEW);
        final int epicId2 = manager.addNewEpic(epic2);

        Subtask subtask1 = new Subtask("Subtask #1", "Subtask #1 description", TaskStatus.NEW, epicId1);
        Subtask subtask2 = new Subtask("Subtask #2", "Subtask #2 description", TaskStatus.NEW, epicId1);
        Subtask subtask3 = new Subtask("Subtask #3", "Subtask #3 description", TaskStatus.DONE, epicId1);

        final int subtaskId1 = manager.addNewSubtask(subtask1);
        final int subtaskId2 = manager.addNewSubtask(subtask2);
        final int subtaskId3 = manager.addNewSubtask(subtask3);

        printHistory(manager.getHistory());

        manager.getTask(1);
        manager.getTask(2);
        manager.getEpic(4);
        manager.getSubtask(5);
        manager.getSubtask(6);
        manager.getEpic(3);
        manager.getTask(1);
        manager.getTask(2);

        printHistory(manager.getHistory());

        manager.deleteTask(1);

        printHistory(manager.getHistory());

        manager.deleteEpic(3);

        printHistory(manager.getHistory());

    }

    /* -- 21.01
    static void printTasks(InMemoryTaskManager inMemoryTaskManager) {
        System.out.println("Tasks: ");
        for (Task t : inMemoryTaskManager.getTasks()) {
            System.out.println(t);
        }
    }

    static void printEpics(InMemoryTaskManager inMemoryTaskManager) {
        System.out.println("Epics: ");
        for (Epic e : inMemoryTaskManager.getEpics()) {
            System.out.println(e);
        }
    }

    static void printSubtasks(InMemoryTaskManager inMemoryTaskManager) {
        System.out.println("Subtasks: ");
        for (Subtask s : inMemoryTaskManager.getSubtasks()) {
            System.out.println(s);
        }

    }*/

    static void printHistory(List<Task> history) {

        System.out.println("History: ");
        for (Task h : history) {
            System.out.println(h);
        }

    }
}