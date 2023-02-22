

public class Subtask extends Task {

    protected int epicId;

    public Subtask(String name, String description, TaskStatus status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
        this.type = TaskType.SUBTASK;
    }

    public int getEpicId() {
        return epicId;
    }


    @Override
    public String toString() {

        return id +
                "," + type +
                "," + name +
                "," + status +
                "," + description +
                "," + epicId;
    }
}
