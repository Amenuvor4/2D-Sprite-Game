package main;

import javax.swing.*;
import java.awt.*;

public class Main{


    public static  JFrame window;


    public static void main(String[] args){
        window = new JFrame();
        window.setBackground(Color.black);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("2D adventure");


        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        gamePanel.config.loadConfig();
        //FULL SCREEN STUF
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }


}