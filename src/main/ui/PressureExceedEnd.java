package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;

// represent the end for student with pressure greater or equals to MAX_PRESSURE
public class PressureExceedEnd {
    //represent image icons that will be used in th window
    public static final ImageIcon POPUP_ICON = new ImageIcon("./data/resource/popupIcon.png");
    public static final ImageIcon DIALOGUE_BOX_LEFT = new ImageIcon("./data/resource/dialogueBoxLeft.png");
    public static final ImageIcon DIALOGUE_BOX_RIGHT = new ImageIcon("./data/resource/dialogueBoxRight.png");
    public static final ImageIcon STU_DIED = new ImageIcon("./data/resource/studentDied.png");
    public static final ImageIcon TIRED_STU = new ImageIcon("./data/resource/tiredStudent.png");
    public static final ImageIcon ILL_PLAIN_STU = new ImageIcon("./data/resource/illStudentPlain.png");
    public static final ImageIcon ILL_SMILE_STU = new ImageIcon("./data/resource/illStudentSmile.png");
    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final int TEXT_FONT_SIZE = 20;//size of title
    public static final int HE_INDEX = 7;//index of the end (ex. END7)
    public static final int BE_INDEX = 8;//index of the end (ex. END8)
    public static final int DELAY = 5;//delay time
    //represent text that will be shown to the user (endings)
    public static final String END7 = "End7: Your child ended up in the hospital due to overwhelming stress "
            + "and couldn't participate in the college entrance examination. At this moment, you have deeply "
            + "reflected, hoping  that with your belated care, your child can recover soon.";
    //first part of END8
    public static final String END8_FST_PART = "Your child tried to kill him/herself and "
            + "you sent your child to hospital.";
    //second part of END8
    private static final String END8_SCD_PART = "End8: What's that pink stuff...?";

    private JFrame myFrame;//represent the window
    private String gender;//represent the gender of the user
    private JLabel background;//represent the background label
    private Student myStudent;//represent the student
    private JLayeredPane dialogueContainer;//represent a layer pane to contain dialogue image and text
    private JLabel dialogueImageLabel;//dialogue image
    private JLabel dialogueTextLabel;//dialogue text
    private JLabel studentImageLabel;//student image

    //construct a new PressureExceedEnd window
    public PressureExceedEnd(Student myStudent, String gender) {
        this.myStudent = myStudent;
        this.gender = gender;
        setUpFrame();
        setupDialogueBox();
        setupBackground();
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

    //EFFECTS: ask the user whether they support the student:
    //         yes: show end 7
    //         no: show end 8
    private void startConversation() throws InterruptedException {
        studentTalk("I think I'm so tired and I feel like I'm ill...");
        int answer = parentQuestionPopUp(" ","How would you reply?",
                "I'm sorry.", "You are fragile.");
        if (answer == 0) {
            parentTalk("I'm sorry, I gave you so much pressure. (sent " + myStudent.getName() + " to hospital)");
            switchFace(ILL_PLAIN_STU);
            showEnding(HE_INDEX);
        } else {
            parentTalk("Why are you so fragile? Why does it work for everyone else but not for you?"
                    + "Have I ever been a concern to you for all the efforts I've made for you? Stop pretending!");
            showEnding(BE_INDEX);
        }

    }

    //MODIFIED: this
    //EFFECTS: if endIndex==HE_INDEX
    //            show end7
    //         if endIndex==BE_INDEX
    //            show end8
    private void showEnding(int endIndex) throws InterruptedException {
        if (endIndex == HE_INDEX) {
            switchFace(ILL_PLAIN_STU);
            narrationTalk(END7);
        } else if (endIndex == BE_INDEX) {
            studentTalk("...");
            narrationTalk(END8_FST_PART);
            switchFace(ILL_PLAIN_STU);
            studentTalk(gender + ", I think it gonna be hard for me to go back to school.");
            parentTalk("Stop complaining, I'm tired to have you here.");
            switchFace(ILL_SMILE_STU);
            studentTalk("Sure! (happily smiled)");
            studentImageLabel.setVisible(false);
            background.setIcon(STU_DIED);
            narrationTalk(END8_SCD_PART);
        }
    }

    //MODIFIED:this
    //EFFECTS: change the icon of the student to the parameter.
    private void switchFace(ImageIcon face) {
        studentImageLabel.setIcon(face);
    }

    //EFFECTS: create a yes no popup window with title question answer1 answer2 and return the user selection index
    // (if the user click on cancel, keep poping up the same window until user makes a choice)
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
    //EFFECTS: build a new background label with bounds: (0,0,800,600)
    private void setupBackground() {
        background = new JLabel();
        background.setBounds(0,0,800,600);
    }

    //MODIFIED: this
    //EFFECTS: wrap all component to a layer pane and add the pane to the frame
    private void wrapUpComponents() {
        JLayeredPane lp = new JLayeredPane();
        lp.setBounds(0,0,800,600);
        lp.add(background, JLayeredPane.DEFAULT_LAYER);
        lp.add(dialogueContainer, JLayeredPane.PALETTE_LAYER);
        myFrame.add(lp);
        myFrame.setVisible(true);
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
        studentImageLabel.setIcon(TIRED_STU);
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
        simulateDelay(DELAY);
    }

    //MODIFIED: this
    //EFFECTS: 1. change the dialogueImageLabel to right
    //         2. set text to talking content (parameter)
    //         3. wait for DELAY seconds
    private void studentTalk(String text) throws InterruptedException {
        dialogueImageLabel.setIcon(DIALOGUE_BOX_LEFT);
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + (myStudent.getName() + ": " + text).replace("\n", "<br>") + "</div></html>");
        simulateDelay(DELAY);
    }

    //MODIFIED: this
    //EFFECTS:
    //         1. set text to talking content (parameter)
    //         2. wait for DELAY seconds
    private void narrationTalk(String text) throws InterruptedException {
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + text.replace("\n", "<br>") + "</div></html>");
        simulateDelay(DELAY);
    }

    //EFFECTS: pause the thread for seconds.
    private void simulateDelay(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }

//    public static void main(String[] args) {
//        new PressureExceedEnd(new Student("test"), "mom");
//    }
}
