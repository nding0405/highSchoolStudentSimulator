package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ShowProfileWindow {
    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final Integer TEXT_FONT_SIZE = 15;
    public static final ImageIcon PROFILE_IMG = new ImageIcon("./data/resource/StudentProfile.png");

    private JFrame myFrame;
    private JLabel studentImage;
    private JLabel infoText;
    private Student student;

    public ShowProfileWindow(Student s) {
        student = s;
        setUpFrame();
        setUpStudentImage();
        setUpInfoText();

        JLayeredPane lp = new JLayeredPane();
        lp.add(studentImage, JLayeredPane.DEFAULT_LAYER);
        lp.add(infoText, JLayeredPane.PALETTE_LAYER);
        myFrame.add(lp);
        myFrame.setVisible(true);
    }

    private void setUpInfoText() {
        infoText = new JLabel();
        infoText.setBounds(0, 0, 200, 500);
        List<String> profile = student.studentProfile();

        StringBuilder htmlText = new StringBuilder("<html>");
        for (String oneLine : profile) {
            htmlText.append(oneLine).append("<br>").append("<br>").append("<br>");
        }
        htmlText.append("</html>");

        infoText.setText(htmlText.toString());
        infoText.setFont(new Font(FONT_TYPE, Font.PLAIN, TEXT_FONT_SIZE));
        infoText.setOpaque(false);
        infoText.setForeground(Color.WHITE);
    }


    private void setUpStudentImage() {
        studentImage = new JLabel();
        studentImage.setBounds(0,0,400,500);
        studentImage.setIcon(PROFILE_IMG);
    }

    private void setUpFrame() {
        myFrame = new JFrame("Profile");
        myFrame.setSize(400, 500);
        myFrame.setResizable(false);
        myFrame.getContentPane().setBackground(Color.BLACK);
    }
}
