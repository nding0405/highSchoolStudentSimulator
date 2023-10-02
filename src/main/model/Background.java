package model;

import java.util.ArrayList;
import java.util.List;

public class Background {
    private static String earlyMorning = "earlyMorning";
    private static String lateMorning = "lateMorning";
    private static String noon = "noon";
    private static String earlyAfternoon = "earlyAfternoon";
    private static String lateAfternoon = "lateAfternoon";
    private static String dusk = "dusk";
    private static String earlyEvening = "earlyEvening";
    private static String lateEvening = "lateEvening";
    private static String midNight1 = "midNight1";
    private static String midNight2 = "midNight2";
    private static List<String> encapsulateAday = encapAlltime(new ArrayList<>());
    private static int secondsToChange = 5;


    private String backGroundImage;

    public Background() {
        backGroundImage = earlyMorning;
    }

    //EFFECTS: consume一个布林值，如果true就是继续，如果false就是停止rendering并call resultRendering;
    public void timeGoesBy(Boolean goOn) throws InterruptedException {
        while (goOn) {
            oneDayCycle();
        }
    }

    //EFFECT: 一次整天循环，因为上面太长了所以封包一下TT。。。
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
