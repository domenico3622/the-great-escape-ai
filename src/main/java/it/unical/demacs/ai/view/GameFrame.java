package it.unical.demacs.ai.view;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{
        public GamePanel gamePanel;

        public GameFrame() {
            super("The Great Escape - Game");
            JDialog dialog = new JDialog(this, "Main Menu", true);

            // Aggiungo panel per caricamento che contiene al centro un testo che dice "Caricamento..."
            JPanel panelCaricamento = new JPanel();
            panelCaricamento.setLayout(new BorderLayout());
            JLabel labelCaricamento = new JLabel("Caricamento...");
            labelCaricamento.setHorizontalAlignment(SwingConstants.CENTER);
            panelCaricamento.add(labelCaricamento, BorderLayout.CENTER);
            panelCaricamento.setBackground(Color.LIGHT_GRAY);
            add(panelCaricamento);

            setSize(720, 750);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setVisible(true);

            dialog.setContentPane(new MainMenu());
            dialog.setResizable(false);
            dialog.pack();
            dialog.setAlwaysOnTop(true);
            dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            dialog.setVisible(true);

            gamePanel = new GamePanel();

            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        gamePanel.repaint();
                    }
                },
                250
            );

            add(gamePanel);
            remove(panelCaricamento);
            setVisible(true);
        }
}
