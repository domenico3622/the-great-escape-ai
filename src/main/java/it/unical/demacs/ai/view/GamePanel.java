package it.unical.demacs.ai.view;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
        private int cellSize = 60;
        private int offset = 80;

        private int lineLength = cellSize * 9;


        public GamePanel() {
            super();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, 720, 750);

            g.setColor(Color.WHITE);
            // Draw the grid
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    g.fillRect(i * cellSize + offset, j * cellSize + offset, cellSize, cellSize);
                }
            }

            g.setColor(Color.BLACK);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    g.drawRect(i * cellSize + offset, j * cellSize + offset, cellSize, cellSize);
                }
            }

            // North
            g.setColor(Color.RED);
            g.fillRect(offset, offset - 25, lineLength, 5);

            // South
            g.setColor(Color.BLUE);
            g.fillRect(offset, offset + lineLength + 20, lineLength, 5);

            // East
            g.setColor(Color.GREEN);
            g.fillRect(offset + lineLength + 20, offset, 5, lineLength);

            // West
            g.setColor(Color.YELLOW);
            g.fillRect(offset - 25, offset, 5, lineLength);




        }
}
