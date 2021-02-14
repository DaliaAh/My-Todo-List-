package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//Test class for MyTodoList

class MyTodoListTest {

    MyTodoList todo;

    @BeforeEach
    public void setUp() {
        todo = new MyTodoList("Test1");
    }


    @Test
    void addTask() {
        assertEquals(0, todo.countTasks());
        LocalDate dueDate1 = LocalDate.parse("2020-10-14");
        Task task = new Task("name 1", "desc 1", dueDate1, Priority.HIGH);
        assertTrue(todo.addTask(task, task.getHashKey()));
        assertEquals(1, todo.countTasks());
        assertFalse(todo.addTask(task, task.getHashKey()));
        assertEquals(1, todo.countTasks());
    }

    @Test
    void removeTask() {
        assertEquals(0, todo.countTasks());
        LocalDate dueDate1 = LocalDate.parse("2020-10-14");
        Task task1 = new Task("name 1", "desc 1", dueDate1, Priority.HIGH);
        Task task2 = new Task("name 2", "desc 2", dueDate1, Priority.MEDIUM);
        Task task3 = new Task("name 3", "desc 3", dueDate1, Priority.LOW);
        assertTrue(todo.addTask(task1, task1.getHashKey()));
        assertTrue(todo.addTask(task2, task2.getHashKey()));
        assertTrue(todo.addTask(task3, task3.getHashKey()));
        assertEquals(3, todo.countTasks());
        assertTrue(todo.removeTask(task1.getHashKey()));
        assertEquals(2, todo.countTasks());
        assertFalse(todo.removeTask(task1.getHashKey()));
        assertEquals(2, todo.countTasks());
    }

    @Test
    void countTasks() {
        assertEquals(0, todo.countTasks());
        LocalDate dueDate1 = LocalDate.parse("2020-10-14");
        Task task1 = new Task("name 1", "desc 1", dueDate1, Priority.HIGH);
        Task task2 = new Task("name 2", "desc 2", dueDate1, Priority.MEDIUM);
        Task task3 = new Task("name 3", "desc 3", dueDate1, Priority.LOW);
        assertTrue(todo.addTask(task1, task1.getHashKey()));
        assertTrue(todo.addTask(task2, task2.getHashKey()));
        assertTrue(todo.addTask(task3, task3.getHashKey()));
        assertEquals(3, todo.countTasks());
    }

    @Test
    void countTasksComplete() {
        assertEquals(0, todo.countTasks());
        LocalDate dueDate1 = LocalDate.parse("2020-10-14");
        Task task1 = new Task("name 1", "desc 1", dueDate1, Priority.HIGH);
        task1.setComplete(true);
        Task task2 = new Task("name 2", "desc 2", dueDate1, Priority.MEDIUM);
        Task task3 = new Task("name 3", "desc 3", dueDate1, Priority.LOW);
        assertTrue(todo.addTask(task1, task1.getHashKey()));
        assertTrue(todo.addTask(task2, task2.getHashKey()));
        assertTrue(todo.addTask(task3, task3.getHashKey()));
        assertEquals(1, todo.countTasksComplete());
    }

    @Test
    void countTasksInComplete() {
        assertEquals(0, todo.countTasks());
        LocalDate dueDate1 = LocalDate.parse("2020-10-14");
        Task task1 = new Task("name 1", "desc 1", dueDate1, Priority.HIGH);
        task1.setComplete(true);
        Task task2 = new Task("name 2", "desc 2", dueDate1, Priority.MEDIUM);
        Task task3 = new Task("name 3", "desc 3", dueDate1, Priority.LOW);
        assertTrue(todo.addTask(task1, task1.getHashKey()));
        assertTrue(todo.addTask(task2, task2.getHashKey()));
        assertTrue(todo.addTask(task3, task3.getHashKey()));
        assertEquals(2, todo.countTasksInComplete());
    }

    @Test
    public void testGetName() {
        String name = todo.getName();
        assertEquals(name, "Test1");
    }

    @Test
    public void testSetName() {
        String name = todo.setName("Test2");
        assertEquals(name, todo.getName());
    }


    @Test
    void testToString() {
        LocalDate dueDate1 = LocalDate.parse("2020-10-14");
        Task task1 = new Task("name 1", "desc 1", dueDate1, Priority.HIGH);
        MyTodoList myTodoList = new MyTodoList("Name");
        myTodoList.addTask(task1, task1.getHashKey());
        String test = "MyTodoList{" + "name='" + myTodoList.getName() + '\'' + ", todo=" + myTodoList.getTodo().toString() + '}';
        assertEquals(test, myTodoList.toString());
    }


    @Test
    void getTask() {
        Task task1 = new Task("Task1", "no description",
                LocalDate.parse("2020-12-12"), Priority.HIGH);
        Task task2 = new Task("Task2", "no description",
                LocalDate.parse("2020-12-13"), Priority.MEDIUM);
        Task task3 = new Task("Task3", "no description",
                LocalDate.parse("2020-12-14"), Priority.LOW);

        todo.addTask(task1, task1.getHashKey());
        todo.addTask(task2, task2.getHashKey());

        assertTrue(todo.getTodo().containsKey(task1.getHashKey()));
        assertTrue(todo.getTodo().containsKey(task2.getHashKey()));
        assertFalse(todo.getTodo().containsKey(task3.getHashKey()));
        assertEquals(task1,todo.getTask(task1.getHashKey()));
        assertEquals(task2,todo.getTask(task2.getHashKey()));
        assertNotEquals(task3,todo.getTask(task3.getHashKey()));
    }

    @Test
    void testGetTaskByIndex() {
        Task task1 = new Task("Task1", "no description",
                LocalDate.parse("2020-12-12"), Priority.HIGH);
        Task task2 = new Task("Task2", "no description",
                LocalDate.parse("2020-12-13"), Priority.MEDIUM);
        Task task3 = new Task("Task3", "no description",
                LocalDate.parse("2020-12-14"), Priority.LOW);

        todo.addTask(task1, task1.getHashKey());
        todo.addTask(task2, task2.getHashKey());
        todo.addTask(task3, task3.getHashKey());

        ArrayList <Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        assertEquals(tasks.get(2), todo.getTaskByIndex(3));
        assertEquals(tasks.get(1), todo.getTaskByIndex(2));
        assertEquals(tasks.get(0), todo.getTaskByIndex(1));

    }

    @Test
    void testEqualsFalseInstance1() {
        Object obj = new Object();
        assertNotEquals(todo, obj);
        assertNotEquals(todo, null);
    }

    @Test
    void testEqualsFalseInstance2() {
        LocalDate dueDate1 = LocalDate.parse("2020-10-14");
        Task task2 = new Task("name 2", "desc 2", dueDate1, Priority.MEDIUM);
        Task task3 = new Task("name 3", "desc 3", dueDate1, Priority.LOW);
        todo.addTask(task2, task2.getHashKey());
        todo.addTask(task3, task3.getHashKey());

        MyTodoList myOtherTodo = new MyTodoList("MyOtherTodo");
        assertNotEquals(todo, myOtherTodo);

        myOtherTodo = new MyTodoList("Test1");
        assertNotEquals(todo, myOtherTodo);

        myOtherTodo = new MyTodoList("Test1");
        dueDate1 = LocalDate.parse("2020-10-15");
        Task task4 = new Task("name 4", "desc 2", dueDate1, Priority.MEDIUM);
        Task task5 = new Task("name 5", "desc 3", dueDate1, Priority.LOW);
        myOtherTodo.addTask(task4, task4.getHashKey());
        myOtherTodo.addTask(task5, task5.getHashKey());
        assertNotEquals(todo, myOtherTodo);

        myOtherTodo = new MyTodoList("Test1");
        dueDate1 = LocalDate.parse("2020-10-14");
        task4 = new Task("name 2", "desc 2", dueDate1, Priority.MEDIUM);
        task5 = new Task("name 3", "desc 3", dueDate1, Priority.LOW);
        task4.setComplete(true);
        myOtherTodo.addTask(task4, task4.getHashKey());
        myOtherTodo.addTask(task5, task5.getHashKey());
        assertNotEquals(todo, myOtherTodo);
    }

    @Test
    void testEqualsTrue1() {
        LocalDate dueDate1 = LocalDate.parse("2020-10-14");
        Task task2 = new Task("name 2", "desc 2", dueDate1, Priority.MEDIUM);
        Task task3 = new Task("name 3", "desc 3", dueDate1, Priority.LOW);
        todo.addTask(task2, task2.getHashKey());
        todo.addTask(task3, task3.getHashKey());
        MyTodoList myOtherTodo = new MyTodoList("Test1");
        Task task4 = new Task("name 2", "desc 2", dueDate1, Priority.MEDIUM);
        Task task5 = new Task("name 3", "desc 3", dueDate1, Priority.LOW);
        myOtherTodo.addTask(task4, task4.getHashKey());
        myOtherTodo.addTask(task5, task5.getHashKey());
        assertEquals(todo, myOtherTodo);
    }

    @Test
    void testEqualsTrue2(){
        assertEquals(todo,todo);
    }

    @Test
    void testToString1() {
        assertEquals("MyTodoList{name='Test1', todo={}}", todo.toString());
    }

    @Test
    void testToJson() {
        LocalDate dueDate1 = LocalDate.parse("2020-10-14");
        Task task2 = new Task("name 2", "desc 2", dueDate1, Priority.MEDIUM);
        Task task3 = new Task("name 3", "desc 3", dueDate1, Priority.LOW);
        todo.addTask(task2, task2.getHashKey());
        todo.addTask(task3, task3.getHashKey());
        assertNotNull(todo.toJson());
    }

    @Test
    void testFromJson() {
        LocalDate dueDate1 = LocalDate.parse("2020-10-14");
        Task task2 = new Task("name 2", "desc 2", dueDate1, Priority.MEDIUM);
        Task task3 = new Task("name 3", "desc 3", dueDate1, Priority.LOW);
        todo.addTask(task2, task2.getHashKey());
        todo.addTask(task3, task3.getHashKey());
        MyTodoList newTodo = MyTodoList.fromJson(todo.toJson());
        assertEquals(todo, newTodo);
    }
}

