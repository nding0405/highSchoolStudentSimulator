package ui;

import model.Activities;
import model.Student;

import java.util.Scanner;
// Represent the starter of the game. This class will go through the all process of the high school student simulator.

public class GameStarter extends Thread {
    private Scanner input;
    private Student student;
    private String gender;
    private String studentName;

    private static final String openmindedOne =
            "Background: Now, assuming you are a parent from a middle-class Chinese family.";
    private static final String openmindedTwo =
            "Every students need to take a final exam at the end of their third year of high school."
                    + "\n" + "The university they can enter is purely depend on the score of that one-chance exam.";
    private static final String openmindedThree = "The subjects of the final exam are: 'Mandarin' 'Math' 'English' "
            + "AND either 'Combined Science(includes Physics Biology and Chemistry)'"
            + "\n" + "or 'Combined Arts(includes Geology History and Politics)'";
    private static final String openmindedFour = "The choice for art and science will influence the major they can "
            + "apply for their university." + "\n" + "Art student cannot choose science major and vice versa.";
    private static final String openmindedFive = "However, in China, there are relatively less job opportunities for "
            + "students in art than student in science.";
    private static final String openmindedSix = "Please answer the following questions as they will be crucial for "
            + "your child's subject selection.";
    private static final String scienceAnswer2 = "You: Of course you must choose science!"
            + " It's essential for you to secure a well-paid job " + "\n" + "in the future! "
            + "You MUST study hard in your third year to get into a good university; "
            + "otherwise, you'll be letting your parents down! Everything I do is for your sake!";
    private static final String scienceAnswer1 = "You: Of course, I give fully respect to every choice you make. "
            + "As your parent, we are your strongest support.";
    private static final String findArtAnswer1 = "You: Definitely yes my sweetheart. I will be so happy "
            + "if you can persue your dream!";
    private static final String findArtAnswer2 = "You: Absolutely not! Do you even realize how much will it take to "
            + "study arts? Will you be able to find a job afterwards? There's absolutely no room for negotiation on "
            + "this matter! (in a stern tone)";
    private static final String be1 = "BADENDING: Your child hates you and school, he/she dropped school!";
    private static final String be2 = "BADENDING: Your child hates school and dropped it.";

    // EFFECTS: create a new GameStarter object.
    public GameStarter() {
    }

    @Override
    public void run() {
        try {
            runThegame();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    // MODIFIES: this, Activities
    // EFFECTS: going through the whole general process of the game.
    public void runThegame() throws InterruptedException {
        introduction();
        Thread.sleep(1000);
        initializeStudent();
        Thread.sleep(1000);
        initializeParent();
        Thread.sleep(1000);
        backGroundInfo();
        input.nextLine();
        student.studentProfile();
        input.nextLine();
        String operation;
        boolean goOn = true;
        while (goOn) {
            showInstructions();
            operation = input.next();
            detectValidOperation(operation);
            processOperation(operation);
            goOn = !(student.detectEnding());
        }
        gameEndNotification();
    }

    //EFFECTS: printing out the notification for the user that tells the game has ended. Call "student.endChoice()"
    // method to have the end.
    private void gameEndNotification() {
        Boolean dropOrExam = student.dropOrExam();
        if (dropOrExam) {
            System.out.println("GAME END: The pressure of the student exceeds limit...");
            dropSchoolEnd();
        } else {
            System.out.println("It's time to take the final exam!(BACKSPACE to go on)");
            input.nextLine();
            int totScore = student.getKnowledge().takeExam(student.getScienceOrArtForExam());
            student.endChoice(totScore);
        }
    }

    //REQUIRES: the pressure of the student is greater or equals to the maximum.
    //EFFECT: printing out the end that the student drops the school
    private void dropSchoolEnd() {
        System.out.println(studentName + ": Do I have to go for school? "
                + "I've done so much and I'm tired, but I still can't make you satisfy! "
                + "I don't want to go to school anymore!");
        input.nextLine();
        System.out.println("A- It's not the excuse.");
        System.out.println("B- I'm sorry I give you so much pressure");
        String choice = input.next();
        while (!choice.equalsIgnoreCase("a") && !choice.equalsIgnoreCase("b")) {
            System.out.println("Invalid input! Please enter 'A' or 'B'!(not case sensitive >v<!)");
            choice = input.next();
        }
        if (choice.equalsIgnoreCase("a")) {
            System.out.println("You: It's not the excuse, you are just too lazy!");
            System.out.println(studentName + ": ......" + "\n" + be1);
        } else {
            System.out.println("You: I'm sorry I give you so much pressure, I won't do that again...");
            System.out.println(studentName + ": Thank you " + gender + "but I'm really tired..." + "\n" + be2);
        }

    }


    //EFFECTS: print out background information about the "Nationwide Unified Examination for Admissions to General
    // Universities and Colleges"
    public void backGroundInfo() {
        input.nextLine();
        System.out.println(openmindedOne + "(press BACKSPACE to go on.)");
        input.nextLine();
        System.out.println("You have a child named " + studentName
                + " who is about to enter the third year of high school and swimming in the pressure of graduation."
                + "(press BACKSPACE to go on.)");
        input.nextLine();
        System.out.println(openmindedTwo + "(press BACKSPACE to go on.)");
        input.nextLine();
        System.out.println(openmindedThree + "(press BACKSPACE to go on.)");
        input.nextLine();
        System.out.println(openmindedFour + "(press BACKSPACE to go on.)");
        input.nextLine();
        System.out.println(openmindedFive + "(press BACKSPACE to go on.)");
        input.nextLine();
        System.out.println(openmindedSix + "(press BACKSPACE to go on.)");
        input.nextLine();
        preferenceSetup();
    }

    //EFFECT: get the subject preference of the student and send it to  openMindQuestions;
    public void preferenceSetup() {
        String prefer;
        if (student.getLoveFineArt()) {
            prefer = "fine art";
        } else if (student.getPreference()) {
            prefer = "science";
        } else {
            prefer = "arts";
        }
        openMindQuestions(prefer);
    }


    //EFFECT: ask the user whether they agree their student to select the subjects they prefer. If yes then go to the
    // corresponding "agree" methods, go to corresponding "disagree" methods if the user disagree.
    public void openMindQuestions(String prefer) {
        System.out.println(studentName + ":" + gender
                + ", I'm about to enter the third year of high school. "
                + "Can I talk to you about the subject selection...?");
        input.nextLine();
        System.out.println("Enter: yes");
        while (!input.next().equalsIgnoreCase("yes")) {
            System.out.println("Invalid input! Please enter 'yes'!");
            input.next();
        }
        System.out.println("You: Of course.");
        if (prefer.equals("fine art")) {
            fineArtQuestions();
        } else if (prefer.equals("science")) {
            scienceQuestions();
        } else {
            artQuestions();
        }
    }

    //REQUIRE: the student field in this GameStarter object must prefer to learn art
    //MODIFIED: this, student
    //EFFECT: ask whether the user would like their student to learn art. If yes then will call artAgree().
    // If no then will call artDisagree() to continue asking the user some questions.
    private void artQuestions() {
        String choice;
        System.out.println(studentName + ":" + gender
                + " I prefer arts and I'm more inclined to choose subjects like politics and history.");
        input.nextLine();
        System.out.println("A- Of course! I supports any choice you make!'");
        System.out.println("B- Well, we need to talk about it.");
        choice = input.next();
        while (!choice.equalsIgnoreCase("a") && !choice.equalsIgnoreCase("b")) {
            System.out.println("Invalid input! Please enter 'A' or 'B'!(not case sensitive >v<!)");
            choice = input.next();
        }
        if (choice.equals("b") || choice.equals("B")) {
            System.out.println("You: But dear, it's challenging to find good job opportunities in arts. " + "\n"
                    + "What 's gonna happen if you have trouble finding jobs in the future?");
            artDisagree();
        } else {
            System.out.println("You: As parents, we want you to be happy, so we're giving you freedom to choose. "
                    + "\n"
                    + "However, you need to consider your choices carefully and take responsibility for them.");
            artAgree();
        }
    }

    //REQUIRE: the student field in this GameStarter object must prefer to learn art and the user agree their student
    // to learn art in artQuestions().
    //MODIFIED: this, student
    //EFFECT: print out student text and initialize the student pressure and subject selection for the final exam.
    private void artAgree() {
        System.out.println(studentName + ": " + "I love you " + gender + "! (deeply hugged you)");
        student.setSelectionAgree("a", 1);
    }

    //REQUIRE: the student field in this GameStarter object must prefer to learn art and the user disagree with their
    // student to learn art in artQuestions().
    //MODIFIED: this, student
    //EFFECT: continue asking the user about their student subject selection questions and initialize the student
    // pressure and subject selection in regard to the user choice.
    private void artDisagree() {
        System.out.println(studentName + ": " + "But I love it " + gender
                + "! I promise I will do it well in the college entrance exam!");
        input.nextLine();
        System.out.println("A- (sigh, reluctantly agree.)");
        System.out.println("B- Strongly disagree.");
        String choice = input.next();
        while (!choice.equalsIgnoreCase("a") && !choice.equalsIgnoreCase("b")) {
            System.out.println("Invalid input! Please enter 'A' or 'B'!(not case sensitive >v<!)");
            choice = input.next();
        }
        if (choice.equals("a")) {
            System.out.println("Well ok (sigh)... But you have to promise you will work hard and get a high mark.");
            input.nextLine();
            System.out.println(studentName + ": I promise " + gender + "!");
            student.setSelectionAgree("a", 2);
        } else {
            System.out.println("You: Have you ever thought about me if you choose arts?" + "\n"
                    + "Do you know how difficult it is to find a job in arts?" + "No one will feed you if you lose your"
                    + "job!" + "\n" + "You must choose science!");
            input.nextLine();
            System.out.println(studentName + ": (lowering head in silence)");
            student.setSelectionDisAgree("a");
        }
    }

    //REQUIRE: the student field in this GameStarter object must prefer to learn science
    //MODIFIED: this, student
    //EFFECT: ask why does the user would like their student to learn science. And initialize the student pressure
    // in regard to the users' choice.
    private void scienceQuestions() {
        String choice;
        System.out.println(studentName + ":" + gender + " I prefer science and I'm more inclined to choose subjects "
                + "like physics chemistry and biology.");
        input.nextLine();
        System.out.println("A- Of course! I agree because I love you.");
        System.out.println("B- Yes, I agree because it's gonna be easier to find a job and we need money.");
        choice = input.next();
        while (!choice.equalsIgnoreCase("a") && !choice.equalsIgnoreCase("b")) {
            System.out.println("Invalid input! Please enter 'A' or 'B'!(not case sensitive >v<!)");
            choice = input.next();
        }
        if (choice.equalsIgnoreCase("b")) {
            System.out.println(scienceAnswer2);
            System.out.println(studentName + ": Why do you always give me so much pressure!"
                    + "(sad and angry, run back to his/her room)");
            student.setSelectionAgree("s", 2);
        } else {
            System.out.println(scienceAnswer1);
            System.out.println(studentName + ": I'm so happy and I love you...(smile and hugged you)");
            student.setSelectionAgree("s", 1);
        }
    }

    //REQUIRE: the student field in this GameStarter object must prefer to learn fine art
    //MODIFIED: this, student
    //EFFECT: ask whether the user would like their student to learn fine art. If yes then will call fine art student
    // end and end the game. If no then will call other methods to continue asking the user some questions.
    private void fineArtQuestions() {
        String choice;
        input.nextLine();
        System.out.println(studentName + ":" + gender + " I really really love drawing and I want to go for "
                + "an art colleges...");
        System.out.println("A- Of course!");
        System.out.println("B- No way!");
        choice = input.next();
        while (!choice.equalsIgnoreCase("a") && !choice.equalsIgnoreCase("b")) {
            System.out.println("Invalid input! Please enter 'A' or 'B'!(not case sensitive >v<!)");
            choice = input.next();
        }
        if (choice.equalsIgnoreCase("a")) {
            System.out.println(findArtAnswer1);
            System.out.println(studentName + ": Thank you so much and I love you " + gender + "!");
            student.setSelectionAgree("f", 1);
        } else {
            System.out.println(findArtAnswer2);
            System.out.println(studentName + ": Sorry " + gender + "...(lowering head)");
            input.nextLine();
            fineArtstuParentSelection();
        }
    }

    //REQUIRE: the student field in this GameStarter object must prefer to learn fine art and the user must disagree
    // with their student to learn fine art.
    //MODIFIED: this, student
    //EFFECTS: ask the user what subjects would they like to choose for their student of the student likes fine art
    private void fineArtstuParentSelection() {
        String choice;
        System.out.println("You forced your child to quit fine art. Now please choose either art or science for "
                + studentName);
        input.nextLine();
        System.out.println("A-science");
        System.out.println("B-art");
        choice = input.next();
        while (!choice.equalsIgnoreCase("a") && !choice.equalsIgnoreCase("b")) {
            System.out.println("Invalid input! Please enter 'A' or 'B'!(not case sensitive >v<!)");
            choice = input.next();
        }
        if (choice.equals("a") || choice.equals("A")) {
            System.out.println("You chose science for your child, though him/her does not like it.");
            student.setSelectionDisAgree("fs");
        } else {
            System.out.println("You chose art for your child, though him/her does not like it.");
            student.setSelectionDisAgree("fa");
        }
    }


    // EFFECT: detect whether the input of the user is "add" or "view".
    // If it is none of them, ask the user to try again until the input is one of "add" and "view".
    public void detectValidOperation(String operation) {
        while (!(operation.equals("add") || operation.equals("view"))) {
            System.out.println("invalid input, please try again!");
            showInstructions();
            operation = input.next();
        }
    }

    // MODIFIES: this, student
    // EFFECTS: let the user add course or view schedule depending on the input.
    public void processOperation(String operation) throws InterruptedException {
        Activities a = new Activities("a",0,true,true);
        if (operation.equalsIgnoreCase("add")) {
            showSelection();
            addingInstruction();
            String activitySelection = input.next();
            while (!(a.detectValidActivity(activitySelection))) {
                System.out.println("***Invalid activity! Please try again!***");
                activitySelection = input.next();
            }
            findActivity(activitySelection, a);
            student.addActivity(a);
            System.out.println(activitySelection + " is successfully added");
            student.studentAnime(a);
        } else {
            student.showSchedule();
        }
    }

    //REQUIRES: activitySelection has to be a valid activity name(is one of the name in the static list
    // in Activities class)
    //MODIFIES: this, Activities, Student
    //EFFECT: get time for the selected activity from the user, make sure the time is valid (is an integer and the
    // value of the integer must less or equals to the remaining time of the student). Send the activitySelection and
    // time to the findActivity() method in Activities class to correctly assign all fields to the Activities object.
    public void findActivity(String activitySelection, Activities a) {
        boolean validTime = false;
        int time = 0;
        addingTimeInstruction(activitySelection);
        while (!validTime) {
            String next = input.next();
            try {
                time = Integer.parseInt(next);
                if (student.validTime(time)) {
                    validTime = true;
                } else {
                    System.out.println("This input exceed maximum!" + "Please try again!"
                            + "(Maximum = " + student.getRemainingTime() + " )");
                }
            } catch (NumberFormatException e) {
                System.out.println("This input is not an integer! Please try again!");
            }
        }
        a.findActivity(activitySelection,time);
    }

    //EFFECTS: Give the user introduction about the game
    public void introduction() {
        System.out.println("Welcome to High School Student Simulator! "
                + "Arranging schedule for the student and target to enter the 1st ranked university!");
        System.out.println("please give the student a name:");
    }

    //EFFECTS: Give the user instructions
    public void showInstructions() {
        System.out.println("\nSelect from:");
        System.out.println("\tadd -> add activities for student");
        System.out.println("\tview -> view schedule");
    }

    //EFFECTS: Show the user the course menu
    public void showSelection() {
        Activities a = new Activities("a",0,true,true);
        a.printOutActivities();
    }

    //MODIFIES: this
    //EFFECTS: create scanner object for "input", and create a new student object.
    // Tell the user the student is created when the object is created.
    public void initializeStudent() {
        input = new Scanner(System.in);
        studentName = input.next();
        student = new Student(studentName);
        Boolean studentCharacter = student.getChr();
        Boolean studentSubjectPreference = student.getPreference();
        Boolean studentFineArtPreference = student.getLoveFineArt();
        if (studentFineArtPreference) {
            System.out.println(studentName + " loves fine art.");
        } else {
            if (studentCharacter) {
                System.out.println(studentName + " likes outdoor sports for relaxing.");
            } else {
                System.out.println(studentName + " likes indoor activities for relaxing.");
            }
            if (studentSubjectPreference) {
                System.out.println(studentName + " likes science more than arts.");
            } else {
                System.out.println(studentName + " likes arts more than science.");
            }
        }
    }

    //MODIFIED: this
    //EFFECT: ask the user whether they would like to play the role of dad or mom of the student. This selection will
    // affect some print out statements.
    public void initializeParent() {
        System.out.println("Would you like to be the mom or dad of the student?");
        System.out.println("\nSelect from:");
        System.out.println("\tA -> mom");
        System.out.println("\tB -> dad");
        gender = input.next();
        gender = gender.toLowerCase();
        while (!gender.equals("a") && !gender.equals("b")) {
            System.out.println("Invalid input! Please enter 'A' or 'B'! (not case sensitive >v<!)");
            gender = input.next();
            gender = gender.toLowerCase();
        }
        if (gender.equals("a")) {
            gender = "mom";
        } else {
            gender = "dad";
        }
    }


    //EFFECTS: Show the user instruction for adding an activity.
    public void addingInstruction() {
        System.out.println("please enter the activity name which in the list above");
    }

    //
    public void addingTimeInstruction(String activityName) {
        System.out.println("please enter the time you want for this activity" + activityName);
    }

    //EFFECTS: print out the end when the student's score is less than or equals to 300
    public void failEnd(String name) {
        System.out.println(name + " got a really bad mark on the final and no college provides offer!");
    }

    //EFFECTS: print out the end when the student's score is less than or equals to 400 greater than 300
    public void normalEnd(String name) {
        System.out.println(name + " goes for a college.");
    }

    //EFFECTS: print out the end when the student's score is less than or equals to 530 greater than 400
    public void uppernormalEnd(String name) {
        System.out.println(name + " goes for an university.");
    }

    //EFFECTS: print out the end when the student's score is less than or equals to 600 greater than 530
    public void keyUniversityEnd(String name) {
        System.out.println(name + " goes for an key university.");
    }

    //EFFECTS: print out the end when the student's score is less than or equals to 625 greater than 600
    public void wrdClassEnd(String name) {
        System.out.println(name + " goes for a world-class universities.");
    }

    //EFFECTS: print out the end when the student's score is less than  or equals to 680 greater than 625
    public void dblFstClassEnd(String name) {
        System.out.println(name + " goes for a 'Double First-Class' university.");
    }

    //EFFECTS: print out the end when the student's score is less than or equals to 700 greater than 680
    public void tsinghuaEnd(String name) {
        System.out.println(name + " goes for Tsinghua University.");
    }

    //EFFECTS: print out the end when the student's score is greater than 700
    public void numOneEnd(String name) {
        System.out.println(name + " is the Number one scholar and can go for any university he/she wants!");
    }

    //EFFECTS: print out the end for fine art student.
    public void endFineArt(String name) {
        System.out.println(name + " is happy that he/she is able to learn fine art. "
                + "He/She works hard and goes for a nice art college.");
    }

    //EFFECT: printing out the profile of the student (includes chrType prefer name and Subjects for final exam)
    public void showProfile(String name, String subjectSelection1, String subjectSelection2, String subjectSelection3,
                            String character, String prefer) {
        System.out.println("Student Profile:" + "\n" + "-Student Name: " + name + "\n"
                + "-Subjects for final exam: " + "Mandarin, Math, English, "
                + subjectSelection1 + ", " + subjectSelection2 + ", " + subjectSelection3 + "\n"
                + "-Subject Preference: " + prefer + "\n"
                + "-Character: " + character);
    }

    //EFFECT: print out the name and time for one activity.
    public void showOneActivity(String actName, int actTime) {
        System.out.println("Activity Name: " + actName + "Activity Time: " + actTime);
    }

    //EFFECT: Print out the time bar, pressure bar and knowledge bar(include all three selections)
    public void printOutBars(int timeNow, int pressureNow, int s1Knowledge, int s2Knowledge, int s3Knowledge,
                             int mandarin, int math, int english, int totalTimeTograduate, int maxPressure,
                             String subjectSelectionOne, String subjectSelectionTwo, String subjectSelectionThree) {
        System.out.println("Time Bar: " + timeNow + "/" + totalTimeTograduate);
        System.out.println("Pressure Bar: " + pressureNow + "/" + maxPressure);
        System.out.println("Knowledge Bar: " + "- " + subjectSelectionOne + ":" +  s1Knowledge);
        System.out.println("               " + "- " + subjectSelectionTwo + ":" +  s2Knowledge);
        System.out.println("               " + "- " + subjectSelectionThree + ":" +  s3Knowledge);
        System.out.println("               " + "- " + "Mandarin" + ":" +  mandarin);
        System.out.println("               " + "- " + "Math" + ":" +  math);
        System.out.println("               " + "- " + "English" + ":" +  english);
    }


    //EFFECT: print out the mark of courses.
    public void printoutMark(String courseName, int mark) {
        System.out.println(courseName + ": " + mark);
    }
}
