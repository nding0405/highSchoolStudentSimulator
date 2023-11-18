package ui;

import model.Student;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
//build the select game type window:
//------------------------------------
//|             TITLE                |
//|                                  |
//|                                  |
//|                                  |
//|           new game               |
//|           old game               |
//|                                  |
//|                                  |
//------------------------------------

public class SelectGameTypeWindow implements ActionListener {
    public static final String FONT_TYPE = "Courier New";//font type of text
    public static final int TITLE_FONT_SIZE = 20;//size of title
    public static final int BUTTON_FONT_SIZE = 16;//size of button text
    private static final String JSON_STORE = "./data/student.json";//path to the persistence storage.
    JsonReader jsonReader;//to call the read method
    JFrame myFrame;//represent the window
    JButton newGame;//represent the new game button
    JButton oldGame;//represent the old game button
    Student student;//represent student
    String gender;//can only be "dad" or "mom"

    //MODIFIED: this
    //EFFECTS: call all component method and construct the window with all component (button centerPanel TitleLabel
    // background label)
    SelectGameTypeWindow() {
        jsonReader = new JsonReader(JSON_STORE);
        setupGeneralFrame();
        JLabel background = constructBackground();
        JLabel title = constructTitleLabel();
        JLabel mangaImage = constructIcon();
        setupNewGameButton();
        setupOldGameButton();
        JPanel centerPanel = constructCenterPanel(title, mangaImage);

        JLayeredPane lp = new JLayeredPane();
        lp.setLayout(null);
        lp.add(background, JLayeredPane.DEFAULT_LAYER);
        lp.add(centerPanel, JLayeredPane.PALETTE_LAYER);

        myFrame.add(lp);
        myFrame.setVisible(true);
    }

    //EFFECTS: build the center panel(used to contain title label and a manga image label)
    private JPanel constructCenterPanel(JLabel title, JLabel mangaImage) {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null); // Using absolute positioning
        centerPanel.setBounds(0, 0, myFrame.getWidth(), myFrame.getHeight());
        centerPanel.setOpaque(false);
        centerPanel.add(title);
        centerPanel.add(mangaImage);
        centerPanel.add(newGame);
        centerPanel.add(oldGame);
        return centerPanel;
    }

    //MODIFIED: this
    //EFFECTS: set up the frame 1. size(800, 600),
    //                          2. title: "High School Student Simulator"
    //                          3. Set setResizable to false
    //                          4. make sure the terminal will end running if the frame is closed by the user.
    private void setupGeneralFrame() {
        myFrame = new JFrame();
        myFrame.setSize(800, 600);
        myFrame.setTitle("High School Student Simulator");
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myFrame.setResizable(false);
    }

    //EFFECTS: build the title label and return it
    //         1. label text:"Welcome to High School Student Simulator"
    //         2. label size: 500 x 50
    //         3. label position: 180,0
    //         4. label text font: FONT_TYPE
    //         5. label text type: BOLD
    //         4. label text size: TITLE_FONT_SIZE
    private static JLabel constructTitleLabel() {
        JLabel title = new JLabel("Welcome to High School Student Simulator");
        title.setFont(new Font(FONT_TYPE,Font.BOLD, TITLE_FONT_SIZE));
        title.setBounds(180,0,500,50);
        title.setForeground(Color.black);
        return title;
    }

    //MODIFIED: this
    //EFFECTS: set up newGame
    //         1. button text: "Start a new game..."
    //         2. button size: 300 x 30
    //         3. button position: 250,450
    //         4. button text font: FONT_TYPE
    //         5. button text type: PLAIN
    //         4. button text size: BUTTON_FONT_SIZE
    public void setupNewGameButton() {
        newGame = new JButton("Start a new game...");
        newGame.setFont(new Font(FONT_TYPE,Font.PLAIN, BUTTON_FONT_SIZE));
        newGame.setBounds(250, 450, 300, 30); // Example positioning
        newGame.addActionListener(this);
    }

    //MODIFIED: this
    //EFFECTS: set up oldGame
    //         1. button text: "Load old game..."
    //         2. button size: 300 x 30
    //         3. button position: 250,500
    //         4. button text font: FONT_TYPE
    //         5. button text type: PLAIN
    //         4. button text size: BUTTON_FONT_SIZE
    public void setupOldGameButton() {
        oldGame = new JButton("Load old game...");
        oldGame.setFont(new Font(FONT_TYPE,Font.PLAIN,BUTTON_FONT_SIZE));
        oldGame.setBounds(250, 500, 300, 30); // Example positioning
        oldGame.addActionListener(this);
    }

    //EFFECTS: build the background label and return it
    //         1. label text://
    //         2. label size: myFrame.getWidth(), myFrame.getHeight()
    //         3. label position: 0,0
    //         4. label icon: "./data/resource/night1.PNG"
    public JLabel constructBackground() {
        JLabel background = new JLabel(new ImageIcon("./data/resource/night1.PNG"));
        background.setBounds(0, 0, myFrame.getWidth(), myFrame.getHeight());
        return background;
    }

    //EFFECTS: build the icon label and return it
    //         1. label text://
    //         2. label size: 200*220
    //         3. label position: 300,200
    //         4. label icon: "./data/resource/miku.png"
    //         5. label image size: 200*220
    public JLabel constructIcon() {
        ImageIcon myIcon = new ImageIcon("./data/resource/miku.png");
        Image myImage = myIcon.getImage();
        myImage = myImage.getScaledInstance(200,220,Image.SCALE_SMOOTH);
        myIcon = new ImageIcon(myImage);
        JLabel mangaImage = new JLabel();
        mangaImage.setIcon(myIcon);
        mangaImage.setBounds(300, 200, 200, 220);
        mangaImage.setOpaque(false);
        return mangaImage;
    }

    //EFFECTS: load the old game from json file. Assign the read value (student and gender) to the field.
    private void loadStudent() {
        try {
            student = jsonReader.read();
            gender = jsonReader.readGender();
            System.out.println("Loaded " + student.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

//    public static void main(String[] args) {
//        new SelectGameTypeWindow();
//    }

    //MODIFIED: this
    //EFFECTS: when the newGame button is invoked, close this window, start the BackGroundInfoWindow();
    //         when the oldGame button is invoked, close this window, call loadStudent(). Then call \
    //         MainGamingWindow(student,gender);
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(newGame)) {
            myFrame.dispose();
            try {
                new BackGroundInfoWindow();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            myFrame.dispose();
            loadStudent();
            new MainGamingWindow(student,gender);
        }
    }
}