package ui;

import model.Activities;
import model.Exceptions.PressureExceedException;
import model.Exceptions.TimeUpException;
import model.Student;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MainGamingWindow {
    private static final String JSON_STORE = "./data/student.json";

    private static final String[] ACTIVITIES = {"Mandarin", "English", "Math", "Physics", "Chemistry",
            "Biology", "History", "Geology", "Politics", "Jogging", "Hangout with friends", "Hiking", "Drawing",
            "Playing the piano", "VideoGame"};

    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final Integer TEXT_FONT_SIZE = 15;

    public static final String NIGHT1 = "./data/resource/night1.PNG";
    public static final String NIGHT2 = "./data/resource/night2.PNG";
    public static final String NIGHT3 = "./data/resource/night3.PNG";

    private static String STU1 = "initial";
    private static String STU2 = "initial";

    public static final String REG_STU1 = "./data/resource/regularStudent1.PNG";
    public static final String REG_STU2 = "./data/resource/regularStudent2.PNG";

    public static final String STUDY_STU1 = "./data/resource/studyStudent1.PNG";
    public static final String STUDY_STU2 = "./data/resource/studyStudent2.PNG";

    public static final String RELAX_STU1 = "./data/resource/relaxStudent1.PNG";
    public static final String RELAX_STU2 = "./data/resource/relaxStudent2.PNG";

    public static final String DESK = "./data/resource/desk1.png";

    public static final ImageIcon BAR_IMAGE = new ImageIcon("./data/resource/BarImage.png");
    public static final ImageIcon ADD_BUTTON_IMAGE = new ImageIcon("./data/resource/AddButton.png");
    public static final ImageIcon SAVE_BUTTON_IMAGE = new ImageIcon("./data/resource/SaveButton.png");
    public static final ImageIcon PROFILE_BUTTON_IMAGE = new ImageIcon("./data/resource/ProfileButton.png");
    public static final ImageIcon SCHEDULE_BUTTON_IMAGE = new ImageIcon("./data/resource/ScheduleButton.png");

    private static final java.util.List<String> encapsulateOneDay = encapAlltime(new ArrayList<>());

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

    private JComboBox<String> activitiesBox;
    private JTextField typeInTimeBox;

    private String selectedActivity;

    private Student myStudent;
    private String gender;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

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
        initializeJson();
        setupAll();
        changeStuImage("regular");
        startBackgroundTask();
        startRegularStudentTask();
    }

    private void changeStuImage(String type) {
        if (type.equals("regular")) {
            STU1 = REG_STU1;
            STU2 = REG_STU2;
        } else if (type.equals("study")) {
            STU1 = STUDY_STU1;
            STU2 = STUDY_STU2;
        } else {
            STU1 = RELAX_STU1;
            STU2 = RELAX_STU2;
        }
    }

    //REQUIRES:
    //MODIFIED:
    //EFFECTS:
    private void updateBars() {
        setTextForBars();
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
                ImageIcon image1;
                ImageIcon image2;
                while (true) {
                    image1 = new ImageIcon(STU1);
                    image2 = new ImageIcon(STU2);
                    studentImage.setIcon(image1);
                    Thread.sleep(500);
                    studentImage.setIcon(image2);
                    Thread.sleep(500);
                }
            }

            @Override
            protected void done() {
                // Handle completion if needed
            }
        };

        backgroundWorker.execute();
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
        setupFrame();// setup frame look
        JLayeredPane bars = setupBars();//setup bars look
        JPanel buttons = setupButtons();//setup buttons look, buttons action listeners
        JLabel deskImage = setUpDeskImg();//setup desk image
        JLabel background = setupBackGround();//start a doInBG thread
        JLabel studentImg = setUpStudentImg();//start a doInBG thread

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
        deskImage.setIcon(new ImageIcon(DESK));
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
    //MODIFIED: this
    //EFFECTS: setup all 4 bars
    //         1. Horizontally laid at the top
    //         2. Set the background of each button
    //         3. Call helper methods to set action listeners
    private JPanel setupButtons() {
        setupSaveButton();
        setupAddButton();
        setupScheduleButton();
        setupProfileButton();

        JPanel container = new JPanel();
        container.setLayout(null);
        container.setBounds(200, 0, 600, 100);
        container.setOpaque(false);
        container.add(saveButton);
        container.add(addButton);
        container.add(activitiesBox);
        container.add(typeInTimeBox);
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
        saveButtonActionListener();
    }

    private void saveButtonActionListener() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStudent();
            }
        });
    }

    private void setupAddButton() {
        addButton = new JButton();
        addButton.setBounds(150, 0, 150, 50);
        addButton.setIcon(ADD_BUTTON_IMAGE);
        addButton.setOpaque(false);
        addButton.setContentAreaFilled(false);
        addButton.setBorderPainted(false);
        setUpAddButtonBox();//dropdown list
        setUpTimeTextBox();//type in time
        addButtonActionListener();
        addActivitiesBoxActionListener();
        addTextAreaActionListener();
    }

    private void setUpTimeTextBox() {
        typeInTimeBox = new JTextField();
        typeInTimeBox.setBounds(150, 50, 150, 50);
        typeInTimeBox.setVisible(false);
    }

    private void setUpAddButtonBox() {
        activitiesBox = new JComboBox<>(ACTIVITIES);
        activitiesBox.setBounds(150, 50, 150, 50);
        activitiesBox.setVisible(false);
    }

    private void addButtonActionListener() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activitiesBox.setVisible(!activitiesBox.isVisible());
            }
        });
    }

    private void addActivitiesBoxActionListener() {
        activitiesBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addButton.setEnabled(false);//prohibit the add button
                activitiesBox.setVisible(false);//wipe out the dropdown list
                typeInTimeBox.setVisible(true);//show the text area
                String selectedOption = (String) activitiesBox.getSelectedItem();
                selectedActivity = selectedOption;
            }
        });
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void addTextAreaActionListener() {
        typeInTimeBox.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    //do nothing
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        try {
                            String userInput = typeInTimeBox.getText().trim();
                            int intValue = Integer.parseInt(userInput);
                            if (myStudent.validTime(intValue)) {
                                doEveryThingAfterValidTime(intValue);
                            } else {
                                JOptionPane.showMessageDialog(null,
                                        "Your time cannot exceed " + myStudent.getRemainingTime(),
                                        "INVALID INPUT", JOptionPane.PLAIN_MESSAGE);
                            }
                        } catch (NumberFormatException exception) {
                            JOptionPane.showMessageDialog(null,
                                    "Your input is not an integer ",
                                    "INVALID INPUT", JOptionPane.PLAIN_MESSAGE);
                        } finally {
                            typeInTimeBox.setText("");
                            typeInTimeBox.setVisible(false);
                            addButton.setEnabled(true);
                        }
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //do nothing
                }
            });
    }

    private void doEveryThingAfterValidTime(int time) {
        Activities a = new Activities("a",1,false,false);
        a.findActivity(selectedActivity, time);
        myStudent.addActivity(a);
        JOptionPane.showMessageDialog(null,
                "Successfully added " + selectedActivity + " for " + time
                        + " hrs",
                "ACTIVITY ADDED", JOptionPane.PLAIN_MESSAGE);
        updateBars();
        studentAmine(a.getcourseOrPlay());
        detectEnding();
    }

    private void detectEnding() {
        try {
            myStudent.detectEnding();
        } catch (PressureExceedException e) {
            JOptionPane.showMessageDialog(null,
                    "Game end, because the pressure exceed the limit.",
                    "GAME END", JOptionPane.PLAIN_MESSAGE);
        } catch (TimeUpException e) {
            JOptionPane.showMessageDialog(null,
                    "Game end, because the time exceed the limit.",
                    "GAME END", JOptionPane.PLAIN_MESSAGE);
        }
    }


    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void studentAmine(Boolean type) {
        SwingWorker<Void, Void> backgroundWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                if (type) {
                    STU1 = STUDY_STU1;
                    STU2 = STUDY_STU2;
                } else {
                    STU1 = RELAX_STU1;
                    STU2 = RELAX_STU2;
                }
                Thread.sleep(3000);
                STU1 = REG_STU1;
                STU2 = REG_STU2;
                return null;
            }

            @Override
            protected void done() {
                // Handle completion if needed
            }
        };

        backgroundWorker.execute();
    }

    private void setupScheduleButton() {
        viewScheduleButton = new JButton();
        viewScheduleButton.setBounds(300, 0, 150, 50);
        viewScheduleButton.setIcon(SCHEDULE_BUTTON_IMAGE);
        viewScheduleButton.setOpaque(false);
        viewScheduleButton.setContentAreaFilled(false);
        viewScheduleButton.setBorderPainted(false);
        scheduleButtonActionListener();
    }

    private void scheduleButtonActionListener() {
        viewScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> schedule = myStudent.showSchedule();
                new ShowScheduleWindow(schedule);
            }
        });
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

    private void initializeJson() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    private void saveStudent() {
        try {
            jsonWriter.open();
            jsonWriter.write(myStudent, gender);
            jsonWriter.close();
            System.out.println("Saved " + myStudent.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private static java.util.List<String> encapAlltime(List<String> list) {
        list.add(NIGHT1);
        list.add(NIGHT2);
        list.add(NIGHT3);
        return list;
    }


    public static void main(String[] args) {
        Student s = new Student("testStudent");
        new MainGamingWindow(s, "dad");
    }
}
