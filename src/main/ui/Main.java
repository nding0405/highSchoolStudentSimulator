package ui;

import model.Event;
import model.EventLog;
import java.util.Iterator;

//Start a new gaming window
public class Main {
    public static void main(String[] args) {
//        GameStarter myGame = new GameStarter();
//        myGame.start();
//        Background myBackground = new Background();
//        myBackground.start();
        new SelectGameTypeWindow();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Program is shutting down. Printing all events:");
            Iterator<Event> events = EventLog.getInstance().iterator();
            while (events.hasNext()) {
                Event e = events.next();
                System.out.println(e.getDate() + " " + e.getDescription());
            }
        }));
    }
}

