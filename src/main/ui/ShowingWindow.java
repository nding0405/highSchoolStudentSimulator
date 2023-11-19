package ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

//represent a window that will show a list of string
public class ShowingWindow {
    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final Integer TEXT_FONT_SIZE = 15;//font size of text

    protected JFrame myFrame;//the window

    //EFFECT: construct a new ShowingWindow with
    //        1.window size: 300*300
    //        2.window layout: BorderLayout
    //        2.window component: a scrollPane used to display the list
    public ShowingWindow(List<String> schedule) {
        myFrame = new JFrame("Schedule");
        myFrame.setSize(300, 300);
        myFrame.setLayout(new BorderLayout());

        JList<String> scheduleList = new JList<>(schedule.toArray(new String[0]));
        scheduleList.setBackground(Color.BLACK);
        scheduleList.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(scheduleList);
        scrollPane.setFont(new Font(FONT_TYPE, Font.PLAIN, TEXT_FONT_SIZE));
        scrollPane.setOpaque(false);

        myFrame.add(scrollPane, BorderLayout.CENTER);
        myFrame.getContentPane().setBackground(Color.BLACK);
        myFrame.setVisible(true);
    }
}
