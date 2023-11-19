package ui;

import model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.List;

//represent a window that can show the profile of the student
public class ShowProfileWindow {
    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final Integer TEXT_FONT_SIZE = 15;//font size of text
    //image of student
    public static final ImageIcon PROFILE_IMG = new ImageIcon("./data/resource/StudentProfile.png");

    private JFrame myFrame;//window
    private JLabel studentImage;//student image
    private JLabel infoText;//text label
    private Student student;// represent a student

    //EFFECTS: construct a new show profile window with
    //        1.window size: 400*500
    //        2.window component: a layer pane with student image on DEFAULT_LAYER and infoText on PALETTE_LAYER.
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

    //MODIFIED: this
    //EFFECTS: create a new label with:
    //         1.setBounds(0, 0, 200, 500)
    //         2.setText(html text. toString());
    //         3.setOpaque: false
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


    //MODIFIED: this
    //EFFECTS: set the student image label with bounds(0,0,400,500), icon:PROFILE_IMG
    private void setUpStudentImage() {
        studentImage = new JLabel();
        studentImage.setBounds(0,0,400,500);
        studentImage.setIcon(PROFILE_IMG);
    }

    //MODIFIED: this
    //EFFECTS: frame size (400, 500), setResizable(false), background color: black
    private void setUpFrame() {
        myFrame = new JFrame("Profile");
        myFrame.setSize(400, 500);
        myFrame.setResizable(false);
        myFrame.getContentPane().setBackground(Color.BLACK);
    }
}
