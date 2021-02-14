package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

//Test class for Task
public class TaskTest {
    Task myTask;

    @BeforeEach
    public void setUp() {
        myTask = new Task("Task1", "no description", LocalDate.parse("2020-10-14"), Priority.HIGH);
    }

    @Test
    void testToString() {
        String expected = "Task{"
                + "hashKey=" + myTask.getHashKey()
                + ", name='" + myTask.getName() + '\''
                + ", description='" + myTask.getDescription() + '\''
                + ", createDate=" + myTask.getCreateDate()
                + ", dueDate=" + myTask.getDueDate()
                + ", priority=" + myTask.getPriority()
                + ", complete=" + myTask.isComplete()
                + '}';
        assertEquals(expected, myTask.toString());
    }

    @Test
    void testEqualsFalseInstance1() {
        Object obj = new Object();
        assertNotEquals(myTask, obj);
        assertNotEquals(myTask, null);
    }

    @Test
    void testEqualsFalseInstance2() {
        Task myOtherTask = new Task("Task1", "no description", LocalDate.parse("2020-10-14"),
                Priority.MEDIUM);
        assertNotEquals(myTask, myOtherTask);
        myOtherTask = new Task("Task1", "no description", LocalDate.parse("2020-10-13"),
                Priority.HIGH);
        assertNotEquals(myTask, myOtherTask);
        myOtherTask = new Task("Task1", "no description1", LocalDate.parse("2020-10-14"),
                Priority.HIGH);
        assertNotEquals(myTask, myOtherTask);
        myOtherTask = new Task("Task2", "no description", LocalDate.parse("2020-10-14"),
                Priority.HIGH);
        assertNotEquals(myTask, myOtherTask);
        myOtherTask = new Task("Task1", "no description", LocalDate.parse("2020-10-14"),
                Priority.HIGH);
        myOtherTask.setComplete(true);
        assertNotEquals(myTask, myOtherTask);
        myOtherTask = new Task("Task1", "no description", LocalDate.parse("2020-10-14"),
                Priority.HIGH);
        myOtherTask.setCreateDate(LocalDate.parse("2020-09-14"));
        assertNotEquals(myTask, myOtherTask);
    }

    @Test
    void testEqualsTrue1() {
        Task myOtherTask = new Task("Task1", "no description", LocalDate.parse("2020-10-14"),
                Priority.HIGH);
        myTask.setCreateDate(LocalDate.parse("2020-09-14"));
        myOtherTask.setCreateDate(LocalDate.parse("2020-09-14"));
        assertEquals(myTask, myOtherTask);
    }

    @Test
    void testSetHashKey() {
        myTask.setHashKey(hashCode());
        assertEquals(hashCode(), myTask.getHashKey());
    }

    @Test
    void testSetName() {
        myTask.setName("Test");
        assertEquals("Test", myTask.getName());
    }

    @Test
    void testSetDescription() {
        myTask.setDescription("Testing this method");
        assertEquals("Testing this method", myTask.getDescription());
    }

    @Test
    void testSetCreateDate() {
        myTask.setCreateDate(LocalDate.now());
        assertEquals(LocalDate.now(), myTask.getCreateDate());
    }

    @Test
    void testSetDueDate() {
        myTask.setDueDate(LocalDate.parse("2020-12-12"));
        assertEquals(LocalDate.parse("2020-12-12"), myTask.getDueDate());
    }

    @Test
    void testSetPriority() {
        myTask.setPriority(Priority.HIGH);
        assertEquals(Priority.HIGH, myTask.getPriority());
    }

    @Test
    void testToStringTableHeadings() {
        String expected =
                String.format("%1$" + 4 + "s", "|")
                        + String.format("%1$" + 20 + "s", "Name") + "|"
                        + String.format("%1$" + 30 + "s", "Description") + "|"
                        + String.format("%1$" + 12 + "s", "Create Date") + "|"
                        + String.format("%1$" + 20 + "s", "Due Date") + "|"
                        + String.format("%1$" + 20 + "s", "Priority") + "|"
                        + String.format("%1$" + 20 + "s", "Complete") + "|";
        assertEquals(expected, myTask.toStringTable(0));
    }

    @Test
    void testToStringTableNoHeadings() {
        int record = 1;
        String expected = String.format("%1$" + 4 + "s", record + "|")
                + String.format("%1$" + 20 + "s", myTask.getName()) + "|"
                + String.format("%1$" + 30 + "s", myTask.getDescription()) + "|"
                + String.format("%1$" + 12 + "s", myTask.getCreateDate()) + "|"
                + String.format("%1$" + 20 + "s", myTask.getDueDate()) + "|"
                + String.format("%1$" + 20 + "s", myTask.getPriority().toString()) + "|"
                + String.format("%1$" + 20 + "s", (myTask.isComplete() ? "Y" : "N"))
                + " |";
        assertEquals(expected, myTask.toStringTable(record));
    }
    @Test
    void testToAndFromJson() {
        Task newTask = Task.fromJson(myTask.toJson());
        assertEquals(myTask, newTask);
    }
}


