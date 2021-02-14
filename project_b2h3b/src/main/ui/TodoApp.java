package ui;

import model.MyTodoList;
import model.Priority;
import model.Task;
import persistance.MyTodoJsonReader;
import persistance.MyTodoJsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class TodoApp {

    private MyTodoList todoList;
    private Scanner input;
    private String fileName;
    private static final String JSON_STORE = "./data";


    //EFFECTS: runs the To-do application
    public TodoApp() {
        todoList = new MyTodoList("Dalia's todolist");
        runTodo();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTodo() {
        boolean keepGoing = true;
        String command;

        init();
        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            command = command.toUpperCase();

            if (command.equals("Q") || command.equals("")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "C":
                create();
                break;
            case "U":
                update();
                break;
            case "D":
                delete();
                break;
            case "M":
                complete();
                break;
            case "L":
                list(null);
                break;
            default:
                processMaintenanceCommand(command);
                break;
        }
    }

    //overloaded method
    private void processMaintenanceCommand(String command) {
        switch (command) {
            case "S":
                save();
                break;
            case "R":
                retrieve();
                break;
            case "N":
                newTodo();
                break;
            default:
                display("Selection not valid...", "S");
                break;
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes to-do list
    private void init() {
        this.todoList = new MyTodoList("My TO-DO List");
        this.fileName = null;
        this.input = new Scanner(System.in);
    }

    //EFFECTS: displays menu of options to user
    private void displayMenu() {
        display("Select from:", "P");
        display("C -> Create a Task", "M");
        display("U -> Update a Task", "M");
        display("D -> Delete a Task", "M");
        display("M -> Mark Complete a Task", "M");
        display("L -> List Tasks", "M");
        display("S -> Save Todo", "M");
        display("R -> Retrieve Todo from File", "M");
        display("N -> New ToDo", "M");
        display("Q -> Quit", "M");
    }

    // MODIFIES: this
    // EFFECTS: creates a new task
    private void create() {
        String name = inputName(null);
        String description = inputDescription(null);
        LocalDate dueDate = inputDueDate(null);
        Priority priority = inputPriority(null);
        Task myTask = new Task(name, description, dueDate, priority);
        todoList.addTask(myTask, myTask.getHashKey());
        display("Task added", "S");
    }

    // MODIFIES: this
    // EFFECTS : updates a task
    private void update() {
        Task myTask = select();
        if (myTask != null) {
            myTask.setName(inputName(myTask.getName()));
            myTask.setDescription(inputDescription(myTask.getDescription()));
            myTask.setDueDate(inputDueDate(myTask.getDueDate()));
            myTask.setPriority(inputPriority(myTask.getPriority()));
            display("Task updated", "S");
        }
    }

    //MODIFIES: this
    // EFFECTS: deletes a task
    private void delete() {
        Task myTask = select();
        if (myTask != null) {
            todoList.removeTask(myTask.getHashKey());
            display("Task deleted", "S");
        }
    }

    //MODIFIES: this
    // EFFECTS: marks a task as complete
    private void complete() {
        Task myTask = select();
        if (myTask != null) {
            myTask.setComplete(true);
            display("Task marked as complete", "S");
        }
    }

    // EFFECTS: if to-do list is empty, it returns null
    //  otherwise, it selects a particular task
    private Task select() {
        if (todoList.getTodo().size() == 0) {
            display("Todo List is empty", "S");
            return null;
        }
        list(null);
        while (true) {
            display("Which task do you want to select?", "S");
            String in = input.nextLine();
            if (!((Integer.parseInt(in) > todoList.countTasks()) || (Integer.parseInt(in) == 0))) {
                return todoList.getTaskByIndex(Integer.parseInt(in));
            }
        }
    }

    //EFFECTS: lists all tasks in to-do list
    private void list(String order) {
        HashMap<Integer, Task> myTodo = todoList.getTodo();
        if (order != null) {
            // ORDER myTodo
        }
        if (todoList.getTodo().size() == 0) {
            display("Todo List is empty", "S");
        }
        Iterator<Task> iterator = todoList.getTodo().values().iterator();
        int record = 0;
        while (iterator.hasNext()) {
            Task t = iterator.next();
            if (record == 0) {
                display(t.toStringTable(record), "X");
            }
            record++;
            display(t.toStringTable(record), "X");
        }
    }

    // EFFECTS: saves to file
    private void save() {
        if (fileName == null) {
//            String folder = inputFolder();
            String fileName = inputFileName();
            this.fileName = JSON_STORE + "/" + fileName;
        }
        MyTodoJsonWriter wr = new MyTodoJsonWriter(this.fileName);
        try {
            wr.open();
            wr.write(todoList);
            display("Todo saved", "S");
        } catch (FileNotFoundException e) {
            display("Saved failed.. folder not found", "S");
        } finally {
            wr.close();
        }
    }

    // EFFECTS: retrieves from file
    private void retrieve() {
//        String folder = inputFolder();
        String fileName = inputFileName();
        MyTodoJsonReader rd = new MyTodoJsonReader(JSON_STORE + "/" + fileName);
        try {
            this.todoList = rd.readMyToDoList();
            this.fileName = JSON_STORE + "/" + fileName;
            list(null);
            display("Todo List Loaded", "S");
        } catch (IOException e) {
            display("Load failed.. folder not found", "S");
        }
    }


    private void newTodo() {
        if (todoList.countTasks() != 0) {
            display("Do you want to save this ToDo List (Y/N)", "Q");
            if (input.nextLine().equals("Y")) {
                save();
            }
        }
        init();
    }

    // EFFECTS: displays messages
    private void display(String message, String style) {
        switch (style) {
            case ("P"):
                System.out.println(message + ": ");
                break;
            case ("S"):
                System.out.println(message + "!");
                break;
            case ("Q"):
                System.out.println(message + "?");
                break;
            default:
                System.out.println(message);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: gives a task a name
    private String inputName(String name) {
        while (true) {
            display("Enter the name of your new task", "P");
            display("Name should be alphanumeric maximum 30 characters", "S");
            if (name != null) {
                display("Current value is (Enter to keep the same value): " + name, "S");
            }
            String in = input.nextLine();
            if (in.length() == 0 && name != null) {
                return name;
            }
            if (!((in.length() > 30) || (in.length()) == 0)) {
                return in;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: gives a task a description

    private String inputDescription(String description) {
        while (true) {
            display("Enter the description of your new task", "P");
            display("Description should be maximum 240 characters", "S");
            if (description != null) {
                display("Current value is (Enter to keep the same value): " + description, "S");
            }
            String in = input.nextLine();
            if (in.length() == 0 && description != null) {
                return description;
            }
            if (!((in.length() > 240) || (in.length()) == 0)) {
                return in;
            }

        }
    }


    // MODIFIES: this
    // EFFECTS: gives a task a due date
    private LocalDate inputDueDate(LocalDate dueDate) {
        while (true) {
            display("Enter the due date of your new task", "P");
            display("Due Date should be of format (YYYY-MM-DD) and a date in the future", "S");
            if (dueDate != null) {
                display("Current value is (Enter to keep the same value): " + dueDate.toString(), "S");
            }
            String in = input.nextLine();
            if (in.length() == 0 && dueDate != null) {
                return dueDate;
            }
            if (LocalDate.parse(in).isAfter(LocalDate.now())) {
                return LocalDate.parse(in);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: gives a task a priority level
    private Priority inputPriority(Priority priority) {
        while (true) {
            display("Choose a priority level for your new task", "P");
            display("Priority can be H> High, M> Medium, L> Low", "S");
            if (priority != null) {
                display("Current value is (Enter to keep the same value): " + priority.toString(), "S");
            }
            String in = input.nextLine();
            if (in.length() == 0 && priority != null) {
                return priority;
            }
            if (in.toUpperCase().equals("H")) {
                return Priority.HIGH;
            }
            if (in.toUpperCase().equals("M")) {
                return Priority.MEDIUM;
            }
            if (in.toUpperCase().equals("L")) {
                return Priority.LOW;
            }
        }
    }


    // EFFECTS: enters folder name you want to save the task to

    private String inputFolder() {
        String folderName;
        while (true) {
            display("Enter the name of existing folder ", "P");
            folderName = input.nextLine();
            if (folderName.length() > 0) {
                return folderName;
            }
        }
    }

    //EFFECTS : gives the file a name
    private String inputFileName() {
        String fileName;
        while (true) {
            display("Enter the name of file", "P");
            fileName = input.nextLine();
            if (fileName.length() > 0) {
                return fileName;
            }
        }
    }
}