package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShowScheduleWindow extends ShowingWindow {
    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final Integer TEXT_FONT_SIZE = 15;

    protected JFrame myFrame;

    public ShowScheduleWindow(List<String> schedule) {
        super(schedule);
    }
}
