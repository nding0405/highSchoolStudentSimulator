package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private Student testStudent1;
    private Student testStudent2;
    private Student testStudent3;
    private Student testStudent4;
    private Activities a1;
    private Activities a2;
    private Activities a3;
    private Activities a4;
    private Activities a5;

    @BeforeEach
    void setupStudent() {
        testStudent1 = new Student("I+A");
        testStudent1.setChr(false);
        testStudent1.setPreference(false);
        testStudent2 = new Student("I+S");
        testStudent2.setChr(false);
        testStudent2.setPreference(true);
        testStudent3 = new Student("E+A");
        testStudent3.setChr(true);
        testStudent3.setPreference(false);
        testStudent4 = new Student("E+S");
        testStudent4.setChr(true);
        testStudent4.setPreference(true);
        a1 = new Activities("a1",100, true,true);
        a2 = new Activities("a2",10,true,false);
        a3 = new Activities("a3",1000,false,true);
        a4 = new Activities("a4",100,false,false);
        a5 = new Activities("a5",1000,true,false);
    }

    @Test
    void testConstructor() {
        ArrayList<Activities> testList = new ArrayList<>();
        assertEquals(0,testStudent1.getPressure());
        assertEquals(0,testStudent1.getKnowledge());
        assertEquals("I+A",testStudent1.getName());
        assertEquals(testList,testStudent1.getSchedule());
        assertFalse(testStudent1.getChr());
        assertFalse(testStudent1.getPreference());
    }

    @Test
    void testAddActivitiesA1() {
        wrapUp(testStudent1, a1, 100, (int)(100*1.2), (int)(100*0.8));
        wrapUp(testStudent2, a1, 100, (int)(100*0.9), (int)(1.1*100));
        wrapUp(testStudent3, a1, 100, (int)(100*1.2), (int)(100*0.8));
        wrapUp(testStudent4, a1, 100, (int)(0.9*100), (int)(1.1*100));
    }

    @Test
    void testAddActivitiesA2() {
        wrapUp(testStudent1, a2, 10, (int)(10*0.9), (int)(10*1.1));
        wrapUp(testStudent2, a2, 10, (int)(10*1.2), (int)(10*0.8));
        wrapUp(testStudent3, a2, 10, (int)(10*0.9), (int)(10*1.1));
        wrapUp(testStudent4, a2, 10, (int)(10*1.2), (int)(10*0.8));
    }

    @Test
    void testAddActivitiesA3() {
        wrapUp(testStudent1, a3, 1000, -(int)(0.5*1000), -(int)(0.2*1000));
        wrapUp(testStudent2, a3, 1000, -(int)(0.5*1000), -(int)(0.2*1000));
        wrapUp(testStudent3, a3, 1000, -(int)(1.1*1000), -(int)(0.2*1000));
        wrapUp(testStudent4, a3, 1000, -(int)(1.1*1000), -(int)(0.2*1000));
    }

    @Test
    void testAddActivitiesA4() {
        wrapUp(testStudent1, a4, 100, -(int)(1.1*100), -(int)(0.2*100));
        wrapUp(testStudent2, a4, 100, -(int)(1.1*100), -(int)(0.2*100));
        wrapUp(testStudent3, a4, 100, -(int)(0.5*100), -(int)(0.2*100));
        wrapUp(testStudent4, a4, 100, -(int)(0.5*100), -(int)(0.2*100));
    }

    @Test
    void testShowSchedule() {
        testStudent1.addActivity(a1);
        List<String> testList1 = new ArrayList<>();
        testList1.add("a1 100");
        assertEquals(testStudent1.showSchedule(),testList1);

        testStudent1.addActivity(a2);
        List<String> testList2 = new ArrayList<>();
        testList2.add("a1 100");
        testList2.add("a2 10");
        assertEquals(testStudent1.showSchedule(),testList2);
    }


    @Test
    void testdetectEnding() {
        testStudent1.addActivity(a1);
        testStudent1.addActivity(a3);
        assertTrue(testStudent1.detectEnding());

        testStudent2.addActivity(a5);
        assertTrue(testStudent2.detectEnding());

        testStudent3.addActivity(a1);
        assertFalse(testStudent3.detectEnding());
    }

    void wrapUp(Student s,Activities a, int eptTime, int eptPressure, int eptKnowledge) {
        s.addActivity(a);
        assertEquals(s.getTime(),eptTime);
        assertEquals(s.getPressure(),eptPressure);
        assertEquals(s.getKnowledge(), eptKnowledge);
        List<Activities> exptList = s.getSchedule();
        assertEquals(exptList.get(0) ,a);
    }
}


