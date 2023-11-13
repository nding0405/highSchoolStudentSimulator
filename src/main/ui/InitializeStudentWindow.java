package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;

public class InitializeStudentWindow {
    public static final ImageIcon dialogueBoxLeft = new ImageIcon("./data/resource/dialogueBoxLeft.png");
    public static final ImageIcon dialogueBoxRight = new ImageIcon("./data/resource/dialogueBoxRight.png");
    public static final ImageIcon coveredFace = new ImageIcon("./data/resource/studentDialogue2.png");
    public static final ImageIcon uncoveredFace = new ImageIcon("./data/resource/studentDialogue1.png");
    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final int TEXT_FONT_SIZE = 20;//size of title
    public static final int BUTTON_FONT_SIZE = 16;//size of button text
    private static final int SECONDS_TO_READ = 5000;
    private static String STUDENT_NAME;
    private JFrame myFrame;
    private Student myStudent;
    private String gender;
    private JLayeredPane dialogueContainer;
    private JLabel dialogueImageLabel;
    private JLabel dialogueTextLabel;
    private JLabel studentImageLabel;

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
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

            }
        };
        worker.execute();
    }

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

    private void simulateDelay(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void setupDialogueBox() {
        dialogueContainer = new JLayeredPane();
        dialogueContainer.setBounds(0, 350, 800, 600);
        dialogueContainer.setBackground(Color.BLACK);

        studentImageLabel = new JLabel();
        studentImageLabel.setIcon(coveredFace);
        studentImageLabel.setOpaque(false);
        studentImageLabel.setBackground(Color.BLACK);
        studentImageLabel.setBounds(0, 55, 300, 330);

        dialogueImageLabel = new JLabel();
        dialogueImageLabel.setIcon(dialogueBoxLeft);
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

    private void parentGenderPopUp() {
        String[] selections = {"Mom", "Dad", "walmart shopping bag"};
        while (gender == null) {
            int option = JOptionPane.showOptionDialog(
                    null,
                    "You want to be the ... of the student",
                    "Select your preference",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    selections,
                    0);
            if (option == 0) {
                gender = "mom";
            } else if (option == 1) {
                gender = "dad";
            }
        }
    }

    private void studentNamePopUp() {
        while (STUDENT_NAME == null || STUDENT_NAME.isEmpty()) {
            STUDENT_NAME = JOptionPane.showInputDialog("Please give your child a name");
        }
    }

    private void setupFrame() {
        myFrame = new JFrame();
        myFrame.getContentPane().setBackground(Color.BLACK);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(800,600);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
    }

    private void initializeStudent() {
        myStudent = new Student(STUDENT_NAME);
    }

    private void initializeStudentBaseOnPreference(String prefer) throws InterruptedException {
        if (prefer.equals("fine art")) {
            fineArtStudentInitialize();
        } else if (prefer.equals("science")) {
            scienceStudentInitialize();
        } else {
            artStudentInitialize();
        }
    }

    private void parentTalk(String text) throws InterruptedException {
        dialogueImageLabel.setIcon(dialogueBoxRight);
        dialogueTextLabel.setText("<html>" + ("You: " + text).replace("\n", "<br>") + "</html>");
        simulateDelay(1);
    }

    private void studentTalk(String text) throws InterruptedException {
        dialogueImageLabel.setIcon(dialogueBoxLeft);
        dialogueTextLabel.setText("<html>" + (STUDENT_NAME + ": " + text).replace("\n",
                        "<br>") + "</html>");
        simulateDelay(1);
    }

    private void scienceStudentInitialize() {
        System.out.println("science");
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void artStudentInitialize() throws InterruptedException {
        studentTalk(gender + " I prefer arts and I'm more inclined to choose subjects like politics and history.");
        int i = parentQuestionPopUp("Arts?","How would you answer?",
                "Of course! I supports any choice you make!","Well, we need to talk about it.");
        if (i == 1) {
            parentTalk("But dear, it's challenging to find a job for art students. "
                    + "What 's gonna happen if you have trouble finding jobs in the future?");
            studentTalk("But I love it " + gender
                    + "! I promise I will do it well in the college entrance exam!");
            int answer = parentQuestionPopUp("Art Student","How would you answer?",
                    "(sigh, reluctantly agree.)", "Strongly disagree.");
            if (answer == 0) {
                parentTalk("Well ok (sigh)... But you have to promise you will work hard and get a high mark.");
                studentTalk("I promise " + gender + "!");
                myStudent.setSelectionAgree("a", 2);
            } else {
                parentTalk("You: Have you ever thought about me if you choose arts?" + "\n"
                        + "Do you know how difficult it is to find a job in arts?" + "No one will feed you if you lose your"
                        + "job!" + "\n" + "You must choose science!");
                studentTalk("(lowering head in silence)");
                myStudent.setSelectionDisAgree("a");
            }
        } else {
            parentTalk("As parents, we want you to be happy, so we're giving you freedom to choose. "
                    + "\n"
                    + "However, you need to consider your choices carefully and take responsibility for them.");

        }
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void fineArtStudentInitialize() throws InterruptedException {
        studentTalk(gender + " I really really love drawing and I want to go for an art colleges...");
        int i = parentQuestionPopUp("choose your answer","How would you answer?","Of course!",
                "No way!");
        if (i == 0) {
            parentTalk("Of course!");
            studentTalk("Thank you so much and I love you " + gender + "!");
            myStudent.setSelectionAgree("f", 1);
        } else {
            parentTalk("No way!");
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

    //a1 0, a2 1
    private int parentQuestionPopUp(String title,String question, String a1, String a2) {
        String[] selections = {a1, a2};
        Integer answerIndex = 10;
        while (answerIndex == 10) {
            answerIndex = JOptionPane.showOptionDialog(
                    dialogueTextLabel,
                    question,
                    title,
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    selections,
                    0);
        }
        return answerIndex;
    }

    public static void main(String[] args) throws InterruptedException {
        new InitializeStudentWindow();
    }
}
