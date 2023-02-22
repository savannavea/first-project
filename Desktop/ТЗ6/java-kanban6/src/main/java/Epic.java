import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    protected List<Integer> subtasksIds = new ArrayList<>();

    public Epic(String name, String description, TaskStatus status) {
        super(name, description, status);
        this.type = TaskType.EPIC;
    }

    public void addSubtaskId(int id) {
        subtasksIds.add(id);
    }

    public List<Integer> getSubtaskIds() {

        return subtasksIds;
    }

    public void cleanSubtasksId() {
        subtasksIds.clear();
    }

    @Override
    public String toString() {

        return id +
                "," + type +
                "," + name +
                "," + status +
                "," + description +
                ",";
    }
}