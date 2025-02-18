package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;

// represent the end for fine art student
public class FineArtEnd {
    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final Integer TEXT_FONT_SIZE = 15;//font size of text
    public static final int DELAY = 5;//delay time
    public static final ImageIcon SMILE_FACE = new ImageIcon("./data/resource/studentSmileFace.png");
    public static final ImageIcon DIALOGUE_BOX_LEFT = new ImageIcon("./data/resource/dialogueBoxLeft.png");
    public static final ImageIcon DIALOGUE_BOX_RIGHT = new ImageIcon("./data/resource/dialogueBoxRight.png");
    private static final String END9 = "End9: Your child worked hard and get into a nice art college. Seems nice right?"
            + "Well, it might be challenging to find a job though... Life is an endless jail...";

    private JFrame myFrame;//represent the window
    private String gender;//represent the gender of the user
    private JLabel background;//represent the background label
    private Student myStudent;//represent the student
    private JLayeredPane dialogueContainer;//represent a layer pane to contain dialogue image and text
    private JLabel dialogueImageLabel;//dialogue image
    private JLabel dialogueTextLabel;//dialogue text
    private JLabel studentImageLabel;//student image

    //construct a new FineArtEnd window
    public FineArtEnd(Student myStudent, String gender) {
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

    //EFFECTS: show the text of END9 for the user
    private void startConversation() throws InterruptedException {
        narrationTalk(END9);
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

    //MODIFIED:this
    //EFFECTS: build a 800x600 frame with black background.
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
        studentImageLabel.setIcon(SMILE_FACE);
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
}
