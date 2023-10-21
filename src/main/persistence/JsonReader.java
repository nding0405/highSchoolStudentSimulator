package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Activities;
import model.Knowledge;
import model.Student;
import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Student from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Student read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStudent(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Student from JSON object and returns it
    private Student parseStudent(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int time = jsonObject.getInt("time");
        int pressure = jsonObject.getInt("pressure");
        boolean chrType = jsonObject.getBoolean("chrType");
        boolean preference = jsonObject.getBoolean("preference");
        boolean loveFineArt = jsonObject.getBoolean("loveFineArt");
        boolean scienceOrArtForExam = jsonObject.getBoolean("scienceOrArtForExam");
        String subjectSelectionOne = jsonObject.getString("subjectSelectionOne");
        String subjectSelectionTwo = jsonObject.getString("subjectSelectionTwo");
        String subjectSelectionThree = jsonObject.getString("subjectSelectionThree");
        Student stu = new Student(name);
        stu.setTime(time);
        stu.setPressure(pressure);
        stu.setChr(chrType);
        stu.setPreference(preference);
        stu.setFineart(loveFineArt);
        stu.setScienceOrArtForExam(scienceOrArtForExam);
        stu.setSubjectSelectionOne(subjectSelectionOne);
        stu.setSubjectSelectionTwo(subjectSelectionTwo);
        stu.setSubjectSelectionThree(subjectSelectionThree);
        addschedule(stu, jsonObject);
        addknowledge(stu, jsonObject);
        return stu;
    }

    // MODIFIES: Student
    // EFFECTS: set up the knowledge and add it to the student's field.
    private void addknowledge(Student stu, JSONObject jsonObject) {
        Knowledge k = new Knowledge();
        JSONObject knowledgeData = jsonObject.getJSONObject("knowledge");
        int mandarinKnowledge = knowledgeData.getInt("mandarinKnowledge");
        int mathKnowledge = knowledgeData.getInt("mathKnowledge");
        int englishKnowledge = knowledgeData.getInt("englishKnowledge");
        int selectionOneKnowledge = knowledgeData.getInt("selectionOneKnowledge");
        int selectionTwoKnowledge = knowledgeData.getInt("selectionTwoKnowledge");
        int selectionThreeKnowledge = knowledgeData.getInt("selectionThreeKnowledge");
        k.setMandarinKnowledge(mandarinKnowledge);
        k.setMathKnowledge(mathKnowledge);
        k.setEnglishKnowledge(englishKnowledge);
        k.setS1Knowledge(selectionOneKnowledge);
        k.setS2Knowledge(selectionTwoKnowledge);
        k.setS3Knowledge(selectionThreeKnowledge);
        stu.setKnowledge(k);
    }

    // MODIFIES: Student
    // EFFECTS: parses the schedule from JSON object and adds them to student
    private void addschedule(Student stu, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("schedule");
        for (Object json : jsonArray) {
            JSONObject nextActivities = (JSONObject) json;
            addActivities(stu, nextActivities);
        }
    }

    // MODIFIES: Student
    // EFFECTS: parses activities from JSON object and adds it to student
    private void addActivities(Student stu, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int time = jsonObject.getInt("time");
        boolean courseOrPlay = jsonObject.getBoolean("courseOrPlay");
        boolean activityType = jsonObject.getBoolean("activityType");
        Activities a = new Activities(name, time, courseOrPlay, activityType);
        stu.addActivity(a);
    }
}
