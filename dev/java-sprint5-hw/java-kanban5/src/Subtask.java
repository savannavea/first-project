public class Subtask extends Task {

    protected int epicId;

    public Subtask(String name, String description, TaskStatus status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Subtask)) return false;
        if (!super.equals(obj)) return false;
        Subtask subtask = (Subtask) obj;
        return epicId == subtask.epicId;
    }

    @Override
    public String toString() {
        return "Subtask {" +
                "id " + id +
                ", epic id '" + epicId + '\'' +
                ", name '" + name + '\'' +
                ", description '" + description + '\'' +
                ", status '" + status + '\'' +
                '}';
    }
}