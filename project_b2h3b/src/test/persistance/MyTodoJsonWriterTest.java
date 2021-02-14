package persistance;

import model.MyTodoList;
import model.Priority;
import model.Task;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

//Test class for MyTodoJsonWriter

class MyTodoJsonWriterTest {

    @Test
    void testWriterFileInvalid() {
        try {
            MyTodoList todoList = new MyTodoList("My TodoList");
            assertNull(todoList);
            MyTodoJsonWriter writer = new MyTodoJsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }

    }

    @Test
    void testWriterFileEmpty() {
        try {
            MyTodoList todoList = new MyTodoList("My TodoList");
            MyTodoJsonWriter writer = new MyTodoJsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(todoList);
            writer.close();

            MyTodoJsonReader reader = new MyTodoJsonReader("./data/testWriterEmptyWorkroom.json");
            todoList = reader.readMyToDoList();
            assertEquals("My TodoList", todoList.getName());
            assertEquals(0, todoList.countTasks());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMyTodoList() {
        try {
            MyTodoList todoList = new MyTodoList("My TodoList");
            Task t1 = new Task("Task1", "This is my first task", LocalDate.parse("2020-12-12"),
                    Priority.HIGH);
            Task t2 = new Task("Task2", "This is my second task", LocalDate.parse("2020-12-13"),
                    Priority.MEDIUM);
            todoList.addTask(t1, t1.getHashKey());
            todoList.addTask(t2, t2.getHashKey());
            MyTodoJsonWriter writer = new MyTodoJsonWriter("./data/testWriterGeneralToDoList.json");
            writer.open();
            writer.write(todoList);
            writer.close();

            MyTodoJsonReader reader = new MyTodoJsonReader("./data/testWriterGeneralToDoList.json");
            todoList = reader.readMyToDoList();
            assertEquals("My TodoList", todoList.getName());
            assertEquals(2, todoList.countTasks());
            Task task1 = new Task (1277573242,"Task1","This is my first task",
                    LocalDate.parse("2020-10-24"), LocalDate.parse("2020-12-12"), Priority.HIGH, false);
            Task task2 = new Task ( 1016601165,"Task2","This is my second task",
                    LocalDate.parse("2020-10-24"), LocalDate.parse("2020-12-13"), Priority.MEDIUM, false );
            todoList.addTask(task1,task1.getHashKey());
            todoList.addTask(task2,task2.getHashKey());
            assertEquals(task1,todoList.getTaskByIndex(3));
            assertEquals(task2,todoList.getTaskByIndex(4));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}