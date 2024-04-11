package it.unical.demacs.ai.view;

import it.unical.demacs.ai.model.Game;
import it.unical.demacs.ai.model.Player;
import it.unical.demacs.ai.model.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel {
    private int cellSize = 60;
    private int offset = 80;

    private int lineLength = cellSize * 9;

    Map<Settings.Directions, Image> playersIcon = new HashMap<>();


    public GamePanel() {
        super();

        reload();
        repaint();
    }

    // FIXME: Non funziona
    public void reload(){
        playersIcon.clear();
        Image temp = new ImageIcon("src/main/resources/" + Settings.dirPath.get(Settings.Directions.UP) + "png").getImage();
        Image tempScaled = temp.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
        playersIcon.put(Settings.Directions.UP, tempScaled);

        temp = new ImageIcon("src/main/resources/" + Settings.dirPath.get(Settings.Directions.DOWN) + "png").getImage();
        tempScaled = temp.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
        playersIcon.put(Settings.Directions.DOWN, tempScaled);

        temp = new ImageIcon("src/main/resources/" + Settings.dirPath.get(Settings.Directions.RIGHT) + "png").getImage();
        tempScaled = temp.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
        playersIcon.put(Settings.Directions.RIGHT, tempScaled);

        temp = new ImageIcon("src/main/resources/" + Settings.dirPath.get(Settings.Directions.LEFT) + "png").getImage();
        tempScaled = temp.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
        playersIcon.put(Settings.Directions.LEFT, tempScaled);
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
            if(Settings.dirCol.containsKey(Settings.Directions.UP)){
                g.setColor(Settings.dirCol.get(Settings.Directions.UP));
                g.fillRect(offset, offset - 25, lineLength, 5);
            }

            // South
            if(Settings.dirCol.containsKey(Settings.Directions.DOWN)){
                g.setColor(Settings.dirCol.get(Settings.Directions.DOWN));
                g.fillRect(offset, offset + lineLength + 20, lineLength, 5);
            }

            // East
            if(Settings.dirCol.containsKey(Settings.Directions.RIGHT)){
                g.setColor(Settings.dirCol.get(Settings.Directions.RIGHT));
                g.fillRect(offset + lineLength + 20, offset, 5, lineLength);
            }

            // West
            if(Settings.dirCol.containsKey(Settings.Directions.LEFT)){
                g.setColor(Settings.dirCol.get(Settings.Directions.LEFT));
                g.fillRect(offset - 25, offset, 5, lineLength);
            }

            // Draw the players
            ArrayList<Player> players = (ArrayList<Player>) Game.getInstance().getPlayers();
            for(Player p : players){
                g.drawImage(playersIcon.get(p.getDirection()), p.getCoord().first * cellSize + offset, p.getCoord().second * cellSize + offset, this);
            }

        }
}
