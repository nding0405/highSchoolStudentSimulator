package ui;

import javax.swing.*;
import java.awt.*;

public class SelectGameTypeWindow extends GameStarter {
    public static final String FONT_TYPE = "Courier New";
    public static final int TITLE_FONT_SIZE = 20;
    public static final int BUTTON_FONT_SIZE = 16;
    JFrame myFrame;
    JButton newGame;
    JButton oldGame;

    SelectGameTypeWindow() {
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

    private void setupGeneralFrame() {
        myFrame = new JFrame();
        myFrame.setSize(800, 600);
        myFrame.setTitle("High School Student Simulator");
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myFrame.setResizable(false);
    }

    private static JLabel constructTitleLabel() {
        JLabel title = new JLabel("Welcome to High School Student Simulator");
        title.setFont(new Font(FONT_TYPE,Font.BOLD, TITLE_FONT_SIZE));
        title.setBounds(180,0,500,50);
        title.setForeground(Color.black);
        return title;
    }

    public void setupNewGameButton() {
        newGame = new JButton("Start a new game...");
        newGame.setFont(new Font(FONT_TYPE,Font.PLAIN, BUTTON_FONT_SIZE));
        newGame.setBounds(250, 450, 300, 30); // Example positioning
    }

    public void setupOldGameButton() {
        oldGame = new JButton("Load old game...");
        oldGame.setFont(new Font(FONT_TYPE,Font.PLAIN,16));
        oldGame.setBounds(250, 500, 300, 30); // Example positioning
    }

    public JLabel constructBackground() {
        JLabel background = new JLabel(new ImageIcon("./data/resource/night1.PNG"));
        background.setBounds(0, 0, myFrame.getWidth(), myFrame.getHeight());
        return background;
    }

    public JLabel constructIcon() {
        ImageIcon myIcon = new ImageIcon("./data/resource/miku.png");
        Image myImage = myIcon.getImage();
        myImage = myImage.getScaledInstance(200,230,Image.SCALE_SMOOTH);
        myIcon = new ImageIcon(myImage);
        JLabel mangaImage = new JLabel();
        mangaImage.setIcon(myIcon);
        mangaImage.setBounds(300, 200, 200, 230);
        mangaImage.setOpaque(false);
        return mangaImage;
    }

    public static void main(String[] args) {
        new SelectGameTypeWindow();
    }
}