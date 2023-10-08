package model;

public class Parent {
    private String gender;
    private int openMind;

    public Parent(String g) {
        gender = g;
        openMind = 0;
    }



    public void plusOpenmind(int i) {
        openMind += i;
    }
}
