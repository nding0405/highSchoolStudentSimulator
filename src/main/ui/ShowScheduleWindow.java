package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShowScheduleWindow {
    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final Integer TEXT_FONT_SIZE = 15;

    private JFrame myFrame;

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public ShowScheduleWindow(List<String> schedule) {
        List<String> edited = new ArrayList<>();
        for (String s : schedule) {
            edited.add(s + " hrs");
        }

        myFrame = new JFrame("Schedule");
        myFrame.setSize(300, 300);
        myFrame.setLayout(new BorderLayout());

        JList<String> scheduleList = new JList<>(edited.toArray(new String[0]));
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
