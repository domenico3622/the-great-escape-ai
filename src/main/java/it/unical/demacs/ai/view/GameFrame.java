package it.unical.demacs.ai.view;

import javax.swing.*;

public class GameFrame extends JFrame{
        public GamePanel gamePanel;

        public GameFrame() {
            super("The Great Escape - Game");
            JDialog dialog = new JDialog(this, "Main Menu", true);

            dialog.setContentPane(new MainMenu());
            dialog.setResizable(false);
            dialog.pack();
            dialog.setAlwaysOnTop(true);
            dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);

            gamePanel = new GamePanel();
            add(gamePanel);
            setSize(720, 750);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setVisible(true);
        }
}
