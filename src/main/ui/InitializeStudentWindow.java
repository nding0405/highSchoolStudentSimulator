package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;

public class InitializeStudentWindow {
    public static final ImageIcon dialogueBoxLeft = new ImageIcon("./data/resource/dialogueBoxLeft.png");
    public static final ImageIcon dialogueBoxRight = new ImageIcon("./data/resource/dialogueBoxRight.png");
    public static final ImageIcon coveredFace = new ImageIcon("./data/resource/studentDialogue2.png");
    public static final ImageIcon uncoveredFace = new ImageIcon("./data/resource/studentDialogue1.png");
    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final int TEXT_FONT_SIZE = 20;//size of title
    public static final int BUTTON_FONT_SIZE = 16;//size of button text
    private static String STUDENT_NAME;
    private JFrame myFrame;
    private Student myStudent;
    private String gender;
    private JLayeredPane dialogueContainer;
    private JLabel dialogueImageLabel;
    private JLabel dialogueTextLabel;
    private JLabel studentImageLabel;

    InitializeStudentWindow() throws InterruptedException {
        setupFrame();
        studentNamePopUp();
        parentGenderPopUp();
        initializeStudent();
        setupDialogueBox();
        Thread.sleep(4000);
        parentTalk("Yes.");
    }

    private void parentTalk(String text) {
        dialogueImageLabel.setIcon(dialogueBoxRight);
        dialogueTextLabel.setText("You: " + text);
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

        dialogueTextLabel = new JLabel("<html>" + myStudent.getName() + ":" + gender
                + ", I'm about to enter the third year of high school. "
                + "Can I talk to you about the subject selection...?" + "</html>");
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

    private void initializeStudentBaseOnPreference(String prefer) {

    }

    public static void main(String[] args) throws InterruptedException {
        new InitializeStudentWindow();
    }
}
