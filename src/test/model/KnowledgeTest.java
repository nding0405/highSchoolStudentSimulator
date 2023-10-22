package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KnowledgeTest {
    private static final double courseFitKnowledgeIndex = 1.05;
    private static final double courseUnFitKnowledgeIndex = 0.95;
    private static final double loseKnowledgeWhenPlayIndex = 0.15;
    private static final float fullKnowledge = 196;

    private Knowledge k1;
    private Knowledge k2;
    private Knowledge k3;
    private Knowledge k4;
    private Activities a1;
    private Activities a2;
//    private Activities a3;
    private Activities a4;
    private Activities a5;
    private Activities a6;
    private Activities a7;


    @BeforeEach
    void setup() {
        k1 = new Knowledge();
        k2 = new Knowledge();
        k3 = new Knowledge();
        k4 = new Knowledge();
        a1 = new Activities("a1",100, true,true);
        a2 = new Activities("a2",10,true,false);
//        a3 = new Activities("a3",1000,false,true);
        a4 = new Activities("a4",100,false,false);
        a5 = new Activities("Mandarin",100,true,false);
        a6 = new Activities("Math",100,true,true);
        a7 = new Activities("English",100, true,false);
    }

    @Test
    void testConstructor() {
        assertEquals(0, k1.getMandarinKnowledge());
        assertEquals(0, k1.getMathKnowledge());
        assertEquals(0, k1.getEnglishKnowledge());
        assertEquals(0, k1.getS1Knowledge());
        assertEquals(0, k1.getS2Knowledge());
        assertEquals(0, k1.getS3Knowledge());
    }

    @Test
    void testAddKnowledge() {
        encapsulateAddKnowledgeTest(k1, a4, "a1", "a2", "a3", true,
                (int)(-loseKnowledgeWhenPlayIndex * 100), (int)(-loseKnowledgeWhenPlayIndex * 100),
                (int)(-loseKnowledgeWhenPlayIndex * 100), (int)(-loseKnowledgeWhenPlayIndex * 100),
                (int)(-loseKnowledgeWhenPlayIndex * 100), (int)(-loseKnowledgeWhenPlayIndex * 100));

        encapsulateAddKnowledgeTest(k2, a2, "a", "b", "c", true,
                0, 0, 0, 0, 0, 0);

        encapsulateAddKnowledgeTest(k3, a1, "a1", "b", "c", true,
                (int)(courseFitKnowledgeIndex * 100), 0, 0, 0, 0, 0);

        encapsulateAddKnowledgeTest(k4, a1, "a1", "b", "c", false,
                (int)(courseUnFitKnowledgeIndex * 100), 0, 0,0,0,0);
        setup();
        encapsulateAddKnowledgeTest(k1, a5, "a", "b", "c", true,
                0, 0, 0, (int)(courseUnFitKnowledgeIndex * 100), 0,0);

        encapsulateAddKnowledgeTest(k2, a6, "a1", "b", "c", true,
                0, 0, 0, 0, (int)(courseFitKnowledgeIndex * 100), 0);

        encapsulateAddKnowledgeTest(k3, a7, "a1", "b", "c", false,
                0, 0, 0, 0, 0, (int)(courseFitKnowledgeIndex * 100));
    }

    @Test
    void testAddKnowledgeMultiple() {
        k1.addKnowledge(a4, "a1", "a2", "a3", true);
        encapsulateAddKnowledgeTest(k1, a4, "a1", "a2", "a3", true,
                (int)(-loseKnowledgeWhenPlayIndex * 200), (int)(-loseKnowledgeWhenPlayIndex * 200),
                (int)(-loseKnowledgeWhenPlayIndex * 200), (int)(-loseKnowledgeWhenPlayIndex * 200),
                (int)(-loseKnowledgeWhenPlayIndex * 200), (int)(-loseKnowledgeWhenPlayIndex * 200));
    }

    void encapsulateAddKnowledgeTest(Knowledge k, Activities a, String s1, String s2, String s3, Boolean prefer,
                                     int expt1, int expt2, int expt3, int expt4, int expt5, int expt6) {
        k.addKnowledge(a, s1, s2, s3, prefer);
        assertEquals(expt1, k.getS1Knowledge());
        assertEquals(expt2, k.getS2Knowledge());
        assertEquals(expt3, k.getS3Knowledge());
        assertEquals(expt4, k.getMandarinKnowledge());
        assertEquals(expt5, k.getMathKnowledge());
        assertEquals(expt6, k.getEnglishKnowledge());
    }

    @Test
    void testaddKnowledgeHelperUnfit() {
        k1.addKnowledgeHelper(1, 100, false);
        assertEquals(k1.getS1Knowledge(), (int)(100 * courseUnFitKnowledgeIndex));
        assertEquals(k1.getS2Knowledge(), 0);
        assertEquals(k1.getS3Knowledge(), 0);
        k1.addKnowledgeHelper(2, 100, false);
        assertEquals(k1.getS1Knowledge(), (int)(100 * courseUnFitKnowledgeIndex));
        assertEquals(k1.getS2Knowledge(), (int)(100 * courseUnFitKnowledgeIndex));
        assertEquals(k1.getS3Knowledge(), 0);
        k1.addKnowledgeHelper(3, 100, false);
        assertEquals(k1.getS1Knowledge(), (int)(100 * courseUnFitKnowledgeIndex));
        assertEquals(k1.getS2Knowledge(), (int)(100 * courseUnFitKnowledgeIndex));
        assertEquals(k1.getS3Knowledge(), (int)(100 * courseUnFitKnowledgeIndex));
    }

    @Test
    void testaddKnowledgeHelperFit() {
        k2.addKnowledgeHelper(1, 100, true);
        assertEquals(k2.getS1Knowledge(), (int)(100 * courseFitKnowledgeIndex));
        assertEquals(k2.getS2Knowledge(), 0);
        assertEquals(k2.getS3Knowledge(), 0);
        k2.addKnowledgeHelper(2, 100, true);
        assertEquals(k2.getS1Knowledge(), (int)(100 * courseFitKnowledgeIndex));
        assertEquals(k2.getS2Knowledge(), (int)(100 * courseFitKnowledgeIndex));
        assertEquals(k2.getS3Knowledge(), 0);
        k2.addKnowledgeHelper(3, 100, true);
        assertEquals(k2.getS1Knowledge(), (int)(100 * courseFitKnowledgeIndex));
        assertEquals(k2.getS2Knowledge(), (int)(100 * courseFitKnowledgeIndex));
        assertEquals(k2.getS3Knowledge(), (int)(100 * courseFitKnowledgeIndex));
    }

    @Test
    void testTakeExam() {
        assertEquals(0, k1.takeExam(true));//base case
        k1.setS1Knowledge(200);
        k1.setS2Knowledge(200);
        k1.setS3Knowledge(200);
        k1.setMandarinKnowledge(200);
        k1.setMathKnowledge(200);
        k1.setEnglishKnowledge(200);
        assertEquals(750, k1.takeExam(true));
        k1.setS1Knowledge(100);
        k1.setS2Knowledge(150);
        k1.setS3Knowledge(175);
        k1.setMandarinKnowledge(196);
        k1.setMathKnowledge(100);
        k1.setEnglishKnowledge(50);
        assertEquals(480, k1.takeExam(true));
    }

    @Test
    void testTakeExamForCombined() {
        assertEquals(0, k1.takeExamForCombinedScienceOrArt(true));//base case
        k1.setS1Knowledge(100);
        k1.setS2Knowledge(200);
        k1.setS3Knowledge(50);
        assertEquals((int)(350 / (3 * fullKnowledge) * 300), k1.takeExamForCombinedScienceOrArt(true));
        k1.setS1Knowledge(200);
        k1.setS2Knowledge(200);
        k1.setS3Knowledge(200);
        assertEquals(300, k1.takeExamForCombinedScienceOrArt(true));

        assertEquals(0, k2.takeExamForCombinedScienceOrArt(false));//base case
        k2.setS1Knowledge(100);
        k2.setS2Knowledge(200);
        k2.setS3Knowledge(50);
        assertEquals((int)(350 / (3 * fullKnowledge) * 300), k2.takeExamForCombinedScienceOrArt(false));

        k2.setS1Knowledge(200);
        k2.setS2Knowledge(200);
        k2.setS3Knowledge(200);
        assertEquals(300, k2.takeExamForCombinedScienceOrArt(false));
    }

    @Test
    void testtakeExamForMandarinMathEnglish() {
        k1.setMandarinKnowledge(90);
        k1.setMathKnowledge(80);
        k1.setEnglishKnowledge(70);
        assertEquals((int)((90 / fullKnowledge) * 150), k1.takeExamForMandarin());
        assertEquals((int)((80 / fullKnowledge) * 150), k1.takeExamForMath());
        assertEquals((int)((70 / fullKnowledge) * 150), k1.takeExamForEnglish());

        k1.setMandarinKnowledge((int)fullKnowledge);
        k1.setMathKnowledge((int)fullKnowledge);
        k1.setEnglishKnowledge((int)fullKnowledge);
        assertEquals(150, k1.takeExamForMandarin());
        assertEquals(150, k1.takeExamForMath());
        assertEquals(150, k1.takeExamForEnglish());
    }

    @Test
    void testAddKnowledgeHelperFit() {
        k1.addKnowledgeHelper(1, 100, true);
        assertEquals((int) (100 * courseFitKnowledgeIndex), k1.getS1Knowledge());
        k1.addKnowledgeHelper(2, 10, true);
        assertEquals((int) (10 * courseFitKnowledgeIndex), k1.getS2Knowledge());
        k1.addKnowledgeHelper(3, 400, true);
        assertEquals((int) (400 * courseFitKnowledgeIndex), k1.getS3Knowledge());
        k1.addKnowledgeHelper(4, 40, true);
        assertEquals((int) (40 * courseFitKnowledgeIndex), k1.getMandarinKnowledge());
        k1.addKnowledgeHelper(5, 10, true);
        assertEquals((int) (10 * courseFitKnowledgeIndex), k1.getMathKnowledge());
        k1.addKnowledgeHelper(6, 150, true);
        assertEquals((int) (150 * courseFitKnowledgeIndex), k1.getEnglishKnowledge());
    }

    @Test
    void testAddKnowledgeHelperUnFit() {
        k1.addKnowledgeHelper(1, 100, false);
        assertEquals((int) (100 * courseUnFitKnowledgeIndex), k1.getS1Knowledge());
        k1.addKnowledgeHelper(2, 10, false);
        assertEquals((int) (10 * courseUnFitKnowledgeIndex), k1.getS2Knowledge());
        k1.addKnowledgeHelper(3, 400, false);
        assertEquals((int) (400 * courseUnFitKnowledgeIndex), k1.getS3Knowledge());
        k1.addKnowledgeHelper(4, 40, false);
        assertEquals((int) (40 * courseUnFitKnowledgeIndex), k1.getMandarinKnowledge());
        k1.addKnowledgeHelper(5, 10, false);
        assertEquals((int) (10 * courseUnFitKnowledgeIndex), k1.getMathKnowledge());
        k1.addKnowledgeHelper(6, 150, false);
        assertEquals((int) (150 * courseUnFitKnowledgeIndex), k1.getEnglishKnowledge());
    }

    @Test
    void testfitAddknowledge() {
        k1.fitAddknowledge(1, 100);
        assertEquals((int) (100 * courseFitKnowledgeIndex), k1.getS1Knowledge());
        k1.fitAddknowledge(2, 10);
        assertEquals((int) (10 * courseFitKnowledgeIndex), k1.getS2Knowledge());
        k1.fitAddknowledge(3, 400);
        assertEquals((int) (400 * courseFitKnowledgeIndex), k1.getS3Knowledge());
        k1.fitAddknowledge(4, 40);
        assertEquals((int) (40 * courseFitKnowledgeIndex), k1.getMandarinKnowledge());
        k1.fitAddknowledge(5, 10);
        assertEquals((int) (10 * courseFitKnowledgeIndex), k1.getMathKnowledge());
        k1.fitAddknowledge(6, 150);
        assertEquals((int) (150 * courseFitKnowledgeIndex), k1.getEnglishKnowledge());
    }

    @Test
    void testUnfitAddknowledge() {
        k1.unFitAddknowledge(1, 100);
        assertEquals((int) (100 * courseUnFitKnowledgeIndex), k1.getS1Knowledge());
        k1.unFitAddknowledge(2, 10);
        assertEquals((int) (10 * courseUnFitKnowledgeIndex), k1.getS2Knowledge());
        k1.unFitAddknowledge(3, 400);
        assertEquals((int) (400 * courseUnFitKnowledgeIndex), k1.getS3Knowledge());
        k1.unFitAddknowledge(4, 40);
        assertEquals((int) (40 * courseUnFitKnowledgeIndex), k1.getMandarinKnowledge());
        k1.unFitAddknowledge(5, 10);
        assertEquals((int) (10 * courseUnFitKnowledgeIndex), k1.getMathKnowledge());
        k1.unFitAddknowledge(6, 150);
        assertEquals((int) (150 * courseUnFitKnowledgeIndex), k1.getEnglishKnowledge());
    }
}
