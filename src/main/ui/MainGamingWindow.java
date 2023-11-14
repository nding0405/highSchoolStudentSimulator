package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainGamingWindow {
    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final Integer TEXT_FONT_SIZE = 15;
    public static final String NIGHT1 = "./data/resource/night1.PNG";
    public static final String NIGHT2 = "./data/resource/night2.PNG";
    public static final String NIGHT3 = "./data/resource/night3.PNG";
    public static final String REG_STU1 = "./data/resource/regularStudent1.PNG";
    public static final String REG_STU2 = "./data/resource/regularStudent2.PNG";
    public static final String DESK1 = "./data/resource/desk1.png";
    public static final String DESK2 = "./data/resource/desk2.png";
    public static final ImageIcon BAR_IMAGE = new ImageIcon("./data/resource/BarImage.png");
    public static final ImageIcon ADD_BUTTON_IMAGE = new ImageIcon("./data/resource/AddButton.png");
    public static final ImageIcon SAVE_BUTTON_IMAGE = new ImageIcon("./data/resource/SaveButton.png");
    public static final ImageIcon PROFILE_BUTTON_IMAGE = new ImageIcon("./data/resource/ProfileButton.png");
    public static final ImageIcon SCHEDULE_BUTTON_IMAGE = new ImageIcon("./data/resource/ScheduleButton.png");
    private static final java.util.List<String> encapsulateOneDay = encapAlltime(new ArrayList<>());
    private static final java.util.List<String> encapsulateRegStu = encapRegStu(new ArrayList<>());

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
    private JLabel deskImage;

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
        startBackgroundTask();
        startRegularStudentTask();
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
                    oneDayCycle();
                }
            }

            @Override
            protected void done() {
                // Handle completion if needed
            }
        };

        backgroundWorker.execute();
    }

    private void startRegularStudentTask() {
        SwingWorker<Void, Void> backgroundWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                while (true) {
                    oneStudentCycle();
                }
            }

            @Override
            protected void done() {
                // Handle completion if needed
            }
        };

        backgroundWorker.execute();
    }

    private void oneStudentCycle() throws InterruptedException {
        ImageIcon image;
        for (String s : encapsulateRegStu) {
            image = new ImageIcon(s);
            studentImage.setIcon(image);
            Thread.sleep(500);
        }
    }

    private void oneDayCycle() throws InterruptedException {
        ImageIcon image;
        for (String s : encapsulateOneDay) {
            image = new ImageIcon(s);
            backgroundImage.setIcon(image);
            Thread.sleep(3000);
        }
    }

    //REQUIRES:
    //MODIFIED:
    //EFFECTS:
    private void setupAll() {
        setupFrame();
        JLayeredPane bars = setupBars();
        JPanel buttons = setupButtons();
        JLabel background = setupBackGround();
        JLabel studentImg = setUpStudentImg();
        JLabel deskImage = setUpDeskImg();

        JLayeredPane generalPane = new JLayeredPane();
        generalPane.setBounds(0,0,800,600);

        generalPane.add(background,JLayeredPane.DEFAULT_LAYER);
        generalPane.add(bars,JLayeredPane.PALETTE_LAYER);
        generalPane.add(buttons,JLayeredPane.MODAL_LAYER);
        generalPane.add(studentImg,JLayeredPane.POPUP_LAYER);
        generalPane.add(deskImage,JLayeredPane.DRAG_LAYER);

        myFrame.add(generalPane);
        myFrame.repaint();
    }

    private JLabel setUpDeskImg() {
        deskImage = new JLabel();
        deskImage.setBounds(200,200,400,300);
        deskImage.setIcon(new ImageIcon(DESK1));
        return deskImage;
    }

    private JLabel setUpStudentImg() {
        studentImage = new JLabel();
        studentImage.setBounds(200,200,400,300);
        studentImage.setIcon(new ImageIcon(REG_STU1));
        return studentImage;
    }

    //REQUIRES:
    //MODIFIED:
    //EFFECTS:
    private JLabel setupBackGround() {
        backgroundImage = new JLabel();
        backgroundImage.setBounds(0,0,800,600);
        backgroundImage.setIcon(new ImageIcon(NIGHT1));
        return backgroundImage;
    }

    //REQUIRES:
    //MODIFIED:
    //EFFECTS:
    private JPanel setupButtons() {
        setupSaveButton();
        setupAddButton();
        setupScheduleButton();
        setupProfileButton();

        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBounds(200, 0, 600, 50);
        container.setOpaque(false);
        container.add(saveButton);
        container.add(addButton);
        container.add(viewProfileButton);
        container.add(viewScheduleButton);

        return container;
    }

    private void setupSaveButton() {
        saveButton = new JButton();
        saveButton.setBounds(0, 0, 150, 50);
        saveButton.setIcon(SAVE_BUTTON_IMAGE);
        saveButton.setOpaque(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false);
    }

    private void setupAddButton() {
        addButton = new JButton();
        addButton.setBounds(150, 0, 150, 50);
        addButton.setIcon(ADD_BUTTON_IMAGE);
        addButton.setOpaque(false);
        addButton.setContentAreaFilled(false);
        addButton.setBorderPainted(false);
    }

    private void setupScheduleButton() {
        viewScheduleButton = new JButton();
        viewScheduleButton.setBounds(300, 0, 150, 50);
        viewScheduleButton.setIcon(SCHEDULE_BUTTON_IMAGE);
        viewScheduleButton.setOpaque(false);
        viewScheduleButton.setContentAreaFilled(false);
        viewScheduleButton.setBorderPainted(false);
    }

    private void setupProfileButton() {
        viewProfileButton = new JButton();
        viewProfileButton.setBounds(450, 0, 150, 50);
        viewProfileButton.setIcon(PROFILE_BUTTON_IMAGE);
        viewProfileButton.setOpaque(false);
        viewProfileButton.setContentAreaFilled(false);
        viewProfileButton.setBorderPainted(false);
    }

    //REQUIRES:
    //MODIFIED:
    //EFFECTS:
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private JLayeredPane setupBars() {
        createNewBars();
        setTextForBars();
        setBoundsForBars();
        setFontForBars();
        JPanel barPanel = new JPanel();
        barPanel.setBounds(0,0,200,200);
        barPanel.setLayout(null);
        barPanel.setOpaque(false);
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
        barImage.setBounds(0,0,200,200);
        JLayeredPane lp = new JLayeredPane();
        lp.setBounds(0,0,200,200);
        lp.add(barImage, JLayeredPane.DEFAULT_LAYER);
        lp.add(barPanel, JLayeredPane.MODAL_LAYER);
        myFrame.add(lp);
        myFrame.repaint();
        return lp;
    }

    private void setFontForBars() {
        timeBar.setFont(new Font(FONT_TYPE, Font.PLAIN, TEXT_FONT_SIZE));
        pressureBar.setFont(new Font(FONT_TYPE, Font.PLAIN, TEXT_FONT_SIZE));
        mandarinKnowledgeBar.setFont(new Font(FONT_TYPE, Font.PLAIN, TEXT_FONT_SIZE));
        mathKnowledgeBar.setFont(new Font(FONT_TYPE, Font.PLAIN, TEXT_FONT_SIZE));
        englishKnowledgeBar.setFont(new Font(FONT_TYPE, Font.PLAIN, TEXT_FONT_SIZE));
        s1KnowledgeBar.setFont(new Font(FONT_TYPE, Font.PLAIN, TEXT_FONT_SIZE));
        s2KnowledgeBar.setFont(new Font(FONT_TYPE, Font.PLAIN, TEXT_FONT_SIZE));
        s3KnowledgeBar.setFont(new Font(FONT_TYPE, Font.PLAIN, TEXT_FONT_SIZE));
    }

    private void setBoundsForBars() {
        timeBar.setBounds(0, 0, 200, 25);
        timeBar.setOpaque(false);
        pressureBar.setBounds(0, 25, 200, 25);
        pressureBar.setOpaque(false);
        mandarinKnowledgeBar.setBounds(0, 50, 200, 25);
        mandarinKnowledgeBar.setOpaque(false);
        mathKnowledgeBar.setBounds(0, 75, 200, 25);
        mathKnowledgeBar.setOpaque(false);
        englishKnowledgeBar.setBounds(0, 100, 200, 25);
        englishKnowledgeBar.setOpaque(false);
        s1KnowledgeBar.setBounds(0, 125, 200, 25);
        s1KnowledgeBar.setOpaque(false);
        s2KnowledgeBar.setBounds(0, 150, 200, 25);
        s2KnowledgeBar.setOpaque(false);
        s3KnowledgeBar.setBounds(0, 175, 200, 25);
        s3KnowledgeBar.setOpaque(false);
    }

    private void setTextForBars() {
        timeBar.setText("Time: "  + myStudent.getTime() + "/1000");
        pressureBar.setText("Pressure: " + myStudent.getPressure());
        mandarinKnowledgeBar.setText("Mandarin: " + myStudent.getKnowledge().getMandarinKnowledge());
        mathKnowledgeBar.setText("Math Knowledge: " + myStudent.getKnowledge().getMathKnowledge());
        englishKnowledgeBar.setText("English Knowledge: " + myStudent.getKnowledge().getEnglishKnowledge());
        s1KnowledgeBar.setText(myStudent.getSubjectSelectionOne() + " Knowledge: "
                + myStudent.getKnowledge().getS1Knowledge());
        s2KnowledgeBar.setText(myStudent.getSubjectSelectionTwo() + " Knowledge: "
                + myStudent.getKnowledge().getS2Knowledge());
        s3KnowledgeBar.setText(myStudent.getSubjectSelectionThree() + " Knowledge: "
                + myStudent.getKnowledge().getS3Knowledge());
    }

    private void createNewBars() {
        timeBar = new JLabel();
        pressureBar = new JLabel();
        mandarinKnowledgeBar = new JLabel();
        mathKnowledgeBar = new JLabel();
        englishKnowledgeBar = new JLabel();
        s1KnowledgeBar = new JLabel();
        s2KnowledgeBar = new JLabel();
        s3KnowledgeBar = new JLabel();
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

    private static java.util.List<String> encapAlltime(List<String> list) {
        list.add(NIGHT1);
        list.add(NIGHT2);
        list.add(NIGHT3);
        return list;
    }

    private static java.util.List<String> encapRegStu(List<String> list) {
        list.add(REG_STU1);
        list.add(REG_STU2);
        return list;
    }

    public static void main(String[] args) {
        Student s = new Student("testStudent");
        new MainGamingWindow(s, "dad");
    }
}
