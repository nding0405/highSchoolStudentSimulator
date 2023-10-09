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

    // EFFECT: find the corresponding activities object
    // and assign the input time to the corresponding activities. Assign the fully prepared activities object
    // (which means all fields in activities class are correctly assigned to the object regards to the user input)
    // to the object referenced by the parameter "a".
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

    //REQUIRES:at MOST one of the string is the same as the current activities' name.
    //EFFECTS: check if the current Activities name is one of the three input Strings. If yes, return true,
    // return false otherwise.
    public boolean actInSelection(String s1, String s2, String s3) {
        String actName = getName();
        if (actName.equals(s1) || actName.equals(s2) || actName.equals(s3)) {
            return true;
        } else {
            return false;
        }
    }

    //REQUIRES:There is EXACTLY ONE string in the parameters equals to the current activities' name.
    //EFFECTS: return the index of the parameter who is equals to the activities' name.
    // (ex. the activities name is "a" and the parameter s1 is "a", return 1)
    public int getCorrespondingIndex(String s1, String s2, String s3) {
        String actName = getName();
        if (actName.equals(s1)) {
            return 1;
        } else if (actName.equals(s2)) {
            return 2;
        } else {
            return 3;
        }
    }
}
