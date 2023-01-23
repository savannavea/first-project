import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private int generatorId = 0;

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getEpicSubtasks(int epicId) {
        List<Subtask> subtaskArrayList = new ArrayList<>();
        Epic epic = getEpic(epicId);
        if (epic != null) {
            for (Integer id : epic.getSubtaskIds()) {
                subtaskArrayList.add(subtasks.get(id));
            }
        }
        return subtaskArrayList;
    }

    @Override
    public Task getTask(int id) {
        final Task task = tasks.get(id);
        historyManager.addTask(task);
        return task;
    }

    @Override
    public Subtask getSubtask(int id) {
        final Subtask subtask = subtasks.get(id);
        historyManager.addTask(subtask);
        return subtask;
    }

    @Override
    public Epic getEpic(int id) {
        final Epic epic = epics.get(id);
        historyManager.addTask(epic);
        return epic;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public int addNewTask(Task task) {
        int id = getGenerationId();
        task.setId(id);
        tasks.put(id, task);
        historyManager.addTask(task);
        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int id = getGenerationId();
        epic.setId(id);
        epics.put(id, epic);
        historyManager.addTask(epic);
        return id;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        int id = getGenerationId();
        subtask.setId(id);
        Epic epic = getEpic(subtask.getEpicId());
        if (epic == null) {
            System.out.println("No such epic " + subtask.getEpicId());
            return -1;
        }
        epic.addSubtaskId(id);
        subtasks.put(id, subtask);
        updateEpicStatus(epic);
        historyManager.addTask(subtask);
        return id;
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(getEpic(subtask.getEpicId()));
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = getEpic(id);
        epics.remove(id);
        for (Integer subtaskId : epic.getSubtaskIds()) {
            deleteSubtask(subtaskId);
        }
        historyManager.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        subtasks.remove((id));
        historyManager.remove(id);
    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        subtasks.clear();
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    private int getGenerationId() {
        return ++generatorId;
    }

    private void updateEpicStatus(Epic epic) {

        Set<TaskStatus> setStatus = new HashSet<TaskStatus>();
        List<Subtask> epicSubtasks = getEpicSubtasks(epic.getId());
        for (Subtask subtask : epicSubtasks) {
            setStatus.add(subtask.getStatus());
        }
        if (setStatus.isEmpty() || (setStatus.size() == 1 && setStatus.contains(TaskStatus.NEW))) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        if (setStatus.size() == 1 && setStatus.contains(TaskStatus.DONE)) {
            epic.setStatus(TaskStatus.DONE);
            return;
        }
        epic.setStatus(TaskStatus.IN_PROGRESS);
    }
}