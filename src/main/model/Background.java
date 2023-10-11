package model;

import java.util.ArrayList;
import java.util.List;
//Represent the background of this game. The constants will become images in the visualization phase

public class Background  extends Thread {
    private static final String earlyMorning = "earlyMorning";
    private static final String lateMorning = "lateMorning";
    private static final String noon = "noon";
    private static final String earlyAfternoon = "earlyAfternoon";
    private static final String lateAfternoon = "lateAfternoon";
    private static final String dusk = "dusk";
    private static final String earlyEvening = "earlyEvening";
    private static final String lateEvening = "lateEvening";
    private static final String midNight1 = "midNight1";
    private static final String midNight2 = "midNight2";
    private static final List<String> encapsulateAday = encapAlltime(new ArrayList<>());
    private static final int secondsToChange = 5;


    //EFFECTS: used to run the Background thread in the multi thread
    @Override
    public void run() {
        try {
            timeGoesBy(false);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //EFFECT: *empty
    public Background() {

    }

    //EFFECTS: Keep rendering the day cycle. Changed every 5 seconds.
    public void timeGoesBy(Boolean goOn) throws InterruptedException {
        while (goOn) {
            oneDayCycle();
        }
    }

    //EFFECT: render one day cycle. Changed every 5 seconds.
    public void oneDayCycle() throws InterruptedException {
        for (String timePeriod : encapsulateAday) {
            System.out.println(timePeriod);
            Thread.sleep(secondsToChange * 1000);
        }
    }

    private static List<String> encapAlltime(List<String> list) {
        list.add(earlyMorning);
        list.add(lateMorning);
        list.add(noon);
        list.add(earlyAfternoon);
        list.add(lateAfternoon);
        list.add(dusk);
        list.add(earlyEvening);
        list.add(lateEvening);
        list.add(midNight1);
        list.add(midNight2);
        return list;
    }
}
