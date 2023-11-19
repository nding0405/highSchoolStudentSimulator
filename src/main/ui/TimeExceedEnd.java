package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

// represent the window that shows the time exceed limit end.
public class TimeExceedEnd {
    //represent image icons paths that will be used in th window
    public static final String EXAM = "./data/resource/takeExam.png";
    public static final String GRADUATE = "./data/resource/graduate.png";
    public static final String FACTORY = "./data/resource/factory.png";
    public static final String NOT_FAMOUS = "./data/resource/unFamousSchool.png";
    public static final String FAMOUS = "./data/resource/firstRank.png";
    public static final ImageIcon DIALOGUE_BOX_LEFT = new ImageIcon("./data/resource/dialogueBoxLeft.png");
    public static final ImageIcon DIALOGUE_BOX_RIGHT = new ImageIcon("./data/resource/dialogueBoxRight.png");
    public static final ImageIcon PLAIN_FACE = new ImageIcon("./data/resource/studentPlainFace.png");
    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final int TEXT_FONT_SIZE = 20;//size of title
    //represent text that will be shown to the user (endings)
    private static String END1 = "Your child got a job at an electronics factory. "
            + "Your lofty aspirations were shattered, but over time, you became accustomed to this mundane life, "
            + "and it seemed not too bad after all.";
    private static String END2 = "Your child gained admission to a third-tier university. "
            + "While it was a hard-fought achievement to get into college, in this highly competitive"
            + " and intense society, such educational background may not stand out. The future may not be easy either.";
    private static String END3 = "Your child gained admission to a second-tier university. "
            + "While it was a hard-fought achievement to get into college, in this highly competitive"
            + " and intense society, such educational background may not stand out. The future may not be easy either.";
    private static String END4 = "Your child was admitted to a first-tier university, and the entire family was happy. "
            + "It seemed like a breakthrough, a sudden clearing of the clouds. However, does getting into a top-tier "
            + "university truly mark the end? Human desires are boundless...";
    private static String END5 = "Your child got into a first-class university, and you celebrated with a banquet, "
            + "set off fireworks, and proudly praised your child to everyone you met. But does everything end there?"
            + " Does life truly become smooth sailing afterward?";
    private static String END6 = "Your child is talented, with the opportunity to choose between Tsinghua "
            + "University and Peking University. With such a brilliant brain, one might assume their future life will"
            + " be considerably easier than others. However, each environment comes with its own pressures, "
            + "and perhaps their life is not as smooth sailing as others might imagine.";
    private JFrame myFrame;//represent the window
    private Student myStudent;//represent the student
    private String gender;//represent the gender of the user
    private JLabel background;//represent the background label
    private JLayeredPane dialogueContainer;//represent a layer pane to contain dialogue image and text
    private JLabel dialogueImageLabel;//dialogue image
    private JLabel dialogueTextLabel;//dialogue text
    private JLabel studentImageLabel;//student image
    private JTextField typeInBox;//a text field
    private Integer totalScore;//total score of the student

    //construct a new TimeExceedEnd window
    public TimeExceedEnd(Student myStudent, String gender) {
        this.myStudent = myStudent;
        this.gender = gender;
        setUpFrame();
        setupDialogueBox();
        setupBackground();
        setUpTextBox();
        wrapUpComponents();
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                startConversation();
                return null;
            }

            @Override
            protected void done() {
                //
            }
        };
        worker.execute();
    }

    //MODIFIED: this
    //EFFECTS: wrap all component to a layer pane and add the pane to the frame
    private void wrapUpComponents() {
        JLayeredPane lp = new JLayeredPane();
        lp.setBounds(0,0,800,600);
        lp.add(background, JLayeredPane.DEFAULT_LAYER);
        lp.add(dialogueContainer, JLayeredPane.PALETTE_LAYER);
        lp.add(typeInBox, JLayeredPane.MODAL_LAYER);
        myFrame.add(lp);
        myFrame.setVisible(true);
    }

    //MODIFIED: this
    //EFFECTS: build a new background label with bounds: (0,0,800,600)
    private void setupBackground() {
        background = new JLabel();
        background.setBounds(0,0,800,600);
        background.setIcon(new ImageIcon(GRADUATE));
    }

    //MODIFIED: this
    //EFFECTS: take exam for the student and get scores for all subjects, wrap them as a list of string and return it.
    private List<String> getExamScore() {
        int mandarinScore = myStudent.getKnowledge().takeExamForMandarin();
        int mathScore = myStudent.getKnowledge().takeExamForMath();
        int englishScore = myStudent.getKnowledge().takeExamForEnglish();
        int combinedScore = myStudent.getKnowledge().takeExamForCombinedScienceOrArt(myStudent.getPreference());
        List<String> scores = new ArrayList<>();
        scores.add("Mandarin: " + mandarinScore);
        scores.add("Math: " + mathScore);
        scores.add("English: " + englishScore);
        String combinedType;
        if (myStudent.getScienceOrArtForExam()) {
            combinedType = "Combined Science";
        } else {
            combinedType = "Combined Arts";
        }
        scores.add(combinedType + ": " + combinedScore);
        totalScore = mandarinScore + mathScore + englishScore + combinedScore;
        scores.add("Total score: " + totalScore);
        return scores;
    }

    //MODIFIED: this
    //EFFECTS: ask the user to type in what they want to say to the student.
    private void startConversation() throws InterruptedException {
        studentTalk("Tomorrow is the day to take college entrance exam.");
        parentTypeIn();
    }

    //MODIFIED: this
    //EFFECTS: change the background image based on the parameter
    private void changeBackground(String type) {
        if (type.equals("graduate")) {
            background.setIcon(new ImageIcon(GRADUATE));
        } else if (type.equals("exam")) {
            background.setIcon(new ImageIcon(EXAM));
        }
    }

    //MODIFIED:this
    //EFFECTS: typeInBox.setText: instructions
    //         typeInBox.setVisible(true)
    //         set text to instruct the user in the typeInBox
    private void parentTypeIn() {
        typeInBox.setText("(type in what you want to say to " + myStudent.getName() + ")");
        typeInBox.setVisible(true);
        dialogueTextLabel.setText("You: ");
    }

    //MODIFIED:this
    //EFFECTS: create a new JTextField() with bounds:(380, 450, 200, 50), add actionListener for the textBox
    private void setUpTextBox() {
        typeInBox = new JTextField();
        typeInBox.setBounds(380, 450, 200, 50);
        addTextAreaActionListener();
        typeInBox.setVisible(false);
    }

    //MODIFIED: this
    //EFFECTS: once enter is pressed, call doAfterEnterPressed()
    private void addTextAreaActionListener() {
        typeInBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //do nothing
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    doAfterEnterPressed();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //do nothing
            }
        });
    }

    //MODIFIED: this
    //EFFECTS: record user input and make the student repeat user input
    private void doAfterEnterPressed() {
        String userInput = typeInBox.getText().trim();
        typeInBox.setVisible(false);
        try {
            parentTalk(userInput);
            studentTalk("' " + userInput + " ' " + "..." + "Ok " + gender + " I got it.");
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        changeBackground("exam");
        List<String> scores = getExamScore();
        new ShowExamWindow(scores);
        popUpFinalScore();
    }

    //create a popup window to show the total score of the student on final exam
    private void popUpFinalScore() {
        JOptionPane.showMessageDialog(null, myStudent.getName() + " got "
                        + totalScore + " for final exam.", "FINIAL SCORE", JOptionPane.PLAIN_MESSAGE);
        int ending = myStudent.endChoice(totalScore);
        showEnding(ending);
    }

    //EFFECTS: show ending based on the ending index
    private void showEnding(int ending) {
        if (ending == 0) {
            endOne();
        } else if (ending == 1) {
            endTwo();
        } else if (ending == 2) {
            endThree();
        } else if (ending == 3) {
            endFour();
        } else if (ending == 4 || ending == 5) {
            endFive();
        } else if (ending == 6 || ending == 7) {
            endSix();
        }
    }

    //MODIFIED: this
    //EFFECTS: change the background image to FACTORY
    //         visualize the student image
    //         show END1 text
    private void endOne() {
        background.setIcon(new ImageIcon(FACTORY));
        studentImageLabel.setVisible(false);
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + END1.replace("\n", "<br>") + "</div></html>");
    }

    //MODIFIED: this
    //EFFECTS: change the background image to NOT_FAMOUS
    //         visualize the student image
    //         show END2 text
    private void endTwo() {
        background.setIcon(new ImageIcon(NOT_FAMOUS));
        studentImageLabel.setVisible(false);
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + END2.replace("\n", "<br>") + "</div></html>");
    }

    //MODIFIED: this
    //EFFECTS: change the background image to NOT_FAMOUS
    //         visualize the student image
    //         show END3 text
    private void endThree() {
        background.setIcon(new ImageIcon(NOT_FAMOUS));
        studentImageLabel.setVisible(false);
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + END3.replace("\n", "<br>") + "</div></html>");
    }

    //MODIFIED: this
    //EFFECTS: change the background image to NOT_FAMOUS
    //         visualize the student image
    //         show END4 text
    private void endFour() {
        background.setIcon(new ImageIcon(NOT_FAMOUS));
        studentImageLabel.setVisible(false);
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + END4.replace("\n", "<br>") + "</div></html>");
    }

    //MODIFIED: this
    //EFFECTS: change the background image to NOT_FAMOUS
    //         visualize the student image
    //         show END5 text
    private void endFive() {
        background.setIcon(new ImageIcon(NOT_FAMOUS));
        studentImageLabel.setVisible(false);
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + END5.replace("\n", "<br>") + "</div></html>");
    }

    //MODIFIED: this
    //EFFECTS: change the background image to FAMOUS
    //         visualize the student image
    //         show END6 text
    private void endSix() {
        background.setIcon(new ImageIcon(FAMOUS));
        studentImageLabel.setVisible(false);
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + END6.replace("\n", "<br>") + "</div></html>");
    }

    //MODIFIED: this
    //EFFECTS: build a new background label with bounds: (0,0,800,600)
    private void setUpFrame() {
        myFrame = new JFrame();
        myFrame.getContentPane().setBackground(Color.BLACK);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(800,600);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
    }

    //MODIFIED:this
    //EFFECTS: setup dialogue box:
    //         1. dialogueContainer with bounds:(0, 0, 800, 600)
    //         2. studentImageLabel with bounds:(0, 55, 300, 330) and icon SMILE_FACE
    //         3. dialogueImageLabel with bounds:(0, 350, 800, 250)
    //         4. dialogueTextLabel with bounds:(0, 55, 300, 330) and icon SMILE_FACE
    //         5. add all component to dialogueContainer.
    private void setupDialogueBox() {
        dialogueContainer = new JLayeredPane();
        dialogueContainer.setBounds(0, 0, 800, 600);
        dialogueContainer.setBackground(Color.BLACK);

        studentImageLabel = new JLabel();
        studentImageLabel.setIcon(PLAIN_FACE);
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
    }

    //MODIFIED: this
    //EFFECTS: 1. change the dialogueImageLabel to left
    //         2. set text to talking content (parameter)
    //         3. wait for DELAY seconds
    private void parentTalk(String text) throws InterruptedException {
        dialogueImageLabel.setIcon(DIALOGUE_BOX_RIGHT);
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + ("You: " + text).replace("\n", "<br>") + "</div></html>");
        simulateDelay(3);
    }

    //MODIFIED: this
    //EFFECTS: 1. change the dialogueImageLabel to right
    //         2. set text to talking content (parameter)
    //         3. wait for DELAY seconds
    private void studentTalk(String text) throws InterruptedException {
        dialogueImageLabel.setIcon(DIALOGUE_BOX_LEFT);
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + (myStudent.getName() + ": " + text).replace("\n", "<br>") + "</div></html>");
        simulateDelay(3);
    }

    //EFFECTS: pause the thread for seconds.
    private void simulateDelay(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }

//    public static void main(String[] args) {
//        new TimeExceedEnd(new Student("test"), "mom");
//    }
}
