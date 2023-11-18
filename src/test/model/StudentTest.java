package model;

import model.Exceptions.PressureExceedException;
import model.Exceptions.TimeUpException;
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
    private static final double courseFitpressureIndex = 0.5;
    private static final double courseUnFitpressureIndex = 1.3;
    private static final double playFitPressureIndex = 1.0;
    private static final double playUnFitPressureIndex = 0.4;
    private static final int totalTimeTograduate = 1000;
    private static final int maxPressure = 600;


    @BeforeEach
    void setupStudent() {
        testStudent1 = new Student("I+A");
        testStudent1.setChr(false);
        testStudent1.setPreference(false);
        testStudent1.setFineart(false);
        testStudent2 = new Student("I+S");
        testStudent2.setChr(false);
        testStudent2.setPreference(true);
        testStudent2.setFineart(false);
        testStudent3 = new Student("E+A");
        testStudent3.setChr(true);
        testStudent3.setPreference(false);
        testStudent3.setFineart(false);
        testStudent4 = new Student("E+S");
        testStudent4.setChr(true);
        testStudent4.setPreference(true);
        testStudent4.setFineart(false);
        a1 = new Activities("a1",100, true,true);
        a2 = new Activities("a2",10,true,false);
        a3 = new Activities("a3",1000,false,true);
        a4 = new Activities("a4",100,false,false);
        a5 = new Activities("a5",1000,true,false);
    }

    @Test
    void testConstructor() {
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
    }

    @Test
        //make sure the console is printing (testStudent1)
        //Student Profile:
        //-Student Name: I+A
        //        -Subjects for final exam: Mandarin, Math, English, A, B, C
        //        -Subject Preference: art
        //        -Character: introverted
        //make sure the console is printing (testStudent2)
        //Student Profile:
        //-Student Name: I+S
        //-Subjects for final exam: Mandarin, Math, English, A, B, C
        //-Subject Preference: fine art
        //-Character: introverted
        //make sure the console is printing (testStudent3)
        //Student Profile:
        //-Student Name: E+A
        //-Subjects for final exam: Mandarin, Math, English, A, B, C
        //-Subject Preference: science
        //-Character: outgoing
        //make sure the console is printing (testStudent3)
        //Student Profile:
        //-Student Name: E+S
        //-Subjects for final exam: Mandarin, Math, English, A, B, C
        //-Subject Preference: fine art
        //-Character: outgoing
    void testStudentProfile() { //1 ff //2 ft 3 tf 4 tt
        testStudent1.studentProfile();

        testStudent2.setFineart(true);
        testStudent2.studentProfile();

        testStudent3.setPreference(true);
        testStudent3.studentProfile();

        testStudent4.setFineart(true);
        testStudent4.studentProfile();
    }


    @Test
    void testAddActivitiesA1() {
        List<Activities> exptList = new ArrayList<>();
        exptList.add(a1);
        wrapUp(testStudent1, a1, 100, (int)(100*courseUnFitpressureIndex));
        assertEquals(exptList, testStudent1.getSchedule());
        wrapUp(testStudent2, a1, 100, (int)(100*courseFitpressureIndex));
        assertEquals(exptList, testStudent2.getSchedule());
        wrapUp(testStudent3, a1, 100, (int)(100*courseUnFitpressureIndex));
        assertEquals(exptList, testStudent3.getSchedule());
        wrapUp(testStudent4, a1, 100, (int)(100*courseFitpressureIndex));
        assertEquals(exptList, testStudent4.getSchedule());
    }

    @Test
    void testAddActivitiesA2() {
        List<Activities> exptList = new ArrayList<>();
        exptList.add(a2);
        wrapUp(testStudent1, a2, 10, (int)(10 * courseFitpressureIndex));
        assertEquals(exptList, testStudent1.getSchedule());
        wrapUp(testStudent2, a2, 10, (int)(10 * courseUnFitpressureIndex));
        assertEquals(exptList, testStudent2.getSchedule());
        wrapUp(testStudent3, a2, 10, (int)(10 * courseFitpressureIndex));
        assertEquals(exptList, testStudent3.getSchedule());
        wrapUp(testStudent4, a2, 10, (int)(10 * courseUnFitpressureIndex));
        assertEquals(exptList, testStudent4.getSchedule());
    }

    @Test
    void testAddActivitiesA3() {
        List<Activities> exptList = new ArrayList<>();
        exptList.add(a3);
        wrapUp(testStudent1, a3, 1000, -(int)(playUnFitPressureIndex*1000));
        assertEquals(exptList, testStudent1.getSchedule());
        wrapUp(testStudent2, a3, 1000, -(int)(playUnFitPressureIndex*1000));
        assertEquals(exptList, testStudent2.getSchedule());
        wrapUp(testStudent3, a3, 1000, -(int)(playFitPressureIndex*1000));
        assertEquals(exptList, testStudent3.getSchedule());
        wrapUp(testStudent4, a3, 1000, -(int)(playFitPressureIndex*1000));
        assertEquals(exptList, testStudent4.getSchedule());
    }

    @Test
    void testAddActivitiesA4() {
        List<Activities> exptList = new ArrayList<>();
        exptList.add(a4);
        wrapUp(testStudent1, a4, 100, -(int)(playFitPressureIndex*100));
        assertEquals(exptList, testStudent1.getSchedule());
        wrapUp(testStudent2, a4, 100, -(int)(playFitPressureIndex*100));
        assertEquals(exptList, testStudent2.getSchedule());
        wrapUp(testStudent3, a4, 100, -(int)(playUnFitPressureIndex*100));
        assertEquals(exptList, testStudent3.getSchedule());
        wrapUp(testStudent4, a4, 100, -(int)(playUnFitPressureIndex*100));
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
        try {
            testStudent1.detectEnding();
            fail();
        } catch (PressureExceedException p) {
            fail();
        } catch (TimeUpException t) {
            //pass
        }

        testStudent2.addActivity(a5);
        try {
            testStudent2.detectEnding();
            fail();
        } catch (PressureExceedException p) {
            //pass
        } catch (TimeUpException t) {
            fail();
        }

        testStudent3.addActivity(a1);
        try {
            testStudent3.detectEnding();
            //pass
        } catch (PressureExceedException p) {
            fail();
        } catch (TimeUpException t) {
            fail();
        }
    }

    @Test
    void testEndChoice() {
        assertEquals(0,testStudent1.endChoice(300));
        //make sure it's printing out:
        // "I+A got a really bad mark on the final and no college provides offer!"
        assertEquals(1,testStudent1.endChoice(400));
        //"I+A goes for a college."
        assertEquals(2,testStudent1.endChoice(450));
        //"I+A goes for an university."
        assertEquals(2,testStudent1.endChoice(500));
        //"I+A goes for an university."
        assertEquals(3,testStudent1.endChoice(550));
        //"I+A goes for an key university."
        assertEquals(4,testStudent1.endChoice(610));
        //"goes for a world-class universities."
        assertEquals(3,testStudent1.endChoice(600));
        //"I+A goes for an key university."
        assertEquals(5,testStudent1.endChoice(630));
        //"goes for a 'Double First-Class' university."
        assertEquals(5,testStudent1.endChoice(670));
        //"goes for Tsinghua University."
        assertEquals(6,testStudent1.endChoice(690));
        //"I+A goes for Tsinghua University."
        assertEquals(7,testStudent1.endChoice(740));
        //"I+A is the Number one scholar and can go for any university he/she wants!"
    }

    @Test
    void testEndFineArt() {
        testStudent1.endFineArt();
        //make sure it's printing out:
        // "I+A is happy that he/she is able to learn fine art. He/She works hard and goes for a nice art college."
    }

    void wrapUp(Student s,Activities a, int eptTime, int eptPressure) {
        s.addActivity(a);
        assertEquals(s.getTime(),eptTime);
        assertEquals(s.getPressure(),eptPressure);
        List<Activities> exptList = s.getSchedule();
        assertEquals(exptList.get(0) ,a);
    }

    @Test
    void testAddTimeOnce() {
        testStudent1.addTime(a1);
        assertEquals(100,testStudent1.getTime());
        testStudent2.addTime(a2);
        assertEquals(10,testStudent2.getTime());
    }

    @Test
    void testAddTimeMultiple() {
        testStudent1.addTime(a1);
        testStudent1.addTime(a2);
        assertEquals(110,testStudent1.getTime());
        testStudent2.addTime(a2);
        testStudent2.addTime(a3);
        assertEquals(1010,testStudent2.getTime());
    }

    @Test
    void testAddPressureCourse() {
        testStudent1.addPressure(a1);
        assertEquals((int)(100*courseUnFitpressureIndex), testStudent1.getPressure());
        testStudent2.addPressure(a1);
        assertEquals((int)(100*courseFitpressureIndex), testStudent2.getPressure());
        testStudent3.addPressure(a1);
        assertEquals((int)(100*courseUnFitpressureIndex), testStudent3.getPressure());
        testStudent4.addPressure(a1);
        assertEquals((int)(100*courseFitpressureIndex), testStudent4.getPressure());
    }

    @Test
    void testAddPressureGame() {
        testStudent1.addPressure(a3);
        assertEquals(-(int)(playUnFitPressureIndex*1000), testStudent1.getPressure());
        testStudent2.addPressure(a3);
        assertEquals(-(int)(playUnFitPressureIndex*1000), testStudent2.getPressure());
        testStudent3.addPressure(a3);
        assertEquals(-(int)(playFitPressureIndex*1000), testStudent3.getPressure());
        testStudent4.addPressure(a3);
        assertEquals(-(int)(playFitPressureIndex*1000), testStudent4.getPressure());
    }

    @Test
    void testUpdateBars() {
        testStudent1.addActivity(a1);
        testStudent1.updateBars();
        //make sure it's printing out:
        // Time Bar: 100/1000
        // Pressure Bar: 130/600
        // Knowledge Bar: - A:0
        //                - B:0
        //                - C:0
        //                - Mandarin:0
        //                - Math:0
        //                - English:0
    }

    @Test
    void testStudentAnime() throws InterruptedException {
        testStudent1.studentAnime(a1);
        //make sure it's printing out "the student is studying" six times
        testStudent1.studentAnime(a3);
        //make sure it's printing out "the student is relaxing" six times
    }

    @Test
    void testSetSelectionAgreeArt() {
        testStudent1.setSelectionAgree("a", 1);
        assertEquals("history", testStudent1.getSubjectSelectionOne());
        assertEquals("geology", testStudent1.getSubjectSelectionTwo());
        assertEquals("politics", testStudent1.getSubjectSelectionThree());
        assertEquals(-50, testStudent1.getPressure());
        assertFalse(testStudent1.getScienceOrArtForExam());
        testStudent2.setSelectionAgree("a", 2);
        assertEquals("history", testStudent2.getSubjectSelectionOne());
        assertEquals("geology", testStudent2.getSubjectSelectionTwo());
        assertEquals("politics", testStudent2.getSubjectSelectionThree());
        assertEquals(30, testStudent2.getPressure());
        assertFalse(testStudent2.getScienceOrArtForExam());
    }

    @Test
    void testSetSelectionAgreeScience() {
        testStudent3.setSelectionAgree("s", 1);
        assertEquals("physics", testStudent3.getSubjectSelectionOne());
        assertEquals("biology", testStudent3.getSubjectSelectionTwo());
        assertEquals("chemistry", testStudent3.getSubjectSelectionThree());
        assertEquals(-50, testStudent3.getPressure());
        assertTrue(testStudent3.getScienceOrArtForExam());
        testStudent4.setSelectionAgree("s", 2);
        assertEquals("physics", testStudent4.getSubjectSelectionOne());
        assertEquals("biology", testStudent4.getSubjectSelectionTwo());
        assertEquals("chemistry", testStudent4.getSubjectSelectionThree());
        assertEquals(30, testStudent4.getPressure());
        assertTrue(testStudent4.getScienceOrArtForExam());
        testStudent4.setSelectionAgree("f", 1);
        testStudent4.setSelectionAgree("f", 2);
        //print out:  E+S is happy that he/she is able to learn fine art He/She works hard and goes for a nice
        // art college.
    }

    @Test
    void testSetSelectionDisagreeArt() {
        testStudent1.setSelectionDisAgree("a");
        assertEquals("physics", testStudent1.getSubjectSelectionOne());
        assertEquals("biology", testStudent1.getSubjectSelectionTwo());
        assertEquals("chemistry", testStudent1.getSubjectSelectionThree());
        assertEquals(80, testStudent1.getPressure());
        assertTrue(testStudent1.getScienceOrArtForExam());
        testStudent2.setSelectionDisAgree("fa");
        assertEquals("history", testStudent2.getSubjectSelectionOne());
        assertEquals("geology", testStudent2.getSubjectSelectionTwo());
        assertEquals("politics", testStudent2.getSubjectSelectionThree());
        assertEquals(240, testStudent2.getPressure());
        assertFalse(testStudent2.getScienceOrArtForExam());
        testStudent3.setSelectionDisAgree("fs");
        assertEquals("physics", testStudent3.getSubjectSelectionOne());
        assertEquals("biology", testStudent3.getSubjectSelectionTwo());
        assertEquals("chemistry", testStudent3.getSubjectSelectionThree());
        assertEquals(400, testStudent3.getPressure());
        assertFalse(testStudent3.getScienceOrArtForExam());
    }

    @Test
    void testExamOrdrop() { //finished and passed
        testStudent1.setPressure(maxPressure);
        assertTrue(testStudent1.dropOrExam());

        testStudent1.setPressure(maxPressure + 10);
        assertTrue(testStudent1.dropOrExam());

        testStudent2.setTime(totalTimeTograduate);
        assertFalse(testStudent2.dropOrExam());

        testStudent2.setTime(totalTimeTograduate + 10);
        assertFalse(testStudent2.dropOrExam());
    }

    @Test
    void testGetRemainingTime() { //finished and passed
        assertEquals(totalTimeTograduate, testStudent1.getRemainingTime());//case that no activities are added
        testStudent1.setTime(500);
        assertEquals(500, testStudent1.getRemainingTime());//case that time = remaining time
        testStudent1.setTime(totalTimeTograduate - 1);
        assertEquals(1, testStudent1.getRemainingTime());//boundary case
    }

    @Test
    void testValidTime() { //finished and passed
        testStudent1.setTime(0);
        assertTrue(testStudent1.validTime(1000));//case when no activities are added
        testStudent1.setTime(300);
        assertTrue(testStudent1.validTime(700));//case when the time is exactly the same as remaining time.
        testStudent1.setTime(300);
        assertTrue(testStudent1.validTime(500));//case when the time is less than the remaining time.
        testStudent1.setTime(300);
        assertFalse(testStudent1.validTime(800));//case when the time greater than the remaining time.
        assertFalse(testStudent1.validTime(-1));
        assertTrue(testStudent1.validTime(5));
        testStudent1.setTime(999);
        assertFalse(testStudent1.validTime(3));
    }
}


