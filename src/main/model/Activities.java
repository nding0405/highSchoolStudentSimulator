package model;

public class Activities {
    private int time;
    private int pressure;
    private int knowledge;

    private static String math = "Math class";
    private static String english = "English";
    private static String Physics = "Physics";
    private static String Chemistry = "Chemistry";
    private static String Biology = "Biology";

    private static String Jogging = "Jogging";
    private static String Hangout = "Hangout with friends";
    private static String hiking = "Hiking";
    private static String drawing = "drawing";
    private static String piano = "playing the piano";
    private static String videoGame = "VideoGame";


    public Activities(int time, int pressure, int knowledge) {
        this.time = time;
        this.pressure = pressure;
        this.knowledge = knowledge;
    }


    public void printOutActivities() {
        System.out.println("Math t=10h" + "English t=10h");
    }

    public Boolean detectValidActivity(String name) {
        return true;
    }

    // EFFECT: 根据name找到不同的模型，然后根据时间和模型求出pressure 和knowledge， assign到a上面。
    public Activities findActivity(String name, int time) {
        return null;
    }
}
