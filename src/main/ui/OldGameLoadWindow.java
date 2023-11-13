package ui;

import javax.swing.*;
import java.awt.*;

public class OldGameLoadWindow {
    OldGameLoadWindow() throws InterruptedException {
        JFrame myFrame;
        myFrame = new JFrame();
        myFrame.getContentPane().setBackground(Color.red);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(800,600);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
        Thread.sleep(10000);
//        myFrame.dispose();
    }
}
