package ui;

import exceptions.TimeUpException;
import model.Activities;
import exceptions.PressureExceedException;
import model.Student;
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

// Represent the window for main part of gaming. It includes:
// Bars: time bar, pressure bar, 6 subjects knowledge bars.
// save button: once the user click on it, the game will be saved.
// add button: once the user click on it, one will be able to add activities for the student.
// view schedule button: once the user click on it, one will be view the activities that one added for the student.
// view profile button: once the user click on it, one will be view the basic information of the student.
public class MainGamingWindow {
    //Constants
    //path to json file.
    private static final String JSON_STORE = "./data/student.json";
    //Activities names.
    private static final String[] ACTIVITIES = {"Mandarin", "English", "Math", "Physics", "Chemistry",
            "Biology", "History", "Geology", "Politics", "Jogging", "Hangout with friends", "Hiking", "Drawing",
            "Playing the piano", "VideoGame"};
    //Font type for all text.
    public static final String FONT_TYPE = "Courier New";
    //Font size for all text.
    public static final Integer TEXT_FONT_SIZE = 13;
    //Paths to background images.
    public static final String BG1 = "./data/resource/bg1.png";
    public static final String BG2 = "./data/resource/bg2.png";
    public static final String BG3 = "./data/resource/bg3.png";
    public static final String BG4 = "./data/resource/bg4.png";
    public static final String BG5 = "./data/resource/bg5.png";
    public static final String BG6 = "./data/resource/bg6.png";
    public static final String BG7 = "./data/resource/bg7.png";
    public static final String BG8 = "./data/resource/bg8.png";
    public static final String BG9 = "./data/resource/bg9.png";
    public static final String BG10 = "./data/resource/bg10.png";
    public static final String BG11 = "./data/resource/bg11.png";
    public static final String BG12 = "./data/resource/bg12.png";
    public static final String BG13 = "./data/resource/bg13.png";
    public static final String BG14 = "./data/resource/bg14.png";
    //a substitutable path, it doesn't make any sense now, it will be initialized to a real student image path later.
    private static String STU1 = "initial";
    private static String STU2 = "initial";
    //path to regular student images
    public static final String REG_STU1 = "./data/resource/regularStudent1.PNG";
    public static final String REG_STU2 = "./data/resource/regularStudent2.PNG";
    //path to studying student images
    public static final String STUDY_STU1 = "./data/resource/studyStudent1.PNG";
    public static final String STUDY_STU2 = "./data/resource/studyStudent2.PNG";
    //path to relax student images
    public static final String RELAX_STU1 = "./data/resource/relaxStudent1.PNG";
    public static final String RELAX_STU2 = "./data/resource/relaxStudent2.PNG";
    //path to desk images
    public static final String DESK = "./data/resource/desk1.png";
    //bars and buttons background images.
    public static final ImageIcon BAR_IMAGE = new ImageIcon("./data/resource/BarImage.png");
    public static final ImageIcon ADD_BUTTON_IMAGE = new ImageIcon("./data/resource/AddButton.png");
    public static final ImageIcon SAVE_BUTTON_IMAGE = new ImageIcon("./data/resource/SaveButton.png");
    public static final ImageIcon PROFILE_BUTTON_IMAGE = new ImageIcon("./data/resource/ProfileButton.png");
    public static final ImageIcon SCHEDULE_BUTTON_IMAGE = new ImageIcon("./data/resource/ScheduleButton.png");
    //list of all paths of background images.
    private static final java.util.List<String> encapsulateOneDay = encapAlltime(new ArrayList<>());

    //Fields
    private JFrame myFrame;//the window

    private JLabel pressureBar;//pressure bar
    private JLabel timeBar;//time bar
    private JLabel mandarinKnowledgeBar;//bar for mandarin knowledge
    private JLabel mathKnowledgeBar;//bar for math knowledge
    private JLabel englishKnowledgeBar;//bar for english knowledge
    private JLabel s1KnowledgeBar;//bar for selection1 subject knowledge
    private JLabel s2KnowledgeBar;//bar for selection2 subject knowledge
    private JLabel s3KnowledgeBar;//bar for selection3 subject knowledge

    private JLabel backgroundImage;//a label for showing background images.
    private JLabel studentImage;//a label for showing student images.
    private JLabel deskImage;//a label for showing desk image.

    private JButton saveButton;//a button for save function.
    private JButton addButton;//a button for add activities function.
    private JButton viewScheduleButton;//a button for view added activities function.
    private JButton viewProfileButton;//a button for view student basic information function.

    private JComboBox<String> activitiesBox;//a dropdown list to show activities name.
    private JTextField typeInTimeBox;//a text box to let user type in time for activities.

    private String selectedActivity;//a String to record the selection of the user.

    private Student myStudent;//student object.
    private String gender;//can be either "dad" or "mom".

    private JsonWriter jsonWriter;//a jsonWriter object

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
        startBackgroundTask();
        startRegularStudentTask();
    }

    //MODIFIED: this
    //EFFECTS: if type equals to "regular"
    //            STU1 = REG_STU1;
    //            STU2 = REG_STU2;
    //         if type equals to "study"
    //            STU1 = STUDY_STU1;
    //            STU2 = STUDY_STU1;
    //         if type equals to "relax"
    //            STU1 = RELAX_STU1;
    //            STU2 = STUDY_STU1;
    private void changeStuImage(String type) {
        if (type.equals("regular")) {
            STU1 = REG_STU1;
            STU2 = REG_STU2;
        } else if (type.equals("study")) {
            STU1 = STUDY_STU1;
            STU2 = STUDY_STU2;
        } else if (type.equals("relax")) {
            STU1 = RELAX_STU1;
            STU2 = RELAX_STU1;
        }
    }

    //MODIFIED: this
    //EFFECTS: Start another thread to change the background image.
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
                // do nothing
            }
        };

        backgroundWorker.execute();
    }

    //MODIFIED: this
    //EFFECTS: Start another thread to change the student image.
    private void startRegularStudentTask() {
        changeStuImage("regular");
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
                // do nothing
            }
        };

        backgroundWorker.execute();
    }


    //MODIFIED: this
    //EFFECTS: helper method for startBackgroundTask(). It displays one day cycle background images.
    private void oneDayCycle() throws InterruptedException {
        ImageIcon image;
        for (String s : encapsulateOneDay) {
            image = new ImageIcon(s);
            backgroundImage.setIcon(image);
            Thread.sleep(5000);
        }
    }

    //MODIFIED:this
    //EFFECTS: setup all window components.
    //         1. frame
    //         2. buttons
    //         3. deskImage
    //         4. background
    //         5. studentImg
    //         6. bars
    // Then wrap them in a layer pane (background->bars->buttons->studentImg->deskImage),
    // add the layer pane to the frame.
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

    //MODIFIED: this
    //EFFECTS: create a new JLabel with
    //         bounds:(200,200,400,300)
    //         icon: new ImageIcon(DESK)
    // Then, assign it to deskImage and return deskImage(this step is not necessary,
    // but for better formatting inside the method);
    private JLabel setUpDeskImg() {
        deskImage = new JLabel();
        deskImage.setBounds(200,200,400,300);
        deskImage.setIcon(new ImageIcon(DESK));
        return deskImage;
    }

    //MODIFIED: this
    //EFFECTS: create a new JLabel with
    //         bounds:(200,200,400,300)
    //         icon: new ImageIcon(REG_STU1)
    // Then, assign it to studentImage and return studentImage(this step is not necessary,
    // but for better formatting inside the method);
    private JLabel setUpStudentImg() {
        studentImage = new JLabel();
        studentImage.setBounds(200,200,400,300);
        studentImage.setIcon(new ImageIcon(REG_STU1));
        return studentImage;
    }

    //MODIFIED: this
    //EFFECTS: create a new JLabel with
    //         bounds:(0,0,800,600)
    //         icon: new ImageIcon(BG1)
    // Then, assign it to backgroundImage and return backgroundImage(this step is not necessary,
    // but for better formatting inside the method);
    private JLabel setupBackGround() {
        backgroundImage = new JLabel();
        backgroundImage.setBounds(0,0,800,600);
        backgroundImage.setIcon(new ImageIcon(BG1));
        return backgroundImage;
    }

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

    //MODIFIED: this
    //EFFECTS: setup save button
    //         1.bounds:(0, 0, 150, 50)
    //         2.icon:SAVE_BUTTON_IMAGE
    //         3.opaque: transparent
    //         4.call helper to link action listener: saveButtonActionListener()
    private void setupSaveButton() {
        saveButton = new JButton();
        saveButton.setBounds(0, 0, 150, 50);
        saveButton.setIcon(SAVE_BUTTON_IMAGE);
        saveButton.setOpaque(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false);
        saveButtonActionListener();
    }

    //MODIFIED: this
    //EFFECTS: link action listener to saveButton:
    //         1. call saveStudent() once the user click on saveButton.
    private void saveButtonActionListener() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveStudent();
            }
        });
    }

    //MODIFIED: this
    //EFFECTS: setup add button
    //         1.bounds:(150, 0, 150, 50)
    //         2.icon:ADD_BUTTON_IMAGE
    //         3.opaque: transparent
    //         4.subsets: call helpers to create a dropdown list
    //                    call helpers to create a text field
    //         5.opaque: transparent
    //         6.call helper to link action listener for add button: addButtonActionListener()
    //         7.call helper to link action listener: addActivitiesBoxActionListener()
    //         8.call helper to link action listener: addTextAreaActionListener()
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

    //MODIFIED: this
    //EFFECTS: set up a text box
    //         1.bounds:(150, 50, 150, 50) (right below the add button)
    //         3.setVisible: false(original choice, will be set tp visible once the user click on one
    //         of the options in the dropdown list)
    private void setUpTimeTextBox() {
        typeInTimeBox = new JTextField();
        typeInTimeBox.setBounds(150, 50, 150, 50);
        typeInTimeBox.setText("(type in the time (in hrs) you want for the activity)");
        typeInTimeBox.setVisible(false);
    }

    //MODIFIED: this
    //EFFECTS: set up a dropdown list
    //         1.bounds:(150, 50, 150, 50) (right below the add button)
    //         3.setVisible: false(original choice, will be set tp visible once the user click on add button)
    private void setUpAddButtonBox() {
        activitiesBox = new JComboBox<>(ACTIVITIES);
        activitiesBox.setBounds(150, 50, 150, 50);
        activitiesBox.setVisible(false);
    }

    //MODIFIED: this
    //EFFECTS: link action listener to addButton:
    //         1. change the state of visibility of activitiesBox to inverse once the user click on addButton.
    private void addButtonActionListener() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activitiesBox.setVisible(!activitiesBox.isVisible());
            }
        });
    }

    //MODIFIED: this
    //EFFECTS: link action listener to activitiesBox:
    //         1. disable the addButton once the user click on one of the options(to avoid messing up the layout).
    //         2. activitiesBox.setVisible(false)
    //         3. typeInTimeBox.setVisible(true)
    //         4. record the selection of the user and assign their choice to selectedActivity.
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

    //MODIFIED: this
    //EFFECTS: link action listener to typeInTimeBox:
    //         1. detect the input once the user presses ENTER.
    private void addTextAreaActionListener() {
        typeInTimeBox.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    //do nothing
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        checkTextBoxInput();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    //do nothing
                }
            });
    }

    // EFFECTS:   if Integer.parseInt(userInput) throw exception
    //               catch: NumberFormatException show message to tell the user their input is not an int
    //            if user input is and integer AND myStudent.validTime(intValue) == true
    //               doEveryThingAfterValidTime(intValue)
    //               else: show message to tell the user their input is invalid and show the valid range
    private void checkTextBoxInput() {
        try {
            String userInput = typeInTimeBox.getText().trim();
            int intValue = Integer.parseInt(userInput);
            detectValidTime(intValue);
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

    //EFFECTS: check if the time is a valid time, show popup if it is not
    private void detectValidTime(int intValue) {
        if (myStudent.validTime(intValue)) {
            doEveryThingAfterValidTime(intValue);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Your time cannot less than 5 and cannot exceed "
                            + myStudent.getRemainingTime(),
                    "INVALID INPUT", JOptionPane.PLAIN_MESSAGE);
        }
    }

    //REQUIRES: time should be a valid time (tested by myStudent.validTime(time))
    //MODIFIED: this, Student
    //EFFECTS: 1. find activity based on selectedActivity and time
    //         2. add the activity for student
    //         3. create a message popup to tell the user the activities is successfully added
    //         4. call setTextForBars() to update bars
    //         5. call studentAmine(a.getcourseOrPlay()) to show student anime
    //         6. detectEnding() to check if the game ends.
    private void doEveryThingAfterValidTime(int time) {
        Activities a = new Activities("a",1,false,false);
        a.findActivity(selectedActivity, time);
        myStudent.addActivity(a);
        JOptionPane.showMessageDialog(null,
                "Successfully added " + selectedActivity + " for " + time
                        + " hrs",
                "ACTIVITY ADDED", JOptionPane.PLAIN_MESSAGE);
        setTextForBars();
        studentAmine(a.getcourseOrPlay());
        detectEnding();
    }

    // MODIFIED: this
    // EFFECTS: try myStudent.detectEnding()
    //          catch PressureExceedException: show msg, close this window, start PressureExceedEndWindow
    //          catch TimeUpException: show msg, close this window, start TimeExceedEnd
    private void detectEnding() {
        try {
            myStudent.detectEnding();
        } catch (PressureExceedException e) {
            JOptionPane.showMessageDialog(null,
                    "Game end, because the pressure exceed the limit.",
                    "GAME END", JOptionPane.PLAIN_MESSAGE);
            myFrame.dispose();
            new PressureExceedEnd(myStudent, gender);
        } catch (TimeUpException e) {
            JOptionPane.showMessageDialog(null,
                    "Game end, because the time exceed the limit.",
                    "GAME END", JOptionPane.PLAIN_MESSAGE);
            myFrame.dispose();
            new TimeExceedEnd(myStudent, gender);
        }
    }


    //MODIFIED: this
    //EFFECTS: if (type == true)
    //         change the constant STU1 to STUDY_STU1, STU2 to STUDY_STU2
    //         else
    //         change the constant STU1 to RELAX_STU1, STU2 to RELAX_STU2
    //         (since the student anime thread is repeatedly changing studentImage with STU1 and STU2 every 0.5 second.
    //         While this method change the content of STU1 and STU2 for 3 seconds. The anime will change for 6 ticks.)
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
                return null;
            }

            @Override
            protected void done() {
                STU1 = REG_STU1;
                STU2 = REG_STU2;
            }
        };

        backgroundWorker.execute();
    }

    //MODIFIED: this
    //EFFECTS: set up viewScheduleButton
    //         1.bounds:(300, 0, 150, 50)
    //         2.setIcon: SCHEDULE_BUTTON_IMAGE
    //         3.setOpaque: false
    //         4 call scheduleButtonActionListener() to link action listener to it.
    private void setupScheduleButton() {
        viewScheduleButton = new JButton();
        viewScheduleButton.setBounds(300, 0, 150, 50);
        viewScheduleButton.setIcon(SCHEDULE_BUTTON_IMAGE);
        viewScheduleButton.setOpaque(false);
        viewScheduleButton.setContentAreaFilled(false);
        viewScheduleButton.setBorderPainted(false);
        scheduleButtonActionListener();
    }

    //EFFECTS: link action listener to viewScheduleButton:
    //         1. call myStudent.showSchedule() to get schedule as a list of string
    //         2. add " hrs" after each elements
    //         3. call new ShowScheduleWindow(edited list);
    private void scheduleButtonActionListener() {
        viewScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> schedule = myStudent.showSchedule();
                List<String> edited = new ArrayList<>();
                for (String s : schedule) {
                    edited.add(s + " hrs");
                }
                new ShowScheduleWindow(edited);
            }
        });
    }

    //MODIFIED: this
    //EFFECTS: set up viewProfileButton
    //         1.bounds:(450, 0, 150, 50)
    //         2.setIcon: PROFILE_BUTTON_IMAGE
    //         3.setOpaque: false
    //         4 call profileButtonActionListener() to link action listener to it.
    private void setupProfileButton() {
        viewProfileButton = new JButton();
        viewProfileButton.setBounds(450, 0, 150, 50);
        viewProfileButton.setIcon(PROFILE_BUTTON_IMAGE);
        viewProfileButton.setOpaque(false);
        viewProfileButton.setContentAreaFilled(false);
        viewProfileButton.setBorderPainted(false);
        profileButtonActionListener();
    }

    //EFFECTS: link action listener to viewScheduleButton:
    //         1. call new ShowProfileWindow(myStudent);
    private void profileButtonActionListener() {
        viewProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowProfileWindow(myStudent);
            }
        });
    }

    //MODIFIED: this
    //EFFECTS: setup all 8 bars by using helpers.
    //        createNewBars();
    //        setTextForBars();
    //        setBoundsForBars();
    //        setFontForBars();
    //        Then add all bars to a panel->add panel(MODAL_LAYER)and bar image (DEFAULT_LAYER) to a layer panel and
    //        return it.
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
        JLabel barImage = new JLabel(BAR_IMAGE);
        barImage.setBounds(0,0,200,200);
        JLayeredPane lp = new JLayeredPane();
        lp.setBounds(0,0,200,200);
        lp.add(barImage, JLayeredPane.DEFAULT_LAYER);
        lp.add(barPanel, JLayeredPane.MODAL_LAYER);
        return lp;
    }

    //MODIFIED: this
    //EFFECTS: setup fonts for all 8 bars: new Font(FONT_TYPE, Font.PLAIN, TEXT_FONT_SIZE)
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

    //MODIFIED: this
    //EFFECTS: setup bounds for all 8 bars: vertically and equally spaced in the range: (0,0,200,200)
    //                                      setOpaque(false)
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

    //MODIFIED: this
    //EFFECTS: setup texts for all 8 bars:
    //         timeBar: "Time: "  + myStudent.getTime() + "/1000"
    //         pressureBar: "Pressure: " + myStudent.getPressure() + "/600"
    //         mandarinKnowledgeBar: "Mandarin: " + myStudent.getKnowledge().getMandarinKnowledge()
    //         mathKnowledgeBar: "Math: " + myStudent.getKnowledge().getMathKnowledge()
    //         englishKnowledgeBar: "English Knowledge: " + myStudent.getKnowledge().getEnglishKnowledge()
    //         s1KnowledgeBar: myStudent.getSubjectSelectionOne() + " Knowledge: "
    //                + myStudent.getKnowledge().getS1Knowledge()
    //         s2KnowledgeBar: myStudent.getSubjectSelectionTwo() + " Knowledge: "
    //                + myStudent.getKnowledge().getS2Knowledge()
    //         s3KnowledgeBar: myStudent.getSubjectSelectionThree() + " Knowledge: "
    //                + myStudent.getKnowledge().getS3Knowledge()
    private void setTextForBars() {
        timeBar.setText("Time: "  + myStudent.getTime() + "/1000");
        pressureBar.setText("Pressure: " + myStudent.getPressure() + "/600");
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

    //MODIFIED: this
    //EFFECTS: create JLabel for all 8 bars
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

    //MODIFIED:this
    //EFFECTS: jsonWriter = new JsonWriter(JSON_STORE);
    private void initializeJson() {
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    //EFFECTS: save myStudent and gender to a JSON file with path JSON_STORE.
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

    //EFFECT: construct a list containing all time period in a day.
    private static java.util.List<String> encapAlltime(List<String> list) {
        list.add(BG1);
        list.add(BG2);
        list.add(BG3);
        list.add(BG4);
        list.add(BG5);
        list.add(BG6);
        list.add(BG7);
        list.add(BG8);
        list.add(BG9);
        list.add(BG10);
        list.add(BG11);
        list.add(BG12);
        list.add(BG13);
        list.add(BG14);
        return list;
    }


//    public static void main(String[] args) {
//        Student s = new Student("testStudent");
//        new MainGamingWindow(s, "dad");
//    }
}
