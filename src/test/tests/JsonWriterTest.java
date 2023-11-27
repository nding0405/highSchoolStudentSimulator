package tests;

import model.Activities;
import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {
    private Student testStudent1;
    private Activities a1;
    private static final double courseUnFitpressureIndex = 1.3;

    @BeforeEach
    void setupStudent() {
        testStudent1 = new Student("I+A");
        testStudent1.setChr(false);
        testStudent1.setPreference(false);
        testStudent1.setFineart(false);
        a1 = new Activities("a1",100, true,true);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterInitialStudent() {
        try {
            String gender = "mom";
            JsonWriter writer = new JsonWriter("./data/testWriterInitialStudent.json");
            writer.open();
            writer.write(testStudent1, gender);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterInitialStudent.json");
            testStudent1 = reader.read();
            gender = reader.readGender();
            testFields(gender);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    void testFields(String gender) {
        ArrayList<Activities> testList = new ArrayList<>();
        assertEquals(0, testStudent1.getKnowledge().getS1Knowledge());
        assertEquals(0, testStudent1.getKnowledge().getS2Knowledge());
        assertEquals(0, testStudent1.getKnowledge().getS3Knowledge());
        assertEquals(0,testStudent1.getTime());
        assertEquals(0,testStudent1.getPressure());
        assertEquals("I+A",testStudent1.getName());
        assertEquals(testList,testStudent1.getSchedule());
        assertFalse(testStudent1.getChr());
        assertFalse(testStudent1.getPreference());
        assertFalse(testStudent1.getLoveFineArt());
        assertEquals("A", testStudent1.getSubjectSelectionOne());
        assertEquals("B", testStudent1.getSubjectSelectionTwo());
        assertEquals("C", testStudent1.getSubjectSelectionThree());
        assertEquals("mom", gender);
    }

    @Test
    void testWriterGeneralStudent() {
        try {
            testStudent1.addActivity(a1);
            String gender = "dad";
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStudent.json");
            writer.open();
            writer.write(testStudent1, gender);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralStudent.json");
            testStudent1 = reader.read();
            gender = reader.readGender();
            assertGeneralStudent(gender);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    void assertGeneralStudent(String gender) {
        assertEquals(testStudent1.getTime(),100);//test time
        assertEquals(testStudent1.getPressure(),(int)(100 * courseUnFitpressureIndex));//test pressure
        assertEquals(0, testStudent1.getKnowledge().getS1Knowledge());//test selection1 knowledge
        assertEquals(0, testStudent1.getKnowledge().getS2Knowledge());//test selection2 knowledge
        assertEquals(0, testStudent1.getKnowledge().getS3Knowledge());//test selection3 knowledge
        assertEquals(0, testStudent1.getKnowledge().getMandarinKnowledge());//test Mandarin knowledge
        assertEquals(0, testStudent1.getKnowledge().getMathKnowledge());//test Math knowledge
        assertEquals(0, testStudent1.getKnowledge().getEnglishKnowledge());//test English knowledge
        assertEquals("I+A",testStudent1.getName());//test name
        assertEquals(1,testStudent1.getSchedule().size());//test Schedule size
        assertFalse(testStudent1.getChr());
        assertFalse(testStudent1.getPreference());
        assertFalse(testStudent1.getLoveFineArt());
        assertEquals("A", testStudent1.getSubjectSelectionOne());
        assertEquals("B", testStudent1.getSubjectSelectionTwo());
        assertEquals("C", testStudent1.getSubjectSelectionThree());
        assertEquals("dad", gender);
        testAcitivityIdentical();
    }

    void testAcitivityIdentical() {
        Activities readFrom = testStudent1.getSchedule().get(0);
        assertEquals(100,readFrom.getTime());
        assertTrue(readFrom.getcourseOrPlay());
        assertTrue(readFrom.getActivityType());
        assertTrue(readFrom.getActivityType());
        assertEquals("a1",readFrom.getName());
    }
}
