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
    private static double courseFitpressureIndex = 0.9;
    private static double courseUnFitpressureIndex = 1.3;
    private static double courseFitKnowledgeIndex = 1.08;
    private static double courseUnFitKnowledgeIndex = 0.8;
    private static double playFitPressureIndex = 1.0;
    private static double playUnFitPressureIndex = 0.4;
    private static double loseKnowledgeWhenPlayIndex = 0.25;

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
        a1 = new Activities("a1",100, true,true);//理科课
        a2 = new Activities("a2",10,true,false);//文科课
        a3 = new Activities("a3",1000,false,true);//户外活动
        a4 = new Activities("a4",100,false,false);//室内活动
        a5 = new Activities("a5",1000,true,false);//文科课
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
        List<Activities> exptList = new ArrayList<>();
        exptList.add(a1);
        wrapUp(testStudent1, a1, 100, (int)(100*courseUnFitpressureIndex), (int)(100*courseUnFitKnowledgeIndex));
        assertEquals(exptList, testStudent1.getSchedule());
        wrapUp(testStudent2, a1, 100, (int)(100*courseFitpressureIndex), (int)(100*courseFitKnowledgeIndex));
        assertEquals(exptList, testStudent2.getSchedule());
        wrapUp(testStudent3, a1, 100, (int)(100*courseUnFitpressureIndex), (int)(100*courseUnFitKnowledgeIndex));
        assertEquals(exptList, testStudent3.getSchedule());
        wrapUp(testStudent4, a1, 100, (int)(100*courseFitpressureIndex), (int)(100*courseFitKnowledgeIndex));
        assertEquals(exptList, testStudent4.getSchedule());
    }

    @Test
    void testAddActivitiesA2() {
        List<Activities> exptList = new ArrayList<>();
        exptList.add(a2);
        wrapUp(testStudent1, a2, 10, (int)(10*courseFitpressureIndex), (int)(10*courseFitKnowledgeIndex));
        assertEquals(exptList, testStudent1.getSchedule());
        wrapUp(testStudent2, a2, 10, (int)(10*courseUnFitpressureIndex), (int)(10*courseUnFitKnowledgeIndex));
        assertEquals(exptList, testStudent2.getSchedule());
        wrapUp(testStudent3, a2, 10, (int)(10*courseFitpressureIndex), (int)(10*courseFitKnowledgeIndex));
        assertEquals(exptList, testStudent3.getSchedule());
        wrapUp(testStudent4, a2, 10, (int)(10*courseUnFitpressureIndex), (int)(10*courseUnFitKnowledgeIndex));
        assertEquals(exptList, testStudent4.getSchedule());
    }

    @Test
    void testAddActivitiesA3() {
        List<Activities> exptList = new ArrayList<>();
        exptList.add(a3);
        wrapUp(testStudent1, a3, 1000, -(int)(playUnFitPressureIndex*1000), -(int)(loseKnowledgeWhenPlayIndex*1000));
        assertEquals(exptList, testStudent1.getSchedule());
        wrapUp(testStudent2, a3, 1000, -(int)(playUnFitPressureIndex*1000), -(int)(loseKnowledgeWhenPlayIndex*1000));
        assertEquals(exptList, testStudent2.getSchedule());
        wrapUp(testStudent3, a3, 1000, -(int)(playFitPressureIndex*1000), -(int)(loseKnowledgeWhenPlayIndex*1000));
        assertEquals(exptList, testStudent3.getSchedule());
        wrapUp(testStudent4, a3, 1000, -(int)(playFitPressureIndex*1000), -(int)(loseKnowledgeWhenPlayIndex*1000));
        assertEquals(exptList, testStudent4.getSchedule());
    }

    @Test
    void testAddActivitiesA4() {
        List<Activities> exptList = new ArrayList<>();
        exptList.add(a4);
        wrapUp(testStudent1, a4, 100, -(int)(playFitPressureIndex*100), -(int)(loseKnowledgeWhenPlayIndex*100));
        assertEquals(exptList, testStudent1.getSchedule());
        wrapUp(testStudent2, a4, 100, -(int)(playFitPressureIndex*100), -(int)(loseKnowledgeWhenPlayIndex*100));
        assertEquals(exptList, testStudent2.getSchedule());
        wrapUp(testStudent3, a4, 100, -(int)(playUnFitPressureIndex*100), -(int)(loseKnowledgeWhenPlayIndex*100));
        assertEquals(exptList, testStudent3.getSchedule());
        wrapUp(testStudent4, a4, 100, -(int)(playUnFitPressureIndex*100), -(int)(loseKnowledgeWhenPlayIndex*100));
        assertEquals(exptList, testStudent4.getSchedule());
    }

    @Test
    void testShowScheduleOneActivity() {
        testStudent1.addActivity(a1);
        List<String> testList1 = new ArrayList<>();
        testList1.add("a1 100");
        assertEquals(testStudent1.showSchedule(),testList1);
        testStudent2.addActivity(a2);
        List<String> testList2 = new ArrayList<>();
        testList2.add("a2 10");
        assertEquals(testStudent2.showSchedule(),testList2);
    }

    @Test
    void testShowScheduleMultiActivities() {
        testStudent1.addActivity(a1);
        testStudent1.addActivity(a2);
        List<String> testList1 = new ArrayList<>();
        testList1.add("a1 100");
        testList1.add("a2 10");
        assertEquals(testStudent1.showSchedule(),testList1);
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


