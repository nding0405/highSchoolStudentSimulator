package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;

//represent a window that allow user to create their student
public class InitializeStudentWindow {
    public static final ImageIcon DIALOGUE_BOX_LEFT = new ImageIcon("./data/resource/dialogueBoxLeft.png");
    public static final ImageIcon DIALOGUE_BOX_RIGHT = new ImageIcon("./data/resource/dialogueBoxRight.png");
    public static final ImageIcon COVERED_FACE = new ImageIcon("./data/resource/studentFlowerFace.png");
    public static final ImageIcon PLAIN_FACE = new ImageIcon("./data/resource/studentPlainFace.png");
    public static final ImageIcon CRY_FACE = new ImageIcon("./data/resource/studentCryFace.png");
    public static final ImageIcon SMILE_FACE = new ImageIcon("./data/resource/studentSmileFace.png");
    public static final ImageIcon POPUP_ICON = new ImageIcon("./data/resource/popupIcon.png");
    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final int TEXT_FONT_SIZE = 20;//size of title
    protected static String STUDENT_NAME;
    protected JFrame myFrame;
    protected Student myStudent;
    protected String gender;
    protected JLayeredPane dialogueContainer;
    protected JLabel dialogueImageLabel;
    protected JLabel dialogueTextLabel;
    protected JLabel studentImageLabel;

    //MODIFIED: this
    //EFFECTS: construct a new InitializeStudentWindow();
    InitializeStudentWindow() throws InterruptedException {
        setupFrame();
        studentNamePopUp();
        parentGenderPopUp();
        initializeStudent();
        setupDialogueBox();
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                studentTalk("<html>" + gender
                        + ", I'm about to enter the third year of high school. "
                        + "Can I talk to you about the subject selection...?" + "</html>");
                parentTalk("Yes, of course.");
                initializeStudentBaseOnPreference(getPreference());
                return null;
            }

            @Override
            protected void done() {
                myFrame.dispose();
                new MainGamingWindow(myStudent, gender);
            }
        };
        worker.execute();
    }

    //EFFECTS: if student likes fine art
    //            return "fine art"
    //        else if student likes science
    //            return "science"
    //        else if student likes arts
    //            return "arts"
    private String getPreference() {
        String prefer;
        if (myStudent.getLoveFineArt()) {
            prefer = "fine art";
        } else if (myStudent.getPreference()) {
            prefer = "science";
        } else {
            prefer = "arts";
        }
        return prefer;
    }

    //EFFECTS: pause the thread for seconds.
    private void simulateDelay(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }

    // MODIFIED: this
    // EFFECTS: setup dialogue box
    //          1. dialogueContainer
    //             bounds:(0, 350, 800, 600)
    //          2. studentImageLabel
    //             bounds:(0, 55, 300, 330)
    //             icon:COVERED_FACE
    //          3. dialogueImageLabel
    //             bounds:(0, 350, 800, 250)
    //             icon:DIALOGUE_BOX_LEFT
    //          4. dialogueTextLabel
    //             bounds:(0, 350, 700, 250)
    private void setupDialogueBox() {
        dialogueContainer = new JLayeredPane();
        dialogueContainer.setBounds(0, 350, 800, 600);
        dialogueContainer.setBackground(Color.BLACK);
        studentImageLabel = new JLabel();
        studentImageLabel.setIcon(COVERED_FACE);
        studentImageLabel.setOpaque(false);
        studentImageLabel.setBackground(Color.BLACK);
        studentImageLabel.setBounds(0, 55, 300, 330);
        dialogueImageLabel = new JLabel();
        dialogueImageLabel.setIcon(DIALOGUE_BOX_LEFT);
        dialogueImageLabel.setOpaque(false);
        dialogueImageLabel.setBackground(Color.BLACK);
        dialogueImageLabel.setBounds(0, 350, 800, 250);
        dialogueTextLabel = new JLabel();
        dialogueTextLabel.setFont(new Font(FONT_TYPE, Font.PLAIN, TEXT_FONT_SIZE));
        dialogueTextLabel.setOpaque(false);
        dialogueTextLabel.setBounds(0, 350, 700, 250);
        dialogueTextLabel.setHorizontalAlignment(JLabel.CENTER);
        dialogueTextLabel.setForeground(Color.WHITE);
        dialogueContainer.add(studentImageLabel, JLayeredPane.DEFAULT_LAYER);
        dialogueContainer.add(dialogueImageLabel, JLayeredPane.PALETTE_LAYER);
        dialogueContainer.add(dialogueTextLabel, JLayeredPane.MODAL_LAYER);
        myFrame.add(dialogueContainer);
    }

    //MODIFIED: this
    //EFFECTS: ask the gender of the user and assign the value to gender.
    private void parentGenderPopUp() {
        String[] selections = {"Mom", "Dad", "walmart shopping bag"};
        while (gender == null) {
            int option = JOptionPane.showOptionDialog(
                    null,
                    "You want to be the ... of the student",
                    "Select your preference",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    POPUP_ICON,
                    selections,
                    0);
            if (option == 0) {
                gender = "mom";
            } else if (option == 1) {
                gender = "dad";
            }
        }
    }

    //MODIFIED: this
    //EFFECTS: ask the name of the student and assign the value to STUDENT_NAME.
    private void studentNamePopUp() {
        while (STUDENT_NAME == null || STUDENT_NAME.isEmpty()) {
            STUDENT_NAME = (String) JOptionPane.showInputDialog(
                    null,
                    "Please give your child a name",
                    "Input",
                    JOptionPane.PLAIN_MESSAGE,
                    POPUP_ICON,
                    null,
                    "");
        }
    }

    //MODIFIED:this
    //EFFECTS: build a 800x600 frame with black background.
    private void setupFrame() {
        myFrame = new JFrame();
        myFrame.getContentPane().setBackground(Color.BLACK);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(800,600);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
    }

    //REQUIRES: STUDENT_NAME must be initialized
    //MODIFIED:this
    //EFFECTS: create a new student object and assign it to myStudent
    private void initializeStudent() {
        myStudent = new Student(STUDENT_NAME);
    }

    //REQUIRES: myStudent is not null
    //MODIFIED: Student
    //EFFECTS: ask the user several questions and initialize myStudent based on their answer.
    private void initializeStudentBaseOnPreference(String prefer) throws InterruptedException {
        if (prefer.equals("fine art")) {
            fineArtStudentInitialize();
        } else if (prefer.equals("science")) {
            scienceStudentInitialize();
        } else {
            artStudentInitialize();
        }
    }

    //REQUIRE: the myStudent field must prefer to learn science
    //MODIFIED: this, Student
    //EFFECT: ask why does the user would like their student to learn science. And initialize the student pressure
    // in regard to the users' choice.
    private void scienceStudentInitialize() throws InterruptedException {
        studentTalk(gender + " I prefer science and I'm more inclined to choose subjects "
                + "like physics chemistry and biology.");
        int i = parentQuestionPopUp("Science?","How would you answer?",
                    "Of course! I agree because I love you.",
                    "Yes, I agree because it's gonna be easier to find a job and we need money.");
        if (i == 1) {
            switchFace("cry");
            studentTalk("Why do you always give me so much pressure!"
                    + "(sad and angry, run back to his/her room)");
            myStudent.setSelectionAgree("s", 2);
        } else {
            switchFace("smile");
            studentTalk("I'm so happy and I love you...(smile and hugged you)");
            myStudent.setSelectionAgree("s", 1);
        }
    }


    //REQUIRE: the student field must prefer to learn art
    //MODIFIED: this, Student
    //EFFECT: ask whether the user would like their student to learn art. If yes then will call artAgree().
    // If no then will call artDisagree() to continue asking the user some questions.
    private void artStudentInitialize() throws InterruptedException {
        studentTalk(gender + " I prefer arts and I'm more inclined to choose subjects like politics and history.");
        int i = parentQuestionPopUp("Arts?","How would you answer?",
                    "Of course! I supports any choice you make!","Well, we need to talk about it.");
        if (i == 1) {
            artsDisagree();
        } else {
            parentTalk("As parents, we want you to be happy, so we're giving you freedom to choose. "
                    + "However, you need to consider your choices carefully and take responsibility for them.");
            switchFace("smile");
            studentTalk("I love you " + gender + "! (deeply hugged you)");
            myStudent.setSelectionAgree("a", 1);
        }
    }

    //REQUIRE: the myStudent field must prefer to learn art and the user disagree with their
    // student to learn art in artQuestions().
    //MODIFIED: this, Student
    //EFFECT: continue asking the user about their student subject selection questions and initialize the student
    // pressure and subject selection in regard to the user choice.
    public void artsDisagree() throws InterruptedException {
        parentTalk("But dear, it's challenging to find a job for art students. "
                + "What 's gonna happen if you have trouble finding jobs in the future?");
        studentTalk("But I love it " + gender
                + "! I promise I will do it well in the college entrance exam!");
        int answer = parentQuestionPopUp("Art Student","How would you answer?",
                "(sigh, reluctantly agree.)", "Strongly disagree.");
        if (answer == 0) {
            parentTalk("Well ok (sigh)... But you have to promise you will work hard and get a high mark.");
            switchFace("plain");
            studentTalk("I promise " + gender + "!");
            myStudent.setSelectionAgree("a", 2);
        } else {
            parentTalk("You: Have you ever thought about me if you choose arts?"
                    + "Do you know how difficult it is to find a job in arts?"
                    + "No one will feed you if you lose your job! You must choose science!");
            switchFace("plain");
            studentTalk("(lowering head in silence)");
            myStudent.setSelectionDisAgree("a");
        }
    }

    //REQUIRE: the student field in this object must prefer to learn fine art
    //MODIFIED: this, student
    //EFFECT: ask whether the user would like their student to learn fine art. If yes then will call fine art student
    // end and end the game. If no then will call other methods to continue asking the user some questions.
    private void fineArtStudentInitialize() throws InterruptedException {
        studentTalk(gender + " I really really love drawing and I want to go for an art colleges...");
        int i = parentQuestionPopUp("choose your answer","How would you answer?","Of course!",
                "No way!");
        if (i == 0) {
            parentTalk("Of course!");
            switchFace("smile");
            studentTalk("Thank you so much and I love you " + gender + "!");
            myStudent.setSelectionAgree("f", 1);
            new FineArtEnd(myStudent,gender);
        } else {
            parentTalk("No way!");
            switchFace("plain");
            studentTalk("Sorry " + gender + "I won't mention it again...");
            parentTalk("You forced your child to quit fine art. Now please choose either art or science for "
                    + STUDENT_NAME);
            Integer sciOrArt = parentQuestionPopUp("Fine Art Student","Please choose either art or science",
                    "Science","Art");
            if (sciOrArt == 0) {
                myStudent.setSelectionDisAgree("fs");
            } else {
                myStudent.setSelectionDisAgree("fa");
            }
        }
    }

    //EFFECTS: create a yes no option popup with title question answer1 and answer2.
    private int parentQuestionPopUp(String title, String question, String a1, String a2) {
        String[] selections = {a1, a2};
        int answerIndex = JOptionPane.CLOSED_OPTION;
        while (answerIndex == JOptionPane.CLOSED_OPTION) {
            answerIndex = JOptionPane.showOptionDialog(
                    dialogueTextLabel,
                    question,
                    title,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    POPUP_ICON,
                    selections,
                    0);
        }
        return answerIndex;
    }

    //MODIFIED: this
    //EFFECTS: 1. change the dialogueImageLabel to left
    //         2. set text to talking content (parameter)
    //         3. wait for 3 seconds
    private void parentTalk(String text) throws InterruptedException {
        dialogueImageLabel.setIcon(DIALOGUE_BOX_RIGHT);
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + ("You: " + text).replace("\n", "<br>") + "</div></html>");
        simulateDelay(3);
    }

    //MODIFIED: this
    //EFFECTS: 1. change the dialogueImageLabel to right
    //         2. set text to talking content (parameter)
    //         3. wait for 3 seconds
    private void studentTalk(String text) throws InterruptedException {
        dialogueImageLabel.setIcon(DIALOGUE_BOX_LEFT);
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + (STUDENT_NAME + ": " + text).replace("\n", "<br>") + "</div></html>");
        simulateDelay(3);
    }

    //MODIFIED: this
    //EFFECTS: change the studentImageLabel to SMILE_FACE or CRY_FACE or COVERED_FACE or PLAIN_FACE based on
    //         parameter.
    private void switchFace(String s) {
        if (s.equals("smile")) {
            studentImageLabel.setIcon(SMILE_FACE);
        } else if (s.equals("cry")) {
            studentImageLabel.setIcon(CRY_FACE);
        } else if (s.equals("cover")) {
            studentImageLabel.setIcon(COVERED_FACE);
        } else if (s.equals("plain")) {
            studentImageLabel.setIcon(PLAIN_FACE);
        }
    }

//    public static void main(String[] args) throws InterruptedException {
//        new InitializeStudentWindow();
//    }
}
