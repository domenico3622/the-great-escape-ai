package it.unical.demacs.ai.view;

import javax.swing.*;

public class GameFrame extends JFrame{
        GamePanel gamePanel;

        public GameFrame() {
            super("The Great Escape - Game");
            gamePanel = new GamePanel();

            //JDialog dialog = new JDialog(this, "Main Menu", true);

            add(gamePanel);
            setSize(720, 750);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setVisible(true);
/*
            dialog.setContentPane(new MainMenu());
            dialog.pack();
            dialog.setAlwaysOnTop(true);
            dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
*/
            gamePanel.repaint();
        }
}
