package it.unical.demacs.ai.view;

import it.unical.demacs.ai.model.Game;
import it.unical.demacs.ai.model.Player;
import it.unical.demacs.ai.model.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel {
    private int cellSize;
    private int offset = 80;
    private int lineLength;
    private Map<Settings.Directions, Image> dirImg = new HashMap<>();

    public GamePanel() {
        super();

        cellSize = 540 / Settings.boardDim;
        lineLength = cellSize * Settings.boardDim;
        for (Player player : Game.getInstance().getPlayers()) {
            ImageIcon t = new ImageIcon("src/main/resources/" + player.getPath() + ".png");
            Image tscaled = t.getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
            dirImg.put(player.getDirection(), tscaled);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Dark background
        g2d.setColor(new Color(43, 43, 43));
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw the grid
        int cellGap = 2;
        int drawnCellSize = cellSize - cellGap;

        for (int i = 0; i < Settings.boardDim; i++) {
            for (int j = 0; j < Settings.boardDim; j++) {
                g2d.setColor(new Color(60, 63, 65));
                g2d.fillRoundRect(i * cellSize + offset, j * cellSize + offset, drawnCellSize, drawnCellSize, 10, 10);
            }
        }

        // Draw direction indicators (walls available)
        g2d.setStroke(new BasicStroke(3));
        for (Player player : Game.getInstance().getPlayers()) {
            g2d.setColor(player.getColor());
            if ((player.getDirection() == Settings.Directions.UP)) {
                g2d.drawLine(offset, offset - 10, offset + lineLength, offset - 10);
            } else if ((player.getDirection() == Settings.Directions.DOWN)) {
                g2d.drawLine(offset, offset + lineLength + 10, offset + lineLength, offset + lineLength + 10);
            } else if ((player.getDirection() == Settings.Directions.RIGHT)) {
                g2d.drawLine(offset + lineLength + 10, offset, offset + lineLength + 10, offset + lineLength);
            } else if ((player.getDirection() == Settings.Directions.LEFT)) {
                g2d.drawLine(offset - 10, offset, offset - 10, offset + lineLength);
            }
        }

        // Draw the players
        for (Player p : Game.getInstance().getPlayers()) {
            Image img = dirImg.get(p.getDirection());
            if (img != null) {
                g2d.drawImage(img, p.getCoord().column * cellSize + offset, p.getCoord().row * cellSize + offset,
                        drawnCellSize, drawnCellSize, null);
            } else {
                g2d.setColor(p.getColor());
                g2d.fillOval(p.getCoord().column * cellSize + offset + 5, p.getCoord().row * cellSize + offset + 5,
                        drawnCellSize - 10, drawnCellSize - 10);
            }
        }

        // Draw the walls
        for (int rowIndex = 0; rowIndex < (Settings.boardDim - 1); rowIndex++) {
            for (int columnIndex = 0; columnIndex < (Settings.boardDim - 1); columnIndex++) {
                if (Game.getInstance().getWallBoard().get(rowIndex).get(columnIndex)
                        .getOrientation() == Settings.Orientations.VOID) {
                    continue;
                }

                // Make wall color slightly brighter or use owner color
                Color ownerColor = Game.getInstance().getWallBoard().get(rowIndex).get(columnIndex).getOwner()
                        .getColor();
                g2d.setColor(ownerColor);

                // Add a glow/outline effect
                g2d.setStroke(new BasicStroke(4));

                if (Game.getInstance().getWallBoard().get(rowIndex).get(columnIndex)
                        .getOrientation() == Settings.Orientations.HORIZONTAL) {
                    int wx = (columnIndex) * cellSize + offset + 4;
                    int wy = (rowIndex + 1) * cellSize + offset - 4; // Centered on the line
                    g2d.drawLine(wx, wy, wx + cellSize * 2 - 8, wy);
                } else {
                    int wx = (columnIndex + 1) * cellSize + offset - 4; // Centered on the line
                    int wy = (rowIndex) * cellSize + offset + 4;
                    g2d.drawLine(wx, wy, wx, wy + cellSize * 2 - 8);
                }
            }
        }

        // Select font
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 18));

        // Draw the wall available count
        for (Player player : Game.getInstance().getPlayers()) {
            g2d.setColor(player.getColor());
            String walls = String.valueOf(player.getWallsAvailable());

            if (player.getDirection() == Settings.Directions.UP) {
                g2d.drawString(walls, offset + lineLength / 2 - 5, offset - 20);
            } else if (player.getDirection() == Settings.Directions.DOWN) {
                g2d.drawString(walls, offset + lineLength / 2 - 5, offset + lineLength + 30);
            } else if (player.getDirection() == Settings.Directions.RIGHT) {
                g2d.drawString(walls, offset + lineLength + 20, offset + lineLength / 2 + 5);
            } else if (player.getDirection() == Settings.Directions.LEFT) {
                g2d.drawString(walls, offset - 30, offset + lineLength / 2 + 5);
            }
        }

        // Draw the turn indicator
        // Background for turn
        int turnX = 320;
        int turnY = 675;
        g2d.setColor(new Color(60, 63, 65));
        g2d.fillOval(turnX - 10, turnY - 10, 70, 70);

        g2d.setColor(Game.getInstance().getCurrentPlayer().getColor());
        g2d.fillOval(turnX, turnY, 50, 50);

        g2d.setColor(new Color(43, 43, 43)); // Dark border
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(turnX, turnY, 50, 50);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2d.drawString("Turn", turnX + 10, turnY + 80);

        try {
            Thread.sleep(50); // Validate lower sleep time for smoother animations if any
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }
}
