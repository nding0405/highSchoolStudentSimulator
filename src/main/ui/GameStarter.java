package ui;

import model.Activities;
import model.Student;

import java.util.List;
import java.util.Scanner;

public class GameStarter extends Thread {
    private Scanner input;//用户输入
    private Student student;
    private String studentName;

    // EFFECTS: create a new GameStarter object.
    @Override
    public void run() {
        runTheGame();
    }

    public GameStarter() throws InterruptedException {

    }

    // MODIFIES: this
    // EFFECTS: enable the user interaction
    public void runTheGame() {
        introduction();//请输入姓名
        initialize();
        String operation;
        Boolean goOn = !(student.detectEnding());

        while (goOn) {
            showInstructions();//选课请输入add，看已有课程请输入view
            operation = input.next();
            while (!(operation.equals("add") || operation.equals("view"))) {
                System.out.println("invalid operation, please try again!");
                showInstructions();
                operation = input.next();
            }
            processOperation(operation);
            goOn = !(student.detectEnding());
        }

        student.endChoice();
    }

    // MODIFIES: student/null
    // EFFECTS: let the user add course or view schedule depending on the input.
    public void processOperation(String operation) {
        Activities a = new Activities("a",0,true,true);
        if (operation.equals("add")) {
            showSelection();
            addingInstruction();
            String activitySelection = input.next();
            while (!a.detectValidActivity(activitySelection)) { //检测输入是否合理
                System.out.println("***Invalid activity! Please try again!***");
                activitySelection = input.next();
            }
            addingTimeInstruction(activitySelection);
            int timeSelection = Integer.parseInt(input.next());
            a.findActivity(activitySelection,timeSelection); //根据string找到对应的activity,并将其assign到a上面
            student.addActivity(a); //加课->更新bars
            System.out.println(activitySelection + " is successfully added"); //示意加课成功
            student.studentAnime(a); //播放学习或者游戏动画
        } else {
            student.showSchedule();
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
    public void initialize() {
        input = new Scanner(System.in);
        studentName = input.next();//让用户输入学生姓名，并根据姓名创建新的student object
        student = new Student(studentName);
        Boolean studentCharacter = student.getChr();
        Boolean studentSubjectPreference = student.getPreference();
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


    //EFFECTS: Show the user instruction for adding an activity.
    public void addingInstruction() {
        System.out.println("please enter the activity name which in the list above");
    }

    public void addingTimeInstruction(String activityName) {
        System.out.println("please enter the time you want for this activity" + activityName);
    }
}
