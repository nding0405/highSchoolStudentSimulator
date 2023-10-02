package model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private static String studying1 = "the student is studying";
    private static String studying2 = "the student is studying";
    private static String relaxing1 = "the student is relaxing";
    private static String relaxing2 = "the student is relaxing";
    private static String end1 = "student gets into the 1st ranked university";
    private static String end2 = "student gets into the 2nd ranked university";
    private static String end3 = "student gets into the 3nd ranked university";
    private static String end4 = "student gets into an university";
    private static String end5 = "student does not get into an university";
    private static String end6 = "dropped the school";
    private static double courseFitpressureIndex = 0.9;
    private static double courseUnFitpressureIndex = 1.2;
    private static double courseFitKnowledgeIndex = 1.1;
    private static double courseUnFitKnowledgeIndex = 0.8;
    private static double playFitPressureIndex = 1.1;
    private static double playUnFitPressureIndex = 0.5;
    private static double loseKnowledgeWhenPlayIndex = 0.2;
    private static int totalTimeTograduate = 1000;
    private static int maxPressure = 500;



    private String name;
    private List<Activities> schedule;
    private int time;
    private int pressure;
    private int knowledge;
    private Boolean chrType;
    private Boolean preference;

    //EFFECT: create a new student object with name, accumulated time, pressure and knowledge and an empty schedule.
    // chrType is the type of character of the student, true: love sport, extroverted. false: love arts, introverted.
    // preference is the subject preference of the student, true: love science. false: love arts.
    public Student(String name) {
        this.name = name;
        this.schedule = new ArrayList<>();
        this.time = 0;
        this.pressure = 0;
        this.knowledge = 0;
        this.chrType = Math.random() < 0.5;
        this.preference = Math.random() < 0.5;
    }

    //REQUIRES: Activities must be one of the name in the menu
    //MODIFIES: this
    //EFFECT: Add activity "a" to the schedule of the student, add time,knowledge and pressure to the student.
    public void addActivity(Activities a) {
        schedule.add(a);
        addTime(a);
        addKnowledge(a);
        addPressure(a);
        updateBars();
    }


    public List<String> showSchedule() {
        List<String> showSchedule = new ArrayList<>();
        String actName;
        int actTime;
        for (Activities a : schedule) {
            actName = a.getName();
            actTime = a.getTime();
            showSchedule.add(actName + " " + actTime);
            System.out.println("Activity Name: " + actName + "Activity Time: " + actTime);
        }
        return showSchedule;
    }


    //EFFECT: check if the game end: false:did not end true:end
    public Boolean detectEnding() {
        if (maxPressure <= pressure) {
            System.out.println("exceed pressure limit");
            return true;
        } else {
            if (totalTimeTograduate <= time) {
                System.out.println("exceed time limit");
                return true;
            } else {
                System.out.println("Game goes on");
                return false;
            }
        }
    }

    //EFFECT: 根据数值选择不同结局
    public void endChoice() {
        System.out.println("game ends");
    }

    //EFFECT: 加时间
    public void addTime(Activities a) {
        int actTime = a.getTime();
        time += actTime;
    }


    //EFFECT: 加pressure
    public void addPressure(Activities a) {
        Boolean courseOrPlay = a.getcourseOrPlay();//T：上课 F:玩
        Boolean  activityType = a.getActivityType();//T：理科 F：文科  T：运动 F：安静爱好
        int actTime = a.getTime();
        if (courseOrPlay) {
            if (preference == activityType) {
                pressure += (int)(courseFitpressureIndex * actTime);
            } else {
                pressure += (int)(courseUnFitpressureIndex * actTime);
            }
        } else {
            if (chrType == activityType) {
                pressure -= (int)(actTime * playFitPressureIndex);
            } else {
                pressure -= (int)(actTime * playUnFitPressureIndex);
            }
        }

    }

    //EFFECT: 加knowledge
    public void addKnowledge(Activities a) {
        Boolean courseOrPlay = a.getcourseOrPlay();//T：上课 F:玩
        Boolean  activityType = a.getActivityType();//T：理科 F：文科  T：运动 F：安静爱好
        int actTime = a.getTime();
        if (courseOrPlay) {
            if (preference == activityType) {
                knowledge += (int)(courseFitKnowledgeIndex * actTime);
            } else {
                knowledge += (int)(courseUnFitKnowledgeIndex * actTime);
            }
        } else {
            knowledge -= actTime * loseKnowledgeWhenPlayIndex;
        }

    }


    //EFFECT: 更新time bar, pressure bar, knowledge bar 并 print out.
    public void updateBars() {
        int pressureNow = pressure;
        int knowledgeNow = knowledge;
        int timeNow = time;
        System.out.println("Time Bar: " + timeNow + "/" + totalTimeTograduate);
        System.out.println("Pressure Bar: " + pressureNow + "/" + maxPressure);
        if (knowledge <= 0) {
            System.out.println("Knowledge Bar: " + "idoit");
        }
        if ((0 < knowledge) && (100 >= knowledge)) {
            System.out.println("Knowledge Bar: " + "fine");
        }
        if ((100 < knowledge) && (200 >= knowledge)) {
            System.out.println("Knowledge Bar: " + "Keep going");
        }
        if ((200 < knowledge) && (300 >= knowledge)) {
            System.out.println("Knowledge Bar: " + "Good job");
        }
        if ((300 < knowledge) && (400 >= knowledge)) {
            System.out.println("Knowledge Bar: " + "Talented learner");
        }
        if ((400 < knowledge)) {
            System.out.println("Knowledge Bar: " + "Genius");
        }


    }

    //EFFECT: 根据activity类型播放不同的学生动画.
    public void studentAnime(Activities a) {

    }


    //getters:

    public String getName() {
        return this.name;
    }

    public List<Activities> getSchedule() {
        return this.schedule;
    }

    public int getTime() {
        return this.time;
    }

    public int getKnowledge() {
        return this.knowledge;
    }

    public int getPressure() {
        return this.pressure;
    }

    public void setChr(Boolean chr) {
        this.chrType = chr;
    }

    public Boolean getChr() {
        return this.chrType;
    }

    public void setPreference(Boolean prefer) {
        this.preference = prefer;
    }

    public Boolean getPreference() {
        return this.preference;
    }
}
