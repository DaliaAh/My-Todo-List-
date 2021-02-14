package model;

import java.time.LocalDate;
import java.util.Objects;
import org.json.JSONObject;
import persistance.Writable;

// A task that has a hashKey, name, description, createDate, dueDate, priority level, and
// whether it is complete or not

public class Task implements Writable {
    private Integer hashKey;
    private String name;
    private String description;
    private LocalDate createDate;
    private LocalDate dueDate;
    private Priority priority;
    private boolean complete;

    //constructor
    // creates a task with a name, description, dueDate, priority that is not complete
    public Task(String name, String description, LocalDate dueDate, Priority priority) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.createDate = LocalDate.now();
        this.complete = false;
        this.hashKey = hashCode();
    }

    //constructor
    // creates a task loading from file
    public Task(Integer hashKey, String name, String description,
                LocalDate createDate, LocalDate dueDate, Priority priority, boolean complete) {
        this.hashKey = hashKey;
        this.name = name;
        this.description = description;
        this.createDate = createDate;
        this.dueDate = dueDate;
        this.priority = priority;
        this.complete = complete;
    }

    // EFFECTS: returns hashKey
    public int getHashKey() {
        return hashKey;
    }

    // EFFECTS: sets the task's hashKey value to a new hashKey value
    public void setHashKey(int hashKey) {
        this.hashKey = hashKey;
    }

    // EFFECTS: returns the name of the task
    public String getName() {
        return name;
    }

    // EFFECTS : sets the name of the task to name
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns the description of a task
    public String getDescription() {
        return description;
    }

    // EFFECTS: sets the description of a task to description
    public void setDescription(String description) {
        this.description = description;
    }

    // EFFECTS: returns the createDate of the task
    public LocalDate getCreateDate() {
        return createDate;
    }

    // EFFECTS: sets the createDate of the task
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    // EFFECTS: returns the dueDate of the task
    public LocalDate getDueDate() {
        return dueDate;
    }

    // EFFECTS: sets the dueDate of the task
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    // EFFECTS: returns the priority of the task
    public Priority getPriority() {
        return this.priority;
    }

    // EFFECTS: sets the priority of the task
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    // EFFECTS: returns false if task is not complete,
    // true otherwise
    public boolean isComplete() {
        return complete;
    }

    // EFFECTS: sets the task's complete to complete
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return priority.toString().equals(task.priority.toString())
                && complete == task.complete
                && name.equals(task.name)
                && description.equals(task.description)
                && createDate.toString().equals(task.createDate.toString())
                && dueDate.toString().equals(task.dueDate.toString());
    }

    //EFFECTS: puts the elements of a task into a table as strings
    public String toStringTable(int record) {
        if (record == 0) {
            return String.format("%1$" + 4 + "s", "|")
                    + String.format("%1$" + 20 + "s", "Name") + "|"
                    + String.format("%1$" + 30 + "s", "Description") + "|"
                    + String.format("%1$" + 12 + "s", "Create Date") + "|"
                    + String.format("%1$" + 20 + "s", "Due Date") + "|"
                    + String.format("%1$" + 20 + "s", "Priority") + "|"
                    + String.format("%1$" + 20 + "s", "Complete") + "|";
        }
        return String.format("%1$" + 4 + "s", record + "|")
                + String.format("%1$" + 20 + "s", name) + "|"
                + String.format("%1$" + 30 + "s", description) + "|"
                + String.format("%1$" + 12 + "s", createDate) + "|"
                + String.format("%1$" + 20 + "s", dueDate) + "|"
                + String.format("%1$" + 20 + "s", priority.toString()) + "|"
                + String.format("%1$" + 20 + "s", (complete ? "Y" : "N")) + " |";
    }

    @Override
    public String toString() {
        return "Task{"
                + "hashKey=" + hashKey
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", createDate=" + createDate
                + ", dueDate=" + dueDate
                + ", priority=" + priority
                + ", complete=" + complete
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, createDate, dueDate, priority, complete);
    }

    // EFFECTS: changes a task into a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("hashKey", hashKey);
        json.put("name", name);
        json.put("description", description);
        json.put("createDate", createDate.toString());
        json.put("dueDate", dueDate.toString());
        json.put("priority", priority.toString());
        json.put("complete", complete);
        return json;
    }

    // EFFECTS: changes a JSONObject to a task
    public static Task fromJson(JSONObject jo) {
        Integer hashKey = jo.getInt("hashKey");
        String name = jo.getString("name");
        String description = jo.getString("description");
        LocalDate createDate = LocalDate.parse(jo.getString("createDate"));
        LocalDate dueDate = LocalDate.parse(jo.getString("dueDate"));
        Priority priority = Priority.valueOf(jo.getString("priority"));
        boolean complete = jo.getBoolean("complete");
        return new Task(hashKey, name, description, createDate, dueDate, priority, complete);
    }

}
