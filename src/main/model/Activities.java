package model;

import java.util.ArrayList;
import java.util.List;

public class Activities {
    private String name;
    private int time;
    private Boolean courseOrPlay;// 课程还是游乐
    private Boolean activityType;// true: 运动/science false: 静态/art

    private static Activities Mandarin = new Activities("Mandarin", 0, true, false);
    private static Activities English = new Activities("English", 0, true, false);
    private static Activities Math = new Activities("Math", 0, true, true);
    private static Activities Physics = new Activities("Physics", 0, true, true);
    private static Activities Chemistry = new Activities("Chemistry", 0, true, true);
    private static Activities Biology = new Activities("Biology", 0, true, true);
    private static Activities History = new Activities("History", 0, true, false);
    private static Activities Geology = new Activities("Geology", 0, true, false);
    private static Activities Politics = new Activities("Politics", 0, true, false);

    private static Activities Jogging = new Activities("Jogging", 0, false, true);
    private static Activities Hangout = new Activities("Hangout with friends", 0, false, true);
    private static Activities Hiking = new Activities("Hiking", 0, false, true);
    private static Activities Drawing = new Activities("Drawing", 0, false, false);
    private static Activities Piano =  new Activities("playing the piano", 0, false, false);
    private static Activities VideoGame = new Activities("VideoGame", 0, false, false);

    private static final List<Activities> activityList = setupActList(new ArrayList<>());
    private static final List<String> activityNameList = setupNameList(new ArrayList<>());

    public Activities(String name, int time, Boolean courseOrPlay, Boolean activityType) {
        this.name = name;
        this.time = time;
        this.courseOrPlay = courseOrPlay;
        this.activityType = activityType;
    }


    public void printOutActivities() {
        for (String name : activityNameList) {
            System.out.println(name);
        }
    }

    public Boolean detectValidActivity(String name) {
        if (activityNameList.contains(name)) {
            return true;
        } else {
            return false;
        }
    }

    // EFFECT: 根据name找到不同的模型，然后根据时间和模型求出types。
    public void findActivity(String name, int time) {
        int index = 0;
        String actName = activityNameList.get(index);
        while (!(actName.equals(name))) {
            index++;
            actName = activityNameList.get(index);
        }
        Activities targetActivity = activityList.get(index);
        this.name = name;
        this.time = time;
        this.activityType = targetActivity.getActivityType();
        this.courseOrPlay = targetActivity.getcourseOrPlay();
    }

    //getters

    public int getTime() {
        return this.time;
    }


    public Boolean getcourseOrPlay() {
        return this.courseOrPlay;
    }

    public Boolean getActivityType() {
        return this.activityType;
    }

    public String getName() {
        return this.name;
    }


    private static List<String> setupNameList(List<String> list) {
        String name;
        for (Activities a : activityList) {
            name = a.getName();
            list.add(name);
        }
        return list;
    }

    private static List<Activities> setupActList(List<Activities> list) {
        list.add(Mandarin);
        list.add(English);
        list.add(Math);
        list.add(Physics);
        list.add(Chemistry);
        list.add(Biology);
        list.add(History);
        list.add(Geology);
        list.add(Politics);
        list.add(Jogging);
        list.add(Hangout);
        list.add(Hiking);
        list.add(Drawing);
        list.add(Piano);
        list.add(VideoGame);
        return list;
    }
}
