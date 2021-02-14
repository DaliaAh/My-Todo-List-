package persistance;

import model.MyTodoList;
import model.Priority;
import model.Task;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

//Test class for MyTodoJsonWriter

class MyTodoJsonReaderTest  {

    @Test
    void testReadMyToDoListFileNonExistent() {
        MyTodoJsonReader reader = new MyTodoJsonReader("./data/noSuchFile.json");
        try {
            MyTodoList todoList = reader.readMyToDoList();
            assertNull(todoList);
            fail("IOException expected");
        } catch (IOException e) {
            //expected
        }
    }

    @Test
    void testReadMyToDoListEmpty() {
        MyTodoJsonReader reader = new MyTodoJsonReader("./data/testWriterEmptyToDoList.json");
        try {
            MyTodoList todoList = reader.readMyToDoList();
            assertEquals("My TodoList", todoList.getName());
            assertEquals(0, todoList.countTasks());
        } catch (IOException e) {
            fail("File is empty!");
        }
    }


    @Test
    void testReadMyToDoListNormal() {
        MyTodoJsonReader reader = new MyTodoJsonReader("./data/testWriterGeneralToDoList.json");
        try {
            MyTodoList todoList = reader.readMyToDoList();
            assertEquals("My TodoList", todoList.getName());
            assertEquals(2, todoList.countTasks());
            Task t1 = new Task(1277573242, "Task1", "This is my first task", LocalDate.parse("2020-10-24"),
                    LocalDate.parse("2020-12-12"), Priority.HIGH, false);
            Task t2 = new Task (1016601165, "Task2", "This is my second task", LocalDate.parse("2020-10-24"),
                    LocalDate.parse("2020-12-13"), Priority.MEDIUM,  false);
            todoList.addTask(t1, t1.getHashKey());
            todoList.addTask(t2, t2.getHashKey());
            assertEquals(t1, todoList.getTaskByIndex(3));
            assertEquals(t2, todoList.getTaskByIndex(4));
        } catch (IOException e) {
            fail("Couldn't read from file");

        }
    }
}

