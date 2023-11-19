package ui;

import java.util.List;

//represent a window that will show the list of activities
public class ShowScheduleWindow extends ShowingWindow {
    //EFFECTS: consume a list of string, and pass it to the super
    public ShowScheduleWindow(List<String> schedule) {
        super(schedule);
    }
}
