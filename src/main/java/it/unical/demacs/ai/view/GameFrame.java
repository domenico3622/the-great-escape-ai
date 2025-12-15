package it.unical.demacs.ai.view;

import it.unical.demacs.ai.model.Game;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GamePanel gamePanel;
    public ControlPanel controlPanel;

    public GameFrame() {
        super("The Great Escape - Game");

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to default
        }

        JDialog dialog = new JDialog(this, "Main Menu", true);

        // Aggiungo panel per caricamento che contiene al centro un testo che dice
        // "Caricamento..."
        JPanel panelCaricamento = new JPanel();
        panelCaricamento.setLayout(new BorderLayout());
        JLabel labelCaricamento = new JLabel("Caricamento...");
        labelCaricamento.setHorizontalAlignment(SwingConstants.CENTER);
        labelCaricamento.setForeground(Color.WHITE);
        labelCaricamento.setFont(new Font("Arial", Font.BOLD, 24));
        panelCaricamento.add(labelCaricamento, BorderLayout.CENTER);
        panelCaricamento.setBackground(new Color(43, 43, 43));
        add(panelCaricamento);

        setSize(710, 770);
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
        // controlPanel = new ControlPanel();

        remove(panelCaricamento);
        add(gamePanel);
        // JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, gamePanel,
        // controlPanel);
        // splitPane.setDividerLocation(690);
        // splitPane.setEnabled(false);
        // add(splitPane);

        setVisible(true);
        Thread t = new Thread(Game.getInstance().getRunnable());
        t.start();
    }
}
