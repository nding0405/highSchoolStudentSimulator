package model;

import java.util.ArrayList;
import java.util.List;

public class Activities {
    private String name;
    private int time;
    private Boolean courseOrPlay;
    private Boolean activityType;

    private static final Activities Mandarin = new Activities("Mandarin", 0, true, false);
    private static final Activities English = new Activities("English", 0, true, false);
    private static final Activities Math = new Activities("Math", 0, true, true);
    private static final Activities Physics = new Activities("Physics", 0, true, true);
    private static final Activities Chemistry = new Activities("Chemistry", 0, true, true);
    private static final Activities Biology = new Activities("Biology", 0, true, true);
    private static final Activities History = new Activities("History", 0, true, false);
    private static final Activities Geology = new Activities("Geology", 0, true, false);
    private static final Activities Politics = new Activities("Politics", 0, true, false);

    private static final Activities Jogging = new Activities("Jogging", 0, false, true);
    private static final Activities Hangout = new Activities("Hangout_with_friends", 0, false, true);
    private static final Activities Hiking = new Activities("Hiking", 0, false, true);
    private static final Activities Drawing = new Activities("Drawing", 0, false, false);
    private static final Activities Piano =  new Activities("playing_the_piano", 0, false, false);
    private static final Activities VideoGame = new Activities("VideoGame", 0, false, false);

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


    //EFFECT: return true if the input is one of the Activities name in the static list "activityNameList", false
    //otherwise;
    public Boolean detectValidActivity(String name) {
        return activityNameList.contains(name);
    }


    //REQUIRES: the input name must be one of the name in the static name list "activityNameList".
    // EFFECT: find the corresponding activities object
    // and assign the input time to the corresponding activities. Assign the fully prepared activities object
    // (which means all fields in activities class are correctly assigned to the object regards to the user input)
    // to the object referenced by the parameter "a".
    public void findActivity(String name, int time) {
        int index = 0;
        String actName = activityNameList.get(index);
        while (!(actName.equalsIgnoreCase(name))) {
            index++;
            actName = activityNameList.get(index);
        }
        Activities targetActivity = activityList.get(index);
        this.name = name;
        this.time = time;
        this.activityType = targetActivity.getActivityType();
        this.courseOrPlay = targetActivity.getcourseOrPlay();
    }


    //REQUIRES:at MOST one of the string is the same as the current activities' name.
    //EFFECTS: check if the current Activities name is one of the three input Strings or one of "mandarin" "math"
    // "english"(case does not matter). If yes, return true, return false otherwise.
    public boolean actInSelection(String s1, String s2, String s3) {
        String actName = getName();
        return (actName.equalsIgnoreCase(s1) || actName.equalsIgnoreCase(s2) || actName.equalsIgnoreCase(s3)
                || actName.equalsIgnoreCase("mandarin")
                || actName.equalsIgnoreCase("math")
                || actName.equalsIgnoreCase("english"));
    }


    //REQUIRES:There is EXACTLY ONE string in the parameters and "mandarin" "math" "english" equals to the current
    // activities' name.
    //EFFECTS: return the index of the parameter which is equals to the activities' name or the index of "mandarin"
    // "math" "english" which is equals to the activities' name
    // (ex. the activities name is "a" and the parameter s1 is "a", return 1)
    // (ex. the activities name is "a" and the parameter s2 is "a", return 2)
    // (ex. the activities name is "a" and the parameter s3 is "a", return 3)
    // (ex. the activities name is "mandarin" , return 4)
    // (ex. the activities name is "math" , return 5)
    // (ex. the activities name is "english" , return 6)
    public int getCorrespondingIndex(String s1, String s2, String s3) {
        String actName = getName();
        if (actName.equalsIgnoreCase(s1)) {
            return 1;
        } else if (actName.equalsIgnoreCase(s2)) {
            return 2;
        } else if (actName.equalsIgnoreCase(s3)) {
            return 3;
        } else if (actName.equalsIgnoreCase("mandarin")) {
            return 4;
        } else if (actName.equalsIgnoreCase("math")) {
            return 5;
        } else {
            return 6;
        }
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
