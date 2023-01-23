import java.util.List;

public interface HistoryManager {

    List<Task> getHistory();

    void addTask(Task task);

    //++ 21.01
    void remove(int id);


}