package model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studying1 = "the student is studying";
    private String studying2 = "the student is studying";
    private String relaxing1 = "the student is relaxing";
    private String relaxing2 = "the student is relaxing";
    private String end1 = "student gets into the 1st ranked university";
    private String end2 = "student gets into the 2nd ranked university";
    private String end3 = "student gets into the 3nd ranked university";
    private String end4 = "student gets into an university";
    private String end5 = "student does not get into an university";
    private String end6 = "dropped the school";

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
        addTime();
        addKnowledge();
        addPressure();
        updateBars(a);
    }

    public void showSchedule() {
        System.out.println("假装这是一个时间表>-<");
    }

    //EFFECT: check if the game end
    public Boolean detectEnding() {
        return false;
    }

    //EFFECT: 根据数值选择不同结局
    public void endChoice() {

    }

    //EFFECT: 加时间
    public void addTime() {

    }

    //EFFECT: 算总时间
    public void totalTime() {

    }

    //EFFECT: 加pressure
    public void addPressure() {

    }

    //EFFECT: 算总压力
    public void totalPressure() {

    }

    //EFFECT: 加knowledge
    public void addKnowledge() {

    }

    //EFFECT: 算总知识
    public void totalKnowledge() {

    }

    //EFFECT: 更新time bar, pressure bar, knowledge bar 并 print out.
    public void updateBars(Activities a) {

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
