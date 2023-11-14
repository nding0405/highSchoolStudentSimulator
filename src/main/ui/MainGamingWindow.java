package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;

public class MainGamingWindow {
    public static final ImageIcon NIGHT1 = new ImageIcon("./data/resource/night1.PNG");
    public static final ImageIcon NIGHT2 = new ImageIcon("./data/resource/night2.PNG");
    public static final ImageIcon NIGHT3 = new ImageIcon("./data/resource/night3.PNG");
    public static final ImageIcon BAR_IMAGE = new ImageIcon("./data/resource/BarImage.png");
    private JFrame myFrame;

    private JLabel pressureBar;
    private JLabel timeBar;
    private JLabel mandarinKnowledgeBar;
    private JLabel mathKnowledgeBar;
    private JLabel englishKnowledgeBar;
    private JLabel s1KnowledgeBar;
    private JLabel s2KnowledgeBar;
    private JLabel s3KnowledgeBar;

    private JLabel backgroundImage;
    private JLabel studentImage;

    private JButton saveButton;
    private JButton addButton;
    private JButton viewScheduleButton;
    private JButton viewProfileButton;

    private Student myStudent;
    private String gender;

    //REQUIRES: this constructor will only be called once the student is initialized.
    //MODIFIED: this, Student, Knowledge
    //EFFECTS: construct a gaming window:
    //------------------------------------
    //|BARS  |                           |
    //|------                            |
    //|                                  |
    //|                                  |
    //|           studentImage           |
    //|                                  |
    //|                                  |
    //| save add viewSchedule viewProfile|
    //------------------------------------
    MainGamingWindow(Student myStudent, String gender) {
        this.myStudent = myStudent;
        this.gender = gender;
        setupAll();
    }

    //REQUIRES:
    //MODIFIED:
    //EFFECTS:
    private void setupAll() {
        setupFrame();
        setupBars();
        setupButtons();
        setupImages();
    }

    //REQUIRES:
    //MODIFIED:
    //EFFECTS:
    private void setupImages() {

    }

    //REQUIRES:
    //MODIFIED:
    //EFFECTS:
    private void setupButtons() {

    }

    //REQUIRES:
    //MODIFIED:
    //EFFECTS:
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void setupBars() {
        timeBar = new JLabel();
        pressureBar = new JLabel();
        mandarinKnowledgeBar = new JLabel();
        mathKnowledgeBar = new JLabel();
        englishKnowledgeBar = new JLabel();
        s1KnowledgeBar = new JLabel();
        s2KnowledgeBar = new JLabel();
        s3KnowledgeBar = new JLabel();

        timeBar.setText("Time: " + myStudent.getTime() + "/1000");
        pressureBar.setText("Pressure: " + myStudent.getPressure());
        mandarinKnowledgeBar.setText("Mandarin Knowledge: " + myStudent.getKnowledge().getMandarinKnowledge());
        mathKnowledgeBar.setText("Math Knowledge: " + myStudent.getKnowledge().getMathKnowledge());
        englishKnowledgeBar.setText("English Knowledge: " + myStudent.getKnowledge().getEnglishKnowledge());
        s1KnowledgeBar.setText(myStudent.getSubjectSelectionOne() + " Knowledge: "
                + myStudent.getKnowledge().getS1Knowledge());
        s2KnowledgeBar.setText(myStudent.getSubjectSelectionTwo() + " Knowledge: "
                + myStudent.getKnowledge().getS2Knowledge());
        s3KnowledgeBar.setText(myStudent.getSubjectSelectionThree() + " Knowledge: "
                + myStudent.getKnowledge().getS3Knowledge());

        JPanel barPanel = new JPanel();
        barPanel.setBounds(0,0,200,200);
        timeBar.setBounds(0, 0, 200, 25);
        pressureBar.setBounds(0, 25, 200, 25);
        mandarinKnowledgeBar.setBounds(0, 50, 200, 25);
        mathKnowledgeBar.setBounds(0, 75, 200, 25);
        englishKnowledgeBar.setBounds(0, 100, 200, 25);
        s1KnowledgeBar.setBounds(0, 125, 200, 25);
        s2KnowledgeBar.setBounds(0, 150, 200, 25);
        s3KnowledgeBar.setBounds(0, 175, 200, 25);

        barPanel.add(timeBar);
        barPanel.add(pressureBar);
        barPanel.add(mandarinKnowledgeBar);
        barPanel.add(mathKnowledgeBar);
        barPanel.add(englishKnowledgeBar);
        barPanel.add(s1KnowledgeBar);
        barPanel.add(s2KnowledgeBar);
        barPanel.add(s3KnowledgeBar);

        JLabel barImage = new JLabel();
        barImage.setIcon(BAR_IMAGE);
        JLayeredPane lp = new JLayeredPane();
        lp.setBounds(0,0,200,200);
        lp.add(barPanel, JLayeredPane.DEFAULT_LAYER);
        lp.add(barImage, JLayeredPane.MODAL_LAYER);
        myFrame.add(lp);
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

    //REQUIRES:
    //MODIFIED:
    //EFFECTS:
    private void updateBars() {

    }

    //REQUIRES:
    //MODIFIED:
    //EFFECTS: linked to the action listener: viewProfileButton
    private void showProfile() {

    }

    //REQUIRES:
    //MODIFIED:
    //EFFECTS: linked to the action listener: addButton
    private void addActivities() {

    }

    //REQUIRES:
    //MODIFIED:
    //EFFECTS: linked to the action listener: viewScheduleButton
    private void showActivities() {

    }

    //REQUIRES:
    //MODIFIED:
    //EFFECTS: linked to the action listener: saveButton
    private void saveGame() {

    }

    private void startBackgroundTask() {
        SwingWorker<Void, Void> backgroundWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (true) {
                    // Perform background task, e.g., change background image
                    // Simulate sunrise/sunset every 3 seconds
                    Thread.sleep(3000);
                }
            }

            @Override
            protected void done() {
                // Handle completion if needed
            }
        };

        backgroundWorker.execute();
    }

    public static void main(String[] args) {
        Student s = new Student("testStudent");
        new MainGamingWindow(s, "dad");
    }
}
