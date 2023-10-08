package ui;

import model.Activities;
import model.Parent;
import model.Student;

import java.awt.event.KeyEvent;
import java.util.Scanner;

import static java.awt.event.KeyEvent.*;

public class GameStarter extends Thread {
    private Scanner input;
    private Student student;
    private Parent parent;
    private String gender;
    private String studentName;

    private static final String openmindedOne =
            "Background: Now, assuming you are a parent from a middle-class Chinese family.";
    private static final String openmindedTwo =
            "Every students need to take a final exam at the end of their third year of high school."
                    + "\n" + "The university they can enter is purely depend on the score of that one-chance exam.";
    private static final String openmindedThree = "The subjects of the final exam are: 'Mandarin' 'Math' 'English' "
            + "\n" + "AND either 'Combined Science(includes Physics Biology and Chemistry)'"
            + "\n" + "or 'Combined Arts(includes Geology History and Politics)'";
    private static final String openmindedFour = "The choice for art and science will influence the major they can "
            + "apply for" + "\n" + "their university————Art student cannot choose science major and vice versa.";
    private static final String openmindedFive = "However, in China, there are relatively less job opportunities for "
            + "\n" + "students in art than student in science.";
    private static final String openmindedSix = "Please answer the following questions as they will be crucial for "
            + "your child's subject selection.";

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
        askOpenminded();
        Thread.sleep(1000);
        String operation;
        Boolean goOn = true;
        while (goOn) {
            showInstructions();
            operation = input.next();
            detectValidOperation(operation);
            processOperation(operation);
            goOn = !(student.detectEnding());
        }
        student.endChoice();
    }


    public void askOpenminded() {
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

    //EFFECT: 问用户几个问题根据问题答案产生开明度。
    public void openMindQuestions(String prefer) {
        System.out.println(studentName + ":" + gender
                + ", I'm about to enter the third year of high school. "
                + "Can I talk to you about the subject selection...?");
        input.nextLine();
        System.out.println("Enter: yes");
        while (!input.next().equals("yes")) {
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

    //EFFECT: 孩子喜欢文科父母问题
    private void artQuestions() {
        String choice;
        System.out.println(studentName + ":" + gender
                + " I prefer arts and I'm more inclined to choose subjects like politics and history.");
        System.out.println("A-'Of course!" + " I supports any choice you make!'");
        System.out.println("B- Well, we need to talk about it.");
        choice = input.next();
        while (!choice.toLowerCase().equals("a") && !choice.toLowerCase().equals("b")) {
            System.out.println("Invalid input! Please enter 'A' or 'B'!(not case sensitive >v<!)");
            choice = input.next();
        }
        if (choice.equals("b")) {
            System.out.println("You: But darling, it's challenging to find good job opportunities in arts. " + "\n"
                    + "What 's gonna happen if you have trouble finding jobs in the future?");
            artDisagree();
        } else {
            System.out.println("You: As parents, we want you to be happy, so we're giving you freedom to choose. "
                    + "\n"
                    + "However, you need to consider your choices carefully and take responsibility for them.");
            artAgree();
        }
    }

    //EFFECT: 父母同意
    private void artAgree() {
        System.out.println(studentName + ": " + "I love you " + gender + "! (deeply hugged you)");
        student.setSelectionAgree("a");
    }

    //EFFECT: 父母不同意
    private void artDisagree() {
        System.out.println(studentName + ": " + "But I love it" + gender
                + "! I promise I will do it well in the university entrance exam!");
        System.out.println("A- (sigh, reluctantly agree.)");
        System.out.println("B- 'Strongly disagree.'");
        String choice = input.next();
        while (!choice.toLowerCase().equals("a") && !choice.toLowerCase().equals("b")) {
            System.out.println("Invalid input! Please enter 'A' or 'B'!(not case sensitive >v<!)");
            choice = input.next();
        }
        if (choice.equals("a")) {
            System.out.println("You: But darling, it's challenging to find good job opportunities in arts. " + "\n"
                    + "What 's gonna happen if you have trouble finding jobs in the future?");
            student.setSelectionAgree("a");
        } else {
            System.out.println("You: Have you ever thought about me if you choose arts?" + "\n"
                    + "Do you know how difficult it is to find a job in arts?" + "\n"
                    + "No one will feed you if you lose your job!" + "\n"
                    + "No one will feed US in our old age if you can't earn enough money! You must choose science!"
                    + "You must choose physics at least!");
            System.out.println(studentName + ": (lowering head in silence)");
            student.setSelectionDisAgree("a");
        }
    }

    //EFFECT: 孩子喜欢理科父母问题
    private void scienceQuestions() {
        System.out.println(studentName + ":" + gender);
    }

    //EFFECT: 孩子喜欢美术父母问题
    private void fineArtQuestions() {
        System.out.println(studentName + ":" + gender);
    }


    // EFFECT: detect whether the input of the user is "add" or "view".
    // If it is none of them, ask the user to try again until the input is one of "add" and "view".
    public void detectValidOperation(String operation) throws InterruptedException {
        while (!(operation.equals("add") || operation.equals("view"))) {
            System.out.println("invalid input, please try again!");
            Thread.sleep(1000);
            showInstructions();
            operation = input.next();
        }
    }

    // MODIFIES: student/null
    // EFFECTS: let the user add course or view schedule depending on the input.
    public void processOperation(String operation) throws InterruptedException {
        Activities a = new Activities("a",0,true,true);
        if (operation.equals("add")) {
            showSelection();
            addingInstruction();
            String activitySelection = input.next();
            while (!(a.detectValidActivity(activitySelection))) { //检测输入是否合理
                System.out.println("***Invalid activity! Please try again!***");
                activitySelection = input.next();
            }
            findActivity(activitySelection, a);
            student.addActivity(a);
            System.out.println(activitySelection + " is successfully added"); //示意加课成功
            student.studentAnime(a);
        } else {
            student.showSchedule();
        }
    }

    //MODIFIES: this, Activities, Student
    //EFFECT: get time for the selected activity from the user, find the corresponding activities object
    // and assign the input time to the corresponding activities. Assign the prepared activities object
    // to the parameter "a".
    public void findActivity(String activitySelection, Activities a) {
        boolean validTime = false;
        addingTimeInstruction(activitySelection);
        while (!validTime) {
            String next = input.next();
            try {
                int time = Integer.parseInt(next);
                a.findActivity(activitySelection,time);
                validTime = true;
            } catch (NumberFormatException e) {
                System.out.println("This input is not an integer - Please try again!");
            }
        }
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

    //EFFECTS: Show the user all activities that already added to the schedule.
    public void showSchedule() {
        student.showSchedule();
    }

    //MODIFIES: this
    //EFFECTS: create scanner object for "input", and create a new student object.
    // Tell the user the student is created when the object is created.
    public void initializeStudent() {
        input = new Scanner(System.in);
        studentName = input.next();//让用户输入学生姓名，并根据姓名创建新的student object
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

    //EFFECT: 根据用户性别创建一个parent object
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
        parent = new Parent(gender);
    }


    //EFFECTS: Show the user instruction for adding an activity.
    public void addingInstruction() {
        System.out.println("please enter the activity name which in the list above");
    }

    public void addingTimeInstruction(String activityName) {
        System.out.println("please enter the time you want for this activity" + activityName);
    }
}
