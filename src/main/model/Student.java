package model;

import model.Exceptions.PressureExceedException;
import model.Exceptions.TimeUpException;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.GameStarter;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
//Represent a student object.
// **Field explanation**:
// 1.name (name of the student),
// 2.schedule (a list of activities that have been added by the user),
// 3.time (the amount of time in hrs that already passed),
// 4.pressure (the pressure of the student, represented by an integer),
// 5.knowledge (the knowledge of the student, represented by an integer),
// 6.chrType (type of character represented by a boolean value. True means the student is extroverted and likes outdoor
// activities for relaxing while false means the student is introverted and loves indoor activities. This value is
// randomly generated in the constructor.)
// 7.preference (the subject preference of the student. True means the student likes science while false means the
//   student prefers arts. This value is randomly generated),
// 8.loveFineArt (represent whether the student loves fine art or not. True means yes while false means no. Noted that
//   this value is randomly generated, but the probability of 'true' is significantly smaller than the 'false' value.
//   This is kind of leaving an easter egg for the player),
// 9.subjectSelectionOne (represent the first selective subject that the student will take in the final exam)
// 10.subjectSelectionTwo (represent the second selective subject that the student will take in the final exam),
// 11.subjectSelectionThree (represent the third selective subject that the student will take in the final exam).
// 12.knowledge (represent the knowledge value of the student.)
//
// **Constant explanation**:
// courseFitpressureIndex: the pressure of the student will increase by (this index * time) when the course type is the
// one that the student likes.
// courseUnFitpressureIndex: the pressure of the student will increase by (this index * time) when the course type is
// the one that the student does not like.
// playFitPressureIndex: the pressure of the student will decrease by (this index * time) when the game type is the
// one that the student likes.
// playUnFitPressureIndex: the pressure of the student will decrease by (this index * time) when the game type is the
// one that the student does not like.
// totalTimeTograduate: the maximum time of the student to graduate.
// maxPressure: the maximum pressure of the student to endure.
// parentAgreepressureReduce: the pressure of the student will reduce by this constant if the user agree with
// their choice.
// parentDisagreepressurePlus: the pressure of the student will incease by this constant if the user disagree with
// their choice.

public class Student {
    private static final String studying1 = "the student is studying";//should be images showing
    private static final String studying2 = "the student is studying";//a studying student in visualization phase
    private static final String relaxing1 = "the student is relaxing";//should be images showing
    private static final String relaxing2 = "the student is relaxing";//a relaxing student in visualization phase
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
    private Knowledge knowledge;
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

    //MODIFIED: JSONObject
    //EFFECT: write all information about the current student and the gender into a json object.
    public JSONObject toJson(String gender) {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("time", time);
        json.put("pressure", pressure);
        json.put("chrType", chrType);
        json.put("name", name);
        json.put("preference", preference);
        json.put("loveFineArt", loveFineArt);
        json.put("subjectSelectionOne", subjectSelectionOne);
        json.put("subjectSelectionTwo", subjectSelectionTwo);
        json.put("subjectSelectionThree", subjectSelectionThree);
        json.put("scienceOrArtForExam", scienceOrArtForExam);
        json.put("schedule", scheduleToJson());
        json.put("knowledge", knowledgeToJson());
        json.put("gender", gender);
        return json;
    }

    //MODIFIED: JSONObject
    //EFFECT: write knowledge as a json object and return it.
    private JSONObject knowledgeToJson() {
        JSONObject json = knowledge.toJson();
        return json;
    }

    //MODIFIED: JSONObject
    // EFFECTS: returns activities in this schedule as a JSON array
    private JSONArray scheduleToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Activities a : schedule) {
            jsonArray.put(a.toJson());
        }
        return jsonArray;
    }


    //EFFECT: assigning values to name, subjectSelection1, subjectSelection2, subjectSelection3, character, prefer,
    // and pass these values to showProfile() method to print out the profile.
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public List<String> studentProfile() {
        List<String> profile = new ArrayList<>();
        GameStarter toCall = new GameStarter();
        String name = this.name;
        String subjectSelection1 = subjectSelectionOne;
        String subjectSelection2 = subjectSelectionTwo;
        String subjectSelection3 = subjectSelectionThree;
        String character;
        String prefer;
        if (chrType) {
            character = "outgoing";
            profile.add("Character: outgoing");
        } else {
            character = "introverted";
            profile.add("Character: introverted");
        }
        if (loveFineArt) {
            prefer = "fine art";
            profile.add("Subject Preference: fine art");
        } else if (preference) {
            prefer = "science";
            profile.add("Subject Preference: science");
        } else {
            prefer = "art";
            profile.add("Subject Preference: art");
        }
        profile.add("Subject Selection1: " + subjectSelection1);
        profile.add("Subject Selection2: " + subjectSelection2);
        profile.add("Subject Selection3: " + subjectSelection3);
//        toCall.showProfile(name, subjectSelection1, subjectSelection2, subjectSelection3, character, prefer);
        return profile;
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

    //REQUIRES: Activities must be one of the name in the menu
    //MODIFIES: this
    //EFFECT: Add activity "a" to the schedule of the student, DO NOTHING ELSE about the other fields of
    // student.
    public void addSimplySchedule(Activities a) {
        schedule.add(a);
    }


    //EFFECT: printing out all names and times of the activities in the schedule.
    public List<String> showSchedule() {
        GameStarter toCall = new GameStarter();
        List<String> showSchedule = new ArrayList<>();
        String actName;
        int actTime;
        for (Activities a : schedule) {
            actName = a.getName();
            actTime = a.getTime();
            showSchedule.add(actName + " " + actTime);
            toCall.showOneActivity(actName, actTime);
        }
        return showSchedule;
    }


    // EFFECT: check if the game end. Return false, if does not end, true if end. The game will end if the pressure
    // exceeds or equals to the maximum. Or the total time equals to the maximum.
    public void detectEnding() throws PressureExceedException, TimeUpException {
        if (maxPressure <= pressure) {
            throw new PressureExceedException("pressure exceed limit");
        } else if (totalTimeTograduate <= time) {
            throw new TimeUpException("time exceed limit");
        }
//        if (maxPressure <= pressure) {
//            return true;
//        } else {
//            return (totalTimeTograduate <= time);
//        }
    }

    //REQUIRES: the time of the student must equals to the maximum, so that this method will be called to
    // print out the ends.
    //EFFECT:print out the end according to the score of the student.
    public void endChoice(int score) {
        GameStarter toCall = new GameStarter();
        if (score <= 300) { //fail
            toCall.failEnd(name);
        } else if (score <= 400) { //3ben
            toCall.normalEnd(name);
        } else if (score <= 530) { //2ben
            toCall.uppernormalEnd(name);
        } else if (score <= 600) { //1ben
            toCall.keyUniversityEnd(name);
        } else if (score <= 625) { //211
            toCall.wrdClassEnd(name);
        } else if (score <= 680) { //985
            toCall.dblFstClassEnd(name);
        } else if (score <= 700) { //1st
            toCall.tsinghuaEnd(name);
        } else {
            toCall.numOneEnd(name);
        }
    }

    // EFFECTS: Printing out the end of the fine art student
    public void endFineArt() {
        GameStarter toCall = new GameStarter();
        toCall.endFineArt(name);
    }

    // REQUIRES: the time of the activities can not be greater than the remaining time of the student.
    // MODIFIED: this
    // EFFECT: add time of the student by the amount of time of the activities
    public void addTime(Activities a) {
        int actTime = a.getTime();
        time += actTime;
    }

    //MODIFIED: this
    //EFFECT: add or reduce pressure of the student by the (amount of time of the activities * corresponding index)
    // if the activity is course (courseOrPlay == true) then add pressure.
    // - course type fit student preference: pressure += (int)(courseFitpressureIndex * actTime);
    // - course type doesn't fit student preference: pressure += (int)(courseUnFitpressureIndex * actTime);
    // if the activity is game (courseOrPlay == false) then reduce pressure.
    // - game type fit student character: pressure -= (int)(playFitPressureIndex * actTime);
    // - game type doesn't fit student character: pressure -= (int)(playUnFitPressureIndex * actTime);
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


    //EFFECT: Assigning values to timeNow, pressureNow, s1Knowledge, s2Knowledge, s3Knowledge, mandarin, math, english,
    //  totalTimeTograduate, maxPressure, subjectSelectionOne, subjectSelectionTwo, subjectSelectionThree and pass
    // them to the printout method in GameStarter (printOutBars).
    public void updateBars() {
        GameStarter toCall = new GameStarter();
        int pressureNow = pressure;
        int timeNow = time;
        int s1Knowledge = knowledge.getS1Knowledge();
        int s2Knowledge = knowledge.getS2Knowledge();
        int s3Knowledge = knowledge.getS3Knowledge();
        int mandarin = knowledge.getMandarinKnowledge();
        int math = knowledge.getMathKnowledge();
        int english = knowledge.getEnglishKnowledge();
        toCall.printOutBars(timeNow, pressureNow, s1Knowledge, s2Knowledge, s3Knowledge, mandarin, math, english,
                totalTimeTograduate, maxPressure, subjectSelectionOne, subjectSelectionTwo, subjectSelectionThree);
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

    //MODIFIED:this
    //EFFECT: set whether the student choose to take science or art exam, true for science false for art.
    public void setScienceOrArtForExam(Boolean b) {
        this.scienceOrArtForExam = b;
    }

    //MODIFIED:this
    //EFFECT: set the selection 1 for exam.
    public void setSubjectSelectionOne(String s) {
        this.subjectSelectionOne = s;
    }

    //MODIFIED:this
    //EFFECT: set the selection 1 for exam.
    public void setSubjectSelectionTwo(String s) {
        this.subjectSelectionTwo = s;
    }

    //MODIFIED:this
    //EFFECT: set the selection 3 for exam.
    public void setSubjectSelectionThree(String s) {
        this.subjectSelectionThree = s;
    }

    //MODIFIED: this
    //EFFECT: set the knowledge for the student.
    public void setKnowledge(Knowledge k) {
        this.knowledge = k;
    }
}
