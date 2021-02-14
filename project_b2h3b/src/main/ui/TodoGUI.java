package ui;

import model.MyTodoList;
import model.Priority;
import model.Task;
import persistance.MyTodoJsonReader;
import persistance.MyTodoJsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Objects;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

// This is the GUI class.

public class TodoGUI extends JFrame {
    private JFrame jframe;
    private JPanel panelSelectFile;
    private JPanel panelList;
    private JPanel panelTask;
    private JList<String> list;
    private JTextField textFieldName = getTextFieldName();
    private JTextField textFieldDescription = getTextFieldDescription();
    private JFormattedTextField textFieldDueDate = getTextFieldDueDate();
    private JComboBox<Priority> comboPriority = getPriorityJComboBox();


    private MyTodoJsonReader reader;
    private MyTodoJsonWriter writer;
    private Task myTask;
    private MyTodoList myTodo;
    private boolean showCompleted = false;
    private String fileName;

    AudioInputStream audioInputStream;

    //Constructor
    //EFFECTS: constructs the GUI
    public TodoGUI() {
        todoChooserGui();
    }

    //EFFECTS: create and position GUI components on the first panel of the application
    private void todoChooserGui() {
        jframe = makeFrame("My Todo List", 500, 800, JFrame.EXIT_ON_CLOSE);
        panelSelectFile = makePanel(jframe, BorderLayout.CENTER,
                "Choose a Todo", 150, 50, 200, 25);
        JButton clickRetrieve = makeButton("retrieveTodo", "Retrieve A TodoList",
                175, 100, 150, 25, JComponent.CENTER_ALIGNMENT, "cli.wav");
        panelSelectFile.add(clickRetrieve);
        JTextField textFieldName = makeJTextField(1, 100, 150, 150, 25);
        textFieldName.setName("Name");
        panelSelectFile.add(textFieldName);
        JButton clickNew = makeButton("newTodo", "Make New TodoList",
                250, 150, 150, 25, JComponent.CENTER_ALIGNMENT, "cli.wav");
        panelSelectFile.add(clickNew);
        panelSelectFile.setBackground(Color.WHITE);
        jframe.setBackground(Color.PINK);
        jframe.setVisible(true);
    }

    //EFFECTS: create and position GUI components on the second panel of the application
    private void todoListGui() {
        panelSelectFile.setVisible(false);
        if (panelTask != null) {
            panelTask.setVisible(false);
        }

        makePanelList();
        syncListFromTodo();

        jframe.setTitle(myTodo.getName());
        panelList.setBackground(Color.ORANGE);
        panelList.setVisible(true);
    }

    //EFFECTS: position GUI components on the third panel of the application
    private void todoTaskGui(String actionTitle, Task task) {
        panelSelectFile.setVisible(false);
        panelList.setVisible(false);
        jframe.setTitle(actionTitle);

        makePanelTask();
        populateFromTask(actionTitle, task);
        assignComponents();

        jframe.setTitle("Task View");
        panelTask.setBackground(Color.WHITE);
        panelTask.setVisible(true);
    }

    //EFFECTS: create the buttons and panel needed for todoListGui method
    private void makePanelList() {
        if (panelList == null) {
            panelList = makePanel(jframe, BorderLayout.CENTER,
                    "List of Tasks", 180, 50, 200, 25);
            list = makeList(10, 10, 100, 470, 500);

            JButton clickUpdate = makeButton("updateTask", "Update",
                    20, 620, 100, 25, JComponent.CENTER_ALIGNMENT, "cli.wav");

            JButton clickDelete = makeButton("deleteTask", "Delete",
                    125, 620, 100, 25, JComponent.CENTER_ALIGNMENT, null);

            JButton clickComplete = makeButton("completeTask", "Complete",
                    230, 620, 100, 25, JComponent.CENTER_ALIGNMENT, null);

            JButton clickIncomplete = makeButton("IncompleteTask", "UndoComplete",
                    335, 620, 130, 25, JComponent.CENTER_ALIGNMENT, "cli.wav");

            JButton clickCreate = makeButton("newTask", "Create A Task",
                    175, 670, 150, 25, JComponent.CENTER_ALIGNMENT, "cli.wav");

            JButton showComplete = makeButton("showComplete", "Show Completed",
                    175, 700, 150, 25, JComponent.CENTER_ALIGNMENT, "cli.wav");
            showComplete.getModel().setPressed(showCompleted);

            JButton clickSave = makeButton("saveTodo", "Save List",
                    175, 730, 150, 25, JComponent.CENTER_ALIGNMENT, "cli.wav");

            addElementsToPanelList(list, clickUpdate, clickDelete, clickComplete, clickIncomplete, clickCreate,
                    showComplete, clickSave);
        }
    }

    // MODIFIES: panelList
    // EFFECTS: adds elements to panelList
    private void addElementsToPanelList(JList<String> jlist, JButton clickUpdate, JButton clickDelete,
                                        JButton clickComplete, JButton clickIncomplete,
                                        JButton clickCreate, JButton showComplete,
                                        JButton clickSave) {
        panelList.add(jlist);
        panelList.add(clickUpdate);
        panelList.add(clickDelete);
        panelList.add(clickComplete);
        panelList.add(clickIncomplete);
        panelList.add(clickCreate);
        panelList.add(showComplete);
        panelList.add(clickSave);
    }

    //EFFECTS: create the buttons and panel needed for todoTaskGui method
    private void makePanelTask() {
        if (panelTask == null) {
            panelTask = makePanel(jframe, BorderLayout.CENTER,
                    "Task Details", 180, 50, 200, 25);

            JLabel createLabelName = makeJLabel("Name: ", 10, 100, 80, 25);
            JLabel createLabelDescription = makeJLabel("Description: ", 10, 130, 80, 25);
            JLabel createLabelDueDate = makeJLabel("Due Date: ", 10, 160, 80, 25);
            JLabel createLabelPriority = makeJLabel("Priority Level: ", 10, 190, 80, 25);

            addToTodoTaskGui(createLabelName, createLabelDescription, createLabelDueDate, createLabelPriority);

            JButton clickSave = makeButton("saveTask", "Save", 80, 250, 150, 25,
                    JComponent.BOTTOM_ALIGNMENT, "cli.wav");
            panelTask.add(clickSave);

            JButton clickCancel = makeButton("cancelTask", "Cancel", 280, 250, 150, 25,
                    JComponent.BOTTOM_ALIGNMENT, "cli.wav");
            panelTask.add(clickCancel);
        }
    }

    //MODIFIES: panelTask
    // EFFECTS: adds elements to todoTaskGui panel
    private void addToTodoTaskGui(JLabel createLabelName, JLabel createLabelDescription,
                                  JLabel createLabelDueDate, JLabel createLabelPriority) {
        panelTask.add(createLabelName);
        panelTask.add(createLabelDescription);
        panelTask.add(createLabelDueDate);
        panelTask.add(createLabelPriority);
        panelTask.add(textFieldDueDate);

        panelTask.add(textFieldName);
        panelTask.add(textFieldDescription);
        panelTask.add(textFieldDueDate);
        panelTask.add(comboPriority);

    }

    //EFFECTS: creates the text field to input name of task
    private JTextField getTextFieldName() {
        JTextField textFieldName = makeJTextField(1, 100, 100, 150, 25);
        textFieldName.setName("Name");
        return textFieldName;
    }

    //EFFECTS: creates the text field to input description of task
    private JTextField getTextFieldDescription() {
        JTextField textFieldDescription = makeJTextField(1, 100, 130, 200, 25);
        textFieldDescription.setName("Desc");
        return textFieldDescription;
    }

    //EFFECTS: creates the formatted text field to input the due date of task
    private JFormattedTextField getTextFieldDueDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JFormattedTextField textFieldDueDate = new JFormattedTextField(dateFormat);
        textFieldDueDate.setBounds(100, 160, 150, 25);
        textFieldDueDate.setName("DueDate");
        return textFieldDueDate;
    }

    //EFFECTS: creates the JComboBox to choose priority level of task
    private JComboBox<Priority> getPriorityJComboBox() {
        Priority[] options = {Priority.HIGH, Priority.MEDIUM, Priority.LOW};
        JComboBox<Priority> comboPriority = new JComboBox<>(options);
        comboPriority.setSelectedIndex(-1);
        comboPriority.setBounds(100, 190, 150, 25);
        comboPriority.setName("Priority");
        return comboPriority;
    }

    //EFFECTS: assigns the text in the text fields to the task components
    private void assignComponents() {
        if (myTask != null) {
            textFieldName.setText(myTask.getName());
            textFieldDescription.setText(myTask.getDescription());
            textFieldDueDate.setText(myTask.getDueDate().toString());
            comboPriority.setSelectedItem(myTask.getPriority());
        } else {
            textFieldName.setText(null);
            textFieldDescription.setText(null);
            textFieldDueDate.setText(null);
            comboPriority.setSelectedItem(-1);
        }
    }


    //EFFECTS: processes all of the button action listeners
    private void clickActionListener(JButton click, String action, String soundName) {
        click.addActionListener(new AbstractAction(action) {
            @Override
            public void actionPerformed(ActionEvent e) {
                play(soundName);
                if ("newTodo".equals(action)) {
                    newTodo();
                } else if ("retrieveTodo".equals(action)) {
                    retrieveTodo();
                } else if ("saveTodo".equals(action)) {
                    saveTodo();
                } else if ("newTask".equals(action) || "updateTask".equals(action) || "deleteTask".equals(action)
                        || "saveTask".equals(action)) {
                    clickActionListenerCont1(click, action);
                } else {
                    clickActionListenerCont2(click, action);
                }
            }
        });
    }

    private void play(String soundName) {
        if (soundName != null) {
            playSound(soundName);
        }
    }

    //EFFECTS: overloaded method
    private void clickActionListenerCont1(JButton click, String action) {
        click.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (action) {
                    case "newTask":
                        newTask();
                        break;
                    case "updateTask":
                        updateTask();
                        break;
                    case "deleteTask":
                        deleteTask();
                        break;
                    case "saveTask":
                        saveTask();
                        break;
                }
            }
        });
    }

    //EFFECTS: overloaded method
    private void clickActionListenerCont2(JButton click, String action) {
        click.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (action) {
                    case "completeTask":
                        completeTask();
                        break;
                    case "IncompleteTask":
                        undoCompleteTask();
                        break;
                    case "showComplete":
                        showComplete();
                        break;
                    case "cancelTask":
                        cancelTask();
                        break;
                    default:
                        break;
                }
            }
        });
    }


    //EFFECTS: creates a new todolist
    private void newTodo() {
        String myTodoName = "Unknown";
        for (Component component : panelSelectFile.getComponents()) {
            if (component instanceof JTextField) {
                if (component.getName().equals("Name")) {
                    JTextField textFieldName = (JTextField) component;
                    myTodoName = textFieldName.getText();
                }
            }
        }
        if (myTodoName == null || myTodoName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "New Todo List name not entered!");
            return;
        }
        myTodo = new MyTodoList(myTodoName);
        int reply = JOptionPane.showConfirmDialog(null, "Do you want to save this todoList?",
                "Save New Todolist", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            saveTodo();
        }
        fileName = null;
        todoListGui();
    }

    //EFFECTS: retrieves a todolist from a file
    public void retrieveTodo() {
        File file = new File("C:\\Users\\Dalia\\Desktop\\CPSC 210\\Project\\project_b2h3b\\data");
        JFileChooser chooser = new JFileChooser(file);
        int userSelection = chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileName = f.getAbsolutePath();
            reader = new MyTodoJsonReader(fileName);
            try {
                myTodo = reader.readMyToDoList();
                todoListGui();
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "File doesn't exist!");
            }
        }
    }

    //MODIFIES: myTodo
    ///EFFECTS: creates a new task in the given todolist
    public void newTask() {

        todoTaskGui("Create", null);
    }

    //MODIFIES: myTask, myTodo
    //EFFECTS: updates a particular task in the given todolist
    public void updateTask() {
        int selected = list.getSelectedIndex();
        if (selected >= 0) {
            Task task = myTodo.getTaskByIndex(selected + 1);
            todoTaskGui("Update", task);
        }
    }

    //MODIFIES: myTask
    //EFFECTS: sets a task to complete
    public void completeTask() {
        int selected = list.getSelectedIndex();
        if (selected >= 0) {
            Task task = myTodo.getTaskByIndex(selected + 1);
            myTodo.getTodo().get(task.getHashKey()).setComplete(true);
            showCompleted = true;
            playSound("c1.wav");
        }
        todoListGui();
    }

    //MODIFIES: myTask
    //EFFECTS: sets a task to incomplete
    public void undoCompleteTask() {
        int selected = list.getSelectedIndex();
        if (selected >= 0) {
            Task task = myTodo.getTaskByIndex(selected + 1);
            myTodo.getTodo().get(task.getHashKey()).setComplete(false);
            showCompleted = false;
        }
        todoListGui();
    }

    //MODIFIES: myTask, myTodo
    //EFFECTS: mark a task as complete in the given todoList
    public void showComplete() {
        showCompleted = !showCompleted;
        todoListGui();
    }

    //MODIFIES: myTodo
    //EFFECTS: delete a task in the given todolist
    public void deleteTask() {
        int selected = list.getSelectedIndex();
        if (selected >= 0) {
            Task task = myTodo.getTaskByIndex(selected + 1);
            myTodo.getTodo().remove(task.getHashKey());
            playSound("click.wav");
        }
        todoListGui();
    }

    //MODIFIES: myTodo
    //EFFECTS: saves a todolist to a file
    public void saveTodo() {
        File file = new File("C:\\Users\\Dalia\\Desktop\\CPSC 210\\Project\\project_b2h3b\\data");
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Specify a file to save");
        chooser.setCurrentDirectory(file);
        int userSelection = chooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            System.out.println("Save as file: " + f.getAbsolutePath());
            String tempFileName = f.getAbsolutePath();
            writer = new MyTodoJsonWriter(tempFileName);
            try {
                writer.open();
                writer.write(myTodo);
                fileName = tempFileName;
                playSound("c1.wav");
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "Cannot save todolist!");
            } finally {
                writer.close();
                JOptionPane.showMessageDialog(null, "Save successful!");
            }
            todoListGui();
        }
    }

    //MODIFIES: myTask, myTodo
    //EFFECTS: saves changes to a task after update or creates a new task in the given todolist
    public void saveTask() {
        if (validateTask(textFieldName, textFieldDescription, textFieldDueDate, comboPriority)) {
            if (myTask == null) {
                myTask = new Task(textFieldName.getText(), textFieldDescription.getText(),
                        LocalDate.parse(textFieldDueDate.getText()),
                        Priority.valueOf(Objects.requireNonNull(comboPriority.getSelectedItem()).toString()));
            } else {
                myTask.setName(textFieldName.getText());
                myTask.setDescription(textFieldDescription.getText());
                myTask.setDueDate(LocalDate.parse(textFieldDueDate.getText()));
                myTask.setPriority(Priority.valueOf(Objects.requireNonNull(
                        comboPriority.getSelectedItem()).toString()));
            }
            myTodo.getTodo().put(myTask.getHashKey(), myTask);
            todoListGui();
        }
    }

    //EFFECTS: cancels the process of making a new task or updating an existing task
    public void cancelTask() {
        int reply = JOptionPane.showConfirmDialog(null, "Do you want to save?", null,
                JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            saveTask();
        }
        todoListGui();
    }


    //EFFECTS: checks if text has been entered into the text fields in the todoTaskGui, and makes sure the due date
    // entered is a dueDate in the future
    private boolean validateTask(JTextField textFieldName,
                                 JTextField textFieldDescription,
                                 JFormattedTextField textFieldDueDate,
                                 JComboBox<Priority> comboPriority) {
        if (textFieldName.getText().equals("") || textFieldDescription.getText().equals("")
                || textFieldDueDate.getText().equals("") || (comboPriority.getSelectedIndex() == -1)) {
            JOptionPane.showMessageDialog(null, "Please Enter All Data!");
            return false;
        }
        if (LocalDate.parse(textFieldDueDate.getText()).isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(null, "Enter a due date in the future!");
            return false;
        }
        return true;
    }

    //EFFECTS: reads the todolist from a text file into a JList
    private void syncListFromTodo() {
        DefaultListModel<String> dlm = new DefaultListModel<>();
        for (Task task : myTodo.getTodo().values()) {
            if (!task.isComplete() || showCompleted) {
                String display = String.format("%1s DueDate: %10s %s %3s", task.getName(), task.getDueDate(),
                        task.isComplete() ? "  Completed!" : "  Not Completed", task.getPriority());
                dlm.addElement(display);
                list.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            }
        }
        list.setModel(dlm);
    }

    //MODIFIES: myTask
    // EFFECTS: populates myTask with its components
    private void populateFromTask(String actionTitle, Task task) {
        if (task == null || actionTitle.equals("Create")) {
            myTask = null;
            return;
        }
        myTask = task;
    }

    // EFFECTS: create a JFrame
    private JFrame makeFrame(String title, int width, int height, int close) {
        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setLocation(700, 150);
        frame.setMinimumSize(new Dimension(width, height));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(close);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    // EFFECTS: create a JPanel
    private JPanel makePanel(JFrame frame, String layout, String panelLabel,
                             int x, int y, int width, int height) {
        //Create a panel
        JPanel panel = new JPanel();
        panel.setSize(width, height);
        frame.add(panel, layout);
        panel.setLayout(null);
        JLabel label = new JLabel(panelLabel);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Monaco", Font.BOLD, 20));
        panel.add(label);
        return panel;
    }

    //EFFECTS: creates a JButton
    private JButton makeButton(String action, String label, int x, int y, int width, int height,
                               float alignment, String soundName) {
        JButton button = new JButton(label);
        button.setBounds(x, y, width, height);
        button.setAlignmentX(alignment);
        button.setBackground(Color.ORANGE);
        clickActionListener(button, action, soundName);
        return button;
    }

    //EFFECTS: creates a textField
    private JTextField makeJTextField(int columnNumber, int x, int y, int width, int height) {
        JTextField textField = new JTextField(columnNumber);
        textField.setBounds(x, y, width, height);
        return textField;
    }

    //EFFECTS: creates a JLabel
    private JLabel makeJLabel(String title, int x, int y, int width, int height) {
        JLabel label = new JLabel(title);
        label.setBounds(x, y, width, height);
        return label;
    }

    //EFFECTS: creates a JList
    public JList<String> makeList(int rowCount, int x, int y, int width, int height) {
        DefaultListModel<String> dlm = new DefaultListModel<>();
        list = new JList<>(dlm);
        list.setVisibleRowCount(rowCount);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(-1);
        JScrollPane listScroll = new JScrollPane(list);
        listScroll.setPreferredSize(new Dimension(500, 600));
        list.setBounds(x, y, width, height);
        list.setCellRenderer(new CheckBoxListCellRenderer());

        list.setVisible(true);
        return list;
    }

    // EFFECTS: plays a sound file of type wav
    public void playSound(String soundName) {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}
