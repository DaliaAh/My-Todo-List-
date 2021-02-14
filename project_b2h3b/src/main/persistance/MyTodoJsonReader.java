package persistance;

import model.MyTodoList;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads JSON representation of file to todolist

public class MyTodoJsonReader {
    private String source;

    //constructor
    //EFFECTS: constructs reader to read from source file
    public MyTodoJsonReader(String source) {
        this.source = source;
    }

    //MODIFIES: this
    //EFFECTS: reads to-do list from file and returns it;
    //throws IOException if error occurs while reading the file from data
    public MyTodoList readMyToDoList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMyTodoList(jsonObject);
    }

    //MODIFIES: this
    // EFFECTS: reads task as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses to-do list from JSON object and returns it
    private MyTodoList parseMyTodoList(JSONObject jo) {
        return MyTodoList.fromJson(jo);
    }
}
