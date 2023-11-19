package ui;

import java.util.List;

//represent a window that will show the result of the final exam
public class ShowExamWindow extends ShowingWindow {
    //EFFECTS: consume a list of string, and pass it to the super
    public ShowExamWindow(List<String> schedule) {
        super(schedule);
    }
}
