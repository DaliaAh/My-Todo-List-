package model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

// A to-do list that has tasks

public class MyTodoList {
    private String name;
    private final LinkedHashMap<Integer, Task> todo;

    // constructor
    // creates an empty to-do list with
    public MyTodoList(String name) {
        this.name = name;
        this.todo = new LinkedHashMap<>();
    }

    // return name of to-do list
    public String getName() {
        return name;
    }

    // set name of to-do list to name
    public String setName(String name) {
        this.name = name;
        return name;
    }

    //return the to-do list
    public LinkedHashMap<Integer, Task> getTodo() {
        return todo;
    }


    // REQUIRES: task that is not already in the to-do list
    // MODIFIES : this
    // EFFECTS : adds a new task to the to-do list
    public boolean addTask(Task task, Integer hashCode) {
        if (todo.containsKey(hashCode)) {
            return false;
        }
        todo.put(hashCode, task);
        return true;
    }

    // REQUIRES : task that is in the to-do list
    // MODIFIES : this
    // EFFECTS  : removes a task from the to-do list
    public boolean removeTask(Integer hashCode) {
        if (todo.containsKey(hashCode)) {
            todo.remove(hashCode);
            return true;
        }
        return false;
    }

    // EFFECTS: returns Task with given hashCode
    public Task getTask(Integer hashCode) {
        if (todo.containsKey(hashCode)) {
            return todo.get(hashCode);
        }
        return null;
    }

    // EFFECTS: returns Task with given index
    public Task getTaskByIndex(int index) {
        ArrayList<Task> tasks = new ArrayList<>(todo.values());
        return tasks.get(index - 1);
    }

    // EFFECTS: returns size of the to-do list
    public int countTasks() {
        return todo.size();
    }

    // EFFECTS: counts number of tasks completed
    public int countTasksComplete() {
        int i = 0;
        for (Integer integer : todo.keySet()) {
            if (todo.get(integer).isComplete()) {
                i++;
            }
        }
        return i;
    }

    // EFFECTS : counts number of tasks that are incomplete
    public int countTasksInComplete() {
        int i = 0;
        for (Integer integer : todo.keySet()) {
            if (!todo.get(integer).isComplete()) {
                i++;
            }
        }
        return i;
    }

    //EFFECTS: checks to see if two objects are the same
    // if both objects don't have the same name, return false
    // if both objects don't have the same size of keySet(), return false
    // if both objects don't have contain the same hashKeys, return false
    // if both objects don't have the same hashKey for a specific task, return false
    // otherwise return true
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MyTodoList todoList = (MyTodoList) o;
        if (!Objects.equals(name, todoList.name)) {
            return false;
        }

        if (todo.keySet().size() != todoList.getTodo().keySet().size()) {
            return false;
        }
        for (Integer k : todo.keySet()) {
            if (!todoList.getTodo().containsKey(k)) {
                return false;
            }
            Task t = todoList.getTodo().get(k);
            if ((!t.equals(todo.get(k)))) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: returns the TodoList in string format
    @Override
    public String toString() {
        return "MyTodoList{" + "name='" + name + '\'' + ", todo=" + todo.toString() + '}';
    }

    // EFFECTS: changes the TodoList object into a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        json.put("name", name);
        for (Task t : todo.values()) {
            array.put(t.toJson());
        }
        json.put("tasks", array);
        return json;
    }

    // MODIFIES: to-do
    // EFFECTS: parses tasks from JSOn object and add them to to-do list
    // returns to-do
    public static MyTodoList fromJson(JSONObject jo) {
        String name = jo.getString("name");
        MyTodoList todo = new MyTodoList(name);
        JSONArray ja = jo.getJSONArray("tasks");
        for (Object json : ja) {
            JSONObject nextTask = (JSONObject) json;
            todo.addTask(Task.fromJson(nextTask), nextTask.getInt("hashKey"));
        }
        return todo;
    }
}