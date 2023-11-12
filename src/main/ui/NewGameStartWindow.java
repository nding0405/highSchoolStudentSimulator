package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameStartWindow {
    private static final int SECONDS_TO_CHANGE = 5;
    private static final String BACKGROUND_INFO_ONE =
            "Background: Now, assuming you are a parent from "
                    + "\n" + "a middle-class Chinese family.";
    private static final String BACKGROUND_INFO_TWO =
            "Every students need to take a final exam at the end of their third year of high school."
                    + "\n" + "The university they can enter is purely depend on the score of that one-chance exam.";
    private static final String BACKGROUND_INFO_THREE = "The subjects of the final exam are: 'Mandarin' 'Math' "
            + "'English'"
            + "AND either 'Combined Science(includes Physics Biology and Chemistry)'"
            + "or 'Combined Arts(includes Geology History and Politics)'";
    private static final String BACKGROUND_INFO_FOUR = "The choice for art and science will influence the major they can "
            + "apply for their university." + "Art student cannot choose science major and vice versa.";
    private static final String BACKGROUND_INFO_FIVE = "However, in China, there are relatively less job opportunities for "
            + "students in art than student in science.";
    private static final String BACKGROUND_INFO_SIX = "Please answer the following questions as they will be crucial for "
            + "your child's subject selection.";
    private String[] backgroundTexts = {
            BACKGROUND_INFO_ONE,
            BACKGROUND_INFO_TWO,
            BACKGROUND_INFO_THREE,
            BACKGROUND_INFO_FOUR,
            BACKGROUND_INFO_FIVE,
            BACKGROUND_INFO_SIX
    };
    private static final String FONT_TYPE = "Courier New";
    private static final int INFO_FONT_SIZE = 20;
    private static String STUDENT_NAME;
    private JFrame myFrame;
    private Student student;
    private JLabel backgroundInfo;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;

    NewGameStartWindow() {
        setUpFrame();
        popUpStudentNameInput();
        initializeStudent();
        setupBackgroundInfo();
    }

    private void popUpStudentNameInput() {
        STUDENT_NAME = JOptionPane.showInputDialog("Please give your student a name");
    }

    private void setUpFrame() {
        myFrame = new JFrame();
        myFrame.getContentPane().setBackground(Color.BLACK);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(800,600);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
    }

    private void initializeStudent() {
        student = new Student(STUDENT_NAME);
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void setupBackgroundInfo() {
        String backgroundText = "<html>" + BACKGROUND_INFO_ONE.replace("\n", "<br>") + "</html>";
        backgroundInfo = new JLabel(backgroundText);
        backgroundInfo.setFont(new Font(FONT_TYPE, Font.PLAIN, INFO_FONT_SIZE));
        backgroundInfo.setBounds(400, 300, 800, 600);
        backgroundInfo.setForeground(Color.WHITE);
        backgroundInfo.setOpaque(false);
        backgroundInfo.setHorizontalAlignment(JLabel.CENTER);
        backgroundInfo.setVisible(true);
        myFrame.add(backgroundInfo);
        myFrame.setVisible(true);
        final int[] currentIndex = {1};
        Timer timer = new Timer(5000, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex[0] < backgroundTexts.length - 1) {
                    currentIndex[0]++;
                    String newText = "<html>" + backgroundTexts[currentIndex[0]].replace("\n", "<br>") + "</html>";
                    backgroundInfo.setText(newText);
                    backgroundInfo.setHorizontalAlignment(JLabel.CENTER);
                    backgroundInfo.setVisible(true);
                    myFrame.setVisible(true);
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        new NewGameStartWindow();
    }
}
