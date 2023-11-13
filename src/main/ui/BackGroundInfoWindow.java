package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;

public class BackGroundInfoWindow {
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
    private static final String BACKGROUND_INFO_SEVEN = "";
    private String[] backgroundTexts = {
            BACKGROUND_INFO_ONE,
            BACKGROUND_INFO_TWO,
            BACKGROUND_INFO_THREE,
            BACKGROUND_INFO_FOUR,
            BACKGROUND_INFO_FIVE,
            BACKGROUND_INFO_SIX,
            BACKGROUND_INFO_SEVEN
    };
    private static final String FONT_TYPE = "Courier New";
    private static final int INFO_FONT_SIZE = 20;
    private JFrame myFrame;
    private JLabel backgroundInfo;

    BackGroundInfoWindow() throws InterruptedException {
        setUpFrame();
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                setupBackgroundInfo();
                return null;
            }

            @Override
            protected void done() {
                myFrame.dispose();
                try {
                    new InitializeStudentWindow();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        worker.execute();
    }

    private void setUpFrame() {
        myFrame = new JFrame();
        myFrame.getContentPane().setBackground(Color.BLACK);
        myFrame.getContentPane().setForeground(Color.BLACK);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(800,600);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
    }

    private void setupBackgroundInfo() throws InterruptedException {
        backgroundInfo = new JLabel("");
        backgroundInfo.setFont(new Font(FONT_TYPE, Font.PLAIN, INFO_FONT_SIZE));
        backgroundInfo.setBounds(400, 300, 800, 600);
        backgroundInfo.setForeground(Color.WHITE);
        backgroundInfo.setOpaque(false);
        backgroundInfo.setHorizontalAlignment(JLabel.CENTER);
        backgroundInfo.setVisible(true);
        myFrame.add(backgroundInfo);
        myFrame.setVisible(true);
        for (int i = 0; i < backgroundTexts.length; i++) {
            backgroundInfo.setText("<html>" + backgroundTexts[i].replace("\n", "<br>") + "</html>");
            myFrame.setVisible(true);
            Thread.sleep(100);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new BackGroundInfoWindow();
    }
}
