
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTasksManager extends InMemoryTaskManager {

    private static final File file = new File("./src/main/resources/text.csv");


    public static void main(String[] args) {

        loadFromFile(file);
    }

    public void save() throws ManagerSaveException {
        try (FileWriter myWriter = new FileWriter(file)) {

            List<Task> tasks = super.getTasks();

            StringBuilder text = new StringBuilder("id,type,name,status,description,epic");

            for (Task task : tasks) {
                text.append("\n" + task.toString());
            }

            List<Subtask> subtasks = super.getSubtasks();

            for (Subtask subtask : subtasks) {
                text.append("\n" + subtask.toString());
            }

            List<Epic> epics = super.getEpics();

            for (Epic epic : epics) {
                text.append("\n" + epic.toString());
            }

            text.append("\n" + "\n");

            text.append(historyToString(super.historyManager));

            myWriter.write(String.valueOf(text));
            myWriter.close();

            System.out.println(text);

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка считывания файла" + e.getMessage());
        }

    }


    public static void loadFromFile(File file) throws ManagerLoadException {

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] values = line.split(",");
                    taskFromString(values);
                } else {
                    List<Integer> historyList = historyFromString(br.readLine());
                    for (Integer historyId : historyList) {
                        if (tasks.get(historyId) != null) {
                            historyManager.addTask(tasks.get(historyId));
                        } else if (epics.get(historyId) != null) {
                            historyManager.addTask(epics.get(historyId));
                        } else {
                            historyManager.addTask(subtasks.get(historyId));

                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ManagerLoadException("Ошибка загрузки файла" + e.getMessage());
        }
    }

    public static void taskFromString(String[] values) {

        switch (values[1]) {
            case "TASK": {

                int taskId = Integer.parseInt(values[1]);

                Task task = new Task(values[2], values[4], TaskStatus.valueOf(values[3]));
                task.setId(taskId);
                tasks.put(taskId, task);
            }

            case "EPIC": {

                int epicId = Integer.parseInt(values[1]);

                Epic epic = new Epic(values[2], values[4], TaskStatus.valueOf(values[3]));
                epic.setId(epicId);
                epics.put(epicId, epic);

            }

            case "SUBTASK": {

                int epicId = Integer.parseInt(values[5]);
                int subtaskId = Integer.parseInt(values[1]);

                Subtask subtask = new Subtask(values[2], values[4], TaskStatus.valueOf(values[3]), epicId);
                subtask.setId(subtaskId);
                subtasks.put(subtaskId, subtask);
                Epic epic = epics.get(epicId);
                epic.addSubtaskId(subtaskId);
            }
        }
    }

    static String historyToString(HistoryManager manager) {
        String historyString = "";
        List<Task> history = manager.getHistory();
        for (Task task : history) {
            historyString += task.getId() + ",";
        }
        historyString = historyString.substring(0, historyString.length() - 1);
        return historyString;
    }

    static List<Integer> historyFromString(String value) {
        List<Integer> historyList = new ArrayList<>();
        String[] history = value.split(",");
        for (String historyId : history) {
            historyList.add(Integer.parseInt(historyId));

        }
        return historyList;
    }

    @Override
    public List<Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public List<Subtask> getSubtasks() {
        return super.getSubtasks();
    }

    @Override
    public List<Epic> getEpics() {
        return super.getEpics();
    }

    @Override
    public List<Subtask> getEpicSubtasks(int epicId) {
        return super.getEpicSubtasks(epicId);
    }

    @Override
    public Task getTask(int id) {
        return super.getTask(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        return super.getSubtask(id);
    }

    @Override
    public Epic getEpic(int id) {
        return super.getEpic(id);
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    @Override
    public void addNewTask(Task task) {
        super.addNewTask(task);
        save();
    }

    @Override
    public void addNewEpic(Epic epic) {
        super.addNewEpic(epic);
        save();
    }

    @Override
    public void addNewSubtask(Subtask subtask) {
        super.addNewSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }
}
