package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KnowledgeTest {
    private static double courseFitKnowledgeIndex = 1.08;
    private static double courseUnFitKnowledgeIndex = 0.8;
    private static double loseKnowledgeWhenPlayIndex = 0.15;

    private Knowledge k1;
    private Knowledge k2;
    private Knowledge k3;
    private Knowledge k4;
    private Activities a1;
    private Activities a2;
    private Activities a3;
    private Activities a4;
    private Activities a5;


    @BeforeEach
    void setup() {
        k1 = new Knowledge();
        k2 = new Knowledge();
        k3 = new Knowledge();
        k4 = new Knowledge();
        a1 = new Activities("a1",100, true,true);//理科课
        a2 = new Activities("a2",10,true,false);//文科课
        a3 = new Activities("a3",1000,false,true);//户外活动
        a4 = new Activities("a4",100,false,false);//室内活动
        a5 = new Activities("a5",1000,true,false);//文科课
    }

    @Test
    void testConstructor() {
        assertEquals(0, k1.getS1Knowledge());
        assertEquals(0, k1.getS2Knowledge());
        assertEquals(0, k1.getS3Knowledge());
    }

    @Test
    void testAddKnowledge() {
        encapsulateAddKnowledgeTest(k1, a4, "a1", "a2", "a3", true,true,
                (int)(-loseKnowledgeWhenPlayIndex * 100), (int)(-loseKnowledgeWhenPlayIndex * 100),
                (int)(-loseKnowledgeWhenPlayIndex * 100));//活动

        encapsulateAddKnowledgeTest(k2, a2, "a", "b", "c", true,true, 0, 0, 0);//课程不在选科内

        encapsulateAddKnowledgeTest(k3, a1, "a1", "b", "c", true,true,
                (int)(courseFitKnowledgeIndex * 100), 0, 0);//课程在选科内且符合偏好

        encapsulateAddKnowledgeTest(k4, a1, "a1", "b", "c", false,true,
                (int)(courseUnFitKnowledgeIndex * 100), 0, 0);//课程在选科内且不符合偏好
    }

    @Test
    void testAddKnowledgeMultiple() {
        k1.addKnowledge(a4, "a1", "a2", "a3", true, true);
        encapsulateAddKnowledgeTest(k1, a4, "a1", "a2", "a3", true,true,
                (int)(-loseKnowledgeWhenPlayIndex * 200), (int)(-loseKnowledgeWhenPlayIndex * 200),
                (int)(-loseKnowledgeWhenPlayIndex * 200));//活动
    }

    void encapsulateAddKnowledgeTest(Knowledge k, Activities a, String s1, String s2, String s3, Boolean prefer,
                                     Boolean chr, int expt1, int expt2, int expt3) {
        k.addKnowledge(a, s1, s2, s3, prefer, chr);
        assertEquals(expt1, k.getS1Knowledge());
        assertEquals(expt2, k.getS2Knowledge());
        assertEquals(expt3, k.getS3Knowledge());
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
}
