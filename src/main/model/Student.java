package model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private static String studying1 = "the student is studying";
    private static String studying2 = "the student is studying";
    private static String relaxing1 = "the student is relaxing";
    private static String relaxing2 = "the student is relaxing";
    private static String end1 = " hates school and dropped the school!";
    private static String end2 = " is an idoit and fail to enter an university";
    private static String end3 = " got into an non-well-known university";
    private static String end4 = " got into a nice university!";
    private static String end5 = " got into 1st ranked university!";
    private static String end6 = " keeps studying and playing, so he/she missed the university entrance exam...";
    private static double courseFitpressureIndex = 0.9;
    private static double courseUnFitpressureIndex = 1.3;
    private static double courseFitKnowledgeIndex = 1.08;
    private static double courseUnFitKnowledgeIndex = 0.8;
    private static double playFitPressureIndex = 1.0;
    private static double playUnFitPressureIndex = 0.4;
    private static double loseKnowledgeWhenPlayIndex = 0.25;
    private static int totalTimeTograduate = 1000;
    private static int maxPressure = 500;
    private static int knowLedgeRangeIdoit = 0;
    private static int knowLedgeRangeFine = 200;
    private static int knowLedgeRangeKeep = 300;
    private static int knowLedgeRangeTalented = 400;
    private static int knowLedgeRangeGenius = 500;



    private String name;
    private List<Activities> schedule;
    private int time;
    private int pressure;
    private int knowledge;
    private Boolean chrType;
    private Boolean preference;
    private Boolean loveFineArt;
    private String subjectSelectionOne;
    private String subjectSelectionTwo;
    private String subjectSelectionThree;

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
        this.loveFineArt = Math.random() < 0.1;
        this.subjectSelectionOne = "A";
        this.subjectSelectionTwo = "B";
        this.subjectSelectionThree = "C";
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


    //EFFECT: printing out all names and times of the activities in the schedule.
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
//                System.out.println("Game goes on");
                return false;
            }
        }
    }

    //EFFECT: 根据数值选择不同结局
    public String endChoice() {
        if (time >= (totalTimeTograduate + 50)) {
            System.out.println(name + end6);
            return end6;
        } else if (maxPressure <= pressure) {
            System.out.println(name + end1);
            return end1;
        } else if (knowledge <= knowLedgeRangeIdoit) {
            System.out.println(name + end2);
            return end2;
        } else if (knowledge <= knowLedgeRangeFine) {
            System.out.println(name + end3);
            return end3;
        } else if (knowledge <= knowLedgeRangeTalented) {
            System.out.println(name + end4);
            return end4;
        } else {
            System.out.println(name + end5);
            return end5;
        }
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
        if (knowledge <= knowLedgeRangeIdoit) {
            System.out.println("Knowledge Bar: " + "idoit");
        }
        if ((knowLedgeRangeIdoit < knowledge) && (knowLedgeRangeFine >= knowledge)) {
            System.out.println("Knowledge Bar: " + "fine");
        }
        if ((knowLedgeRangeFine < knowledge) && (knowLedgeRangeKeep >= knowledge)) {
            System.out.println("Knowledge Bar: " + "Keep going");
        }
        if ((knowLedgeRangeKeep < knowledge) && (knowLedgeRangeTalented >= knowledge)) {
            System.out.println("Knowledge Bar: " + "Good job");
        }
        if ((knowLedgeRangeTalented < knowledge) && (knowLedgeRangeGenius >= knowledge)) {
            System.out.println("Knowledge Bar: " + "Talented learner");
        }
        if ((knowLedgeRangeGenius < knowledge)) {
            System.out.println("Knowledge Bar: " + "Genius");
        }
    }

    //EFFECT: 根据activity类型播放不同的学生动画.
    public void studentAnime(Activities a) throws InterruptedException {
        Boolean courseOrPlay = a.getcourseOrPlay();
        if (courseOrPlay) {
            for (int i = 0; i <= 2; i++) {
                System.out.println(studying1);
                Thread.sleep(500);
                System.out.println(studying2);
            }
        } else {
            for (int i = 0; i <= 2; i++) {
                System.out.println(relaxing1);
                Thread.sleep(500);
                System.out.println(relaxing2);
            }
        }
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

    public Boolean getChr() {
        return this.chrType;
    }

    public void setPreference(Boolean prefer) {
        this.preference = prefer;
    }

    public Boolean getPreference() {
        return this.preference;
    }

    public Boolean getLoveFineArt() {
        return this.loveFineArt;
    }

    //setters
    public void setChr(Boolean chr) {
        this.chrType = chr;
    }

    //EFFECT: 父母同意时的选科
    public void setSelectionAgree(String prefer) {
        if (prefer.equals("a")) {
            subjectSelectionOne = "history";
            subjectSelectionTwo = "geology";
            subjectSelectionThree = "politics";
        }
    }

    public void setSelectionDisAgree(String prefer) {
        if (prefer.equals("a")) {
            subjectSelectionOne = "physics";
            subjectSelectionTwo = "";
            subjectSelectionThree = "";

        }
    }
}
