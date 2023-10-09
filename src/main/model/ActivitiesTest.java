package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActivitiesTest {
    private Activities a1;
    private Activities a2;
    private Activities a3;
    private Activities a4;
    private Activities a5;
    private Activities a6;

    @BeforeEach
    void setupStudent() {
        a1 = new Activities("a1",100, true,true);
        a2 = new Activities("a2",10,true,false);
        a3 = new Activities("a3",1000,false,true);
        a4 = new Activities("Mandarin",100,false,false);
        a5 = new Activities("Math",1000,true,true);
        a6 = new Activities("English",1000,true,false);
    }

    @Test
    void testConstructor() {
        assertEquals("a1", a1.getName());
        assertEquals(100, a1.getTime());
        assertTrue(a1.getcourseOrPlay());
        assertTrue(a1.getActivityType());
    }

    @Test
    void testDetectValidActivity() {
        assertTrue(a1.detectValidActivity("English"));
        assertFalse(a2.detectValidActivity("Sthelse"));
    }

    @Test
    void testFindActivity() {
        a1.findActivity("English", 10);
        assertEquals(a1.getName(), "English");
        assertEquals(a1.getTime(), 10);
        assertFalse(a1.getActivityType());
        assertTrue(a1.getcourseOrPlay());
        a2.findActivity("Physics", 100);
        assertEquals(a2.getName(), "Physics");
        assertEquals(a2.getTime(), 100);
        assertTrue(a2.getActivityType());
        assertTrue(a2.getcourseOrPlay());
    }


    @Test//passed finished
    void testActInSelection() {
        assertTrue(a1.actInSelection("a1", "a2", "a3"));
        assertFalse(a1.actInSelection("a", "a2", "a3"));
        assertTrue(a4.actInSelection("a", "a2", "a3"));
        assertTrue(a5.actInSelection("a", "a2", "a3"));
        assertTrue(a6.actInSelection("a", "a2", "a3"));
    }

    @Test//passed finished
    void testgetCorrespondingIndex() {
        assertEquals(1,a1.getCorrespondingIndex("a1", "a2", "a3"));
        assertEquals(2,a2.getCorrespondingIndex("a1", "a2", "a3"));
        assertEquals(3,a3.getCorrespondingIndex("a1", "a2", "a3"));
        assertEquals(4,a4.getCorrespondingIndex("a1", "a2", "a3"));
        assertEquals(5,a5.getCorrespondingIndex("a1", "a2", "a3"));
        assertEquals(6,a6.getCorrespondingIndex("a1", "a2", "a3"));
    }
}
