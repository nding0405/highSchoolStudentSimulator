package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;

public class PressureExceedEnd {
    public static final ImageIcon POPUP_ICON = new ImageIcon("./data/resource/popupIcon.png");
    public static final ImageIcon DIALOGUE_BOX_LEFT = new ImageIcon("./data/resource/dialogueBoxLeft.png");
    public static final ImageIcon DIALOGUE_BOX_RIGHT = new ImageIcon("./data/resource/dialogueBoxRight.png");
    public static final ImageIcon STU_DIED = new ImageIcon("./data/resource/studentDied.png");
    public static final ImageIcon TIRED_STU = new ImageIcon("./data/resource/tiredStudent.png");
    public static final ImageIcon ILL_PLAIN_STU = new ImageIcon("./data/resource/illStudentPlain.png");
    public static final ImageIcon ILL_SMILE_STU = new ImageIcon("./data/resource/illStudentSmile.png");
    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final int TEXT_FONT_SIZE = 20;//size of title
    public static final int HE_INDEX = 7;
    public static final int BE_INDEX = 8;
    public static final int DELAY = 5;
    public static final String END7 = "End7: Your child ended up in the hospital due to overwhelming stress and couldn't "
            + "participate in the college entrance examination. At this moment, you have deeply reflected, hoping "
            + "that with your belated care, your child can recover soon.";
    public static final String END8_FST_PART = "Your child tried to kill him/herself and "
            + "you sent your child to hospital.";
    private static final String END8_SCD_PART = "End8: What's that pink stuff...?";

    private JFrame myFrame;
    private String gender;
    private JLabel background;
    private Student myStudent;
    private JLayeredPane dialogueContainer;
    private JLabel dialogueImageLabel;
    private JLabel dialogueTextLabel;
    private JLabel studentImageLabel;

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

    private void switchFace(ImageIcon face) {
        studentImageLabel.setIcon(face);
    }

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

    private void setupBackground() {
        background = new JLabel();
        background.setBounds(0,0,800,600);
    }

    private void wrapUpComponents() {
        JLayeredPane lp = new JLayeredPane();
        lp.setBounds(0,0,800,600);
        lp.add(background, JLayeredPane.DEFAULT_LAYER);
        lp.add(dialogueContainer, JLayeredPane.PALETTE_LAYER);
        myFrame.add(lp);
        myFrame.setVisible(true);
    }


    private void setUpFrame() {
        myFrame = new JFrame();
        myFrame.getContentPane().setBackground(Color.BLACK);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(800,600);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
    }

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

    private void parentTalk(String text) throws InterruptedException {
        dialogueImageLabel.setIcon(DIALOGUE_BOX_RIGHT);
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + ("You: " + text).replace("\n", "<br>") + "</div></html>");
        simulateDelay(DELAY);
    }

    private void studentTalk(String text) throws InterruptedException {
        dialogueImageLabel.setIcon(DIALOGUE_BOX_LEFT);
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + (myStudent.getName() + ": " + text).replace("\n", "<br>") + "</div></html>");
        simulateDelay(DELAY);
    }

    private void narrationTalk(String text) throws InterruptedException {
        dialogueTextLabel.setText("<html><div style='text-align: center;'>"
                + text.replace("\n", "<br>") + "</div></html>");
        simulateDelay(DELAY);
    }

    private void simulateDelay(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }

    public static void main(String[] args) {
        new PressureExceedEnd(new Student("test"), "mom");
    }
}
