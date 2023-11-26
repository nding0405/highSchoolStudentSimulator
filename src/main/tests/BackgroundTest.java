package tests;

import model.Background;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BackgroundTest {
    Background testBk;

    @BeforeEach
    void setUp() {
        testBk = new Background();
    }

    @Test
    void testConstructor() {
        assertEquals(testBk, testBk);
        //there are no fields in Background class.
    }

    @Test
    void testRun() {
        testBk.run();
    }

    @Test
    void testTimeGoesBy() throws InterruptedException {
        testBk.timeGoesBy(false);//paused
//        testBk.timeGoesBy(true);//keeps printing out the day cycles
    }

    @Test
    void testOneDayCycle() throws InterruptedException {
        testBk.oneDayCycle();
        //print out:
        //earlyMorning
        //lateMorning
        //noon
        //earlyAfternoon
        //lateAfternoon
        //dusk
        //earlyEvening
        //lateEvening
        //midNight1
        //midNight2
        // there is a 5-second gap between each output
    }
}
