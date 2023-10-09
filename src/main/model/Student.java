package model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private static final String studying1 = "the student is studying";
    private static final String studying2 = "the student is studying";
    private static final String relaxing1 = "the student is relaxing";
    private static final String relaxing2 = "the student is relaxing";
    private static final double courseFitpressureIndex = 0.5;
    private static final double courseUnFitpressureIndex = 1.3;
    private static final double playFitPressureIndex = 1.0;
    private static final double playUnFitPressureIndex = 0.4;
    private static final int totalTimeTograduate = 1000;
    private static final int maxPressure = 600;
    private static final int parentAgreepressureReduce = 50;
    private static final int parentAgreepressurePlus = 30;
    private static final int parentDisagreepressurePlus = 80;



    private final String name;
    private final List<Activities> schedule;
    private int time;
    private int pressure;
    private final Knowledge knowledge;
    private Boolean chrType;
    private Boolean preference;
    private Boolean loveFineArt;
    private String subjectSelectionOne;
    private String subjectSelectionTwo;
    private String subjectSelectionThree;
    private Boolean scienceOrArtForExam;

    //EFFECT: create a new student object with name, accumulated time, pressure and knowledge and an empty schedule.
    // chrType is the type of character of the student, true: love sport, extroverted. false: love arts, introverted.
    // preference is the subject preference of the student, true: love science. false: love arts.
    public Student(String name) {
        this.name = name;
        this.schedule = new ArrayList<>();
        this.time = 0;
        this.pressure = 0;
        this.knowledge = new Knowledge();
        this.chrType = Math.random() < 0.5;
        this.preference = Math.random() < 0.5;
        this.loveFineArt = Math.random() < 0.05;
        this.subjectSelectionOne = "A";
        this.subjectSelectionTwo = "B";
        this.subjectSelectionThree = "C";
        this.scienceOrArtForExam = true;
    }


    //EFFECT: printing out the profile of the student (includes chrType prefer name and Subjects for final exam)
    public void studentProfile() {
        String name = this.name;
        String subjectSelection1 = subjectSelectionOne;
        String subjectSelection2 = subjectSelectionTwo;
        String subjectSelection3 = subjectSelectionThree;
        String character;
        String prefer;
        if (chrType) {
            character = "outgoing";
        } else {
            character = "introverted";
        }
        if (loveFineArt) {
            prefer = "fine art";
        } else if (preference) {
            prefer = "science";
        } else {
            prefer = "art";
        }
        System.out.println("Student Profile:" + "\n" + "-Student Name: " + name + "\n"
                         + "-Subjects for final exam: " + "Mandarin, Math, English, "
                         + subjectSelection1 + ", " + subjectSelection2 + ", " + subjectSelection3 + "\n"
                         + "-Subject Preference: " + prefer + "\n"
                         + "-Character: " + character);
    }


    //REQUIRES: Activities must be one of the name in the menu
    //MODIFIES: this
    //EFFECT: Add activity "a" to the schedule of the student, add time,knowledge and pressure to the student.
    public void addActivity(Activities a) {
        schedule.add(a);
        addTime(a);
        knowledge.addKnowledge(a, subjectSelectionOne, subjectSelectionTwo, subjectSelectionThree, preference);
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


    //EFFECT: check if the game end. Return false, if does not end, true if end. The game will end if the pressure
    // exceeds or equals to the maximum. Or the total time equals to the maximum.
    public Boolean detectEnding() {
        if (maxPressure <= pressure) {
//            System.out.println("exceed pressure limit");
            return true;
        } else {
            return (totalTimeTograduate <= time);
        }
    }

    //REQUIRES: the time of the student must equals to the maximum, so that this method will be called to
    // print out the ends.
    //EFFECT:print out the end according to the score of the student.
    public void endChoice(int score) {
        if (score <= 300) { //fail
            System.out.println(name + " got a really bad mark on the final and no college provides offer!");
        } else if (score <= 400) { //3ben
            System.out.println(name + " goes for a college.");
        } else if (score <= 530) { //2ben
            System.out.println(name + " goes for an university.");
        } else if (score <= 600) { //1ben
            System.out.println(name + " goes for an key university.");
        } else if (score <= 625) { //211
            System.out.println(name + " goes for a world-class universities.");
        } else if (score <= 680) { //985
            System.out.println(name + " goes for a 'Double First-Class' university.");
        } else if (score <= 700) { //1st
            System.out.println(name + " goes for Tsinghua University.");
        } else {
            System.out.println(name + " is the Number one scholar and can go for any university he/she wants!");
        }
    }

    public void endFineArt() {
        System.out.println("art student end");
    }

    //EFFECT:
    public void addTime(Activities a) {
        int actTime = a.getTime();
        time += actTime;
    }


    //EFFECT: pressure
    public void addPressure(Activities a) {
        Boolean courseOrPlay = a.getcourseOrPlay();
        Boolean  activityType = a.getActivityType();
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


    //EFFECT: Print out the time bar, pressure bar and knowledge bar(include all three selections)
    public void updateBars() {
        int pressureNow = pressure;
        int timeNow = time;
        int s1Knowledge = knowledge.getS1Knowledge();
        int s2Knowledge = knowledge.getS2Knowledge();
        int s3Knowledge = knowledge.getS3Knowledge();
        int mandarin = knowledge.getMandarinKnowledge();
        int math = knowledge.getMathKnowledge();
        int english = knowledge.getEnglishKnowledge();
        System.out.println("Time Bar: " + timeNow + "/" + totalTimeTograduate);
        System.out.println("Pressure Bar: " + pressureNow + "/" + maxPressure);
        System.out.println("Knowledge Bar: " + "- " + subjectSelectionOne + ":" +  s1Knowledge);
        System.out.println("               " + "- " + subjectSelectionTwo + ":" +  s2Knowledge);
        System.out.println("               " + "- " + subjectSelectionThree + ":" +  s3Knowledge);
        System.out.println("               " + "- " + "Mandarin" + ":" +  mandarin);
        System.out.println("               " + "- " + "Math" + ":" +  math);
        System.out.println("               " + "- " + "English" + ":" +  english);
    }

    //EFFECT: rendering different student images depend on the activity type.
    // (if the activity is "course", then rendering the images that the student is studying.
    // if the activity type is "play", then rendering the student is playing.)
    // Noted that there are two images for each type of activity, they are alternately displayed to make it
    // looks like an animation. In phase1, the images is represented by strings.
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

    //REQUIRES: fstOrScnd must be either 1 or 2. prefer must be one of "a" "s" and "f"
    //MODIFIED: this
    //EFFECT: set the subject selections in the final exam for the student when the user(parent) agree with
    // their own choice. If the student prefer is art (prefer = a), set the selections to "history" "geology" and
    // "politics" respectively. if the student prefer science (prefer = s), set the selection to "physics" "biology"
    // and "chemistry" respectively. if the student prefer is "f", directly comes to the end (call endFineArt()).
    // fstOrScnd stands for whether the user agree with the student's choice in the first trial or the second trial.
    // if the fstOrScnd = 1, then decrease the student's pressure by "parentAgreepressureReduce". If fstOrScnd = 2,
    // increase the student pressure by "parentAgreepressurePlus".
    public void setSelectionAgree(String prefer, int fstOrScnd) {
        if (prefer.equals("a")) {
            subjectSelectionOne = "history";
            subjectSelectionTwo = "geology";
            subjectSelectionThree = "politics";
            setScienceOrArtForExam(false);
            if (fstOrScnd == 1) {
                pressure -= parentAgreepressureReduce;
            } else {
                pressure += parentAgreepressurePlus;
            }
        } else if (prefer.equals("s")) {
            subjectSelectionOne = "physics";
            subjectSelectionTwo = "biology";
            subjectSelectionThree = "chemistry";
            setScienceOrArtForExam(true);
            if (fstOrScnd == 1) {
                pressure -= parentAgreepressureReduce;
            } else {
                pressure += parentAgreepressurePlus;
            }
        } else {
            endFineArt();
        }
    }


    //REQUIRES: prefer must be one of "a"(stand for art) "fa"(stand for student prefer fineart but user want the student
    // to choose art) or "fs"(stand for student prefer fine art but user want the student to choose science).
    // Noted that the parameter "prefer" cannot be "s" (stand for science), because if the student prefer science,
    // user must agree with the student's choice.
    //MODIFIED: this
    //EFFECT: set the subject selections in the final exam for the student when the user(parent) disagree with
    // their own choice. If the student prefer is art (prefer = "a"), set the selections to "history" "geology" and
    // "politics" respectively. if the student prefer fine art but user want the student to choose art (prefer = "fa"),
    // set the selection to "history" "geology" and "politics" respectively. if the student prefer fine art but user
    // want the student to choose science (prefer = "fs"), set the selection to "physics" "biology"
    // and "chemistry" respectively.
    // Pressure increment: in this method, increase the student pressure by "parentDisagreepressurePlus" if prefer = "a"
    // Increase the student pressure by "parentDisagreepressurePlus * 3" if prefer = "fa"
    // Increase the student pressure by "parentDisagreepressurePlus * 5" if prefer = "fs",
    public void setSelectionDisAgree(String prefer) {
        if (prefer.equals("a")) {
            subjectSelectionOne = "physics";
            subjectSelectionTwo = "biology";
            subjectSelectionThree = "chemistry";
            setScienceOrArtForExam(true);
            pressure += parentDisagreepressurePlus;
        } else if (prefer.equals("fa")) {
            subjectSelectionOne = "history";
            subjectSelectionTwo = "geology";
            subjectSelectionThree = "politics";
            setScienceOrArtForExam(false);
            pressure += parentDisagreepressurePlus * 3;
        } else {
            subjectSelectionOne = "physics";
            subjectSelectionTwo = "biology";
            subjectSelectionThree = "chemistry";
            setScienceOrArtForExam(false);
            pressure += parentDisagreepressurePlus * 5;
        }
    }


    //REQUIRES: time must be greater or equals to 0.
    //EFFECT: detect whether the time is less than or equals to the remaining time. If yes return true, vice versa.
    public boolean validTime(int time) {
        return time <= (totalTimeTograduate - this.time);
    }


    //REQUIRES: time of the student must be less than totalTimeTograduate, since this method will only be called as
    // the game does not come to the end. And time must be greater or equals to 0.
    //EFFECT: return the remaining time of the student (totalTimeTograduate - this.time).
    public int getRemainingTime() {
        return (totalTimeTograduate - this.time);
    }


    //REQUIRES: this method will only be called when the game comes to the end. So either the pressure exceeds limit
    // or the rime exceed limit will happen.
    //EFFECT: return true if the pressure exceeds limit, false otherwise.
    public Boolean dropOrExam() {
        return (pressure >= maxPressure);
    }


    //getters and setters:

    //EFFECT: get name of the student
    public String getName() {
        return this.name;
    }

    //EFFECT: get the activities added for the student
    public List<Activities> getSchedule() {
        return this.schedule;
    }

    //EFFECT: get the time already passed of the student
    public int getTime() {
        return this.time;
    }

    //EFFECT: get knowledge of the student
    public Knowledge getKnowledge() {
        return this.knowledge;
    }

    //EFFECT: get pressure of the student
    public int getPressure() {
        return this.pressure;
    }

    //EFFECT: get character type of the student
    public Boolean getChr() {
        return this.chrType;
    }

    //EFFECT: get subject preference of the student
    public Boolean getPreference() {
        return this.preference;
    }

    //EFFECT: get SubjectSelectionOne
    public String getSubjectSelectionOne() {
        return this.subjectSelectionOne;
    }

    //EFFECT: get SubjectSelectionTwo
    public String getSubjectSelectionTwo() {
        return this.subjectSelectionTwo;
    }

    //EFFECT: get SubjectSelectionThree
    public String getSubjectSelectionThree() {
        return this.subjectSelectionThree;
    }

    //EFFECT: get whether the student love fine art
    public Boolean getLoveFineArt() {
        return this.loveFineArt;
    }

    //EFFECT: get whether the student choose to take science or art exam
    public Boolean getScienceOrArtForExam() {
        return this.scienceOrArtForExam;
    }

    //setters
    //EFFECT: set character type of the student
    public void setChr(Boolean chr) {
        this.chrType = chr;
    }

    //MODIFIED:this
    //EFFECT: set subject preference of the student
    public void setPreference(Boolean prefer) {
        this.preference = prefer;
    }

    //MODIFIED:this
    //EFFECT: set loveFineArt of the student
    public void setFineart(boolean b) {
        this.loveFineArt = b;
    }

    //MODIFIED:this
    //EFFECT: set pressure of the student
    public void setPressure(int p) {
        this.pressure = p;
    }

    //MODIFIED:this
    //EFFECT: set time of the student
    public void setTime(int i) {
        this.time = i;
    }

    //EFFECT: set whether the student choose to take science or art exam, true for science false for art.
    public void setScienceOrArtForExam(Boolean b) {
        this.scienceOrArtForExam = b;
    }
}
