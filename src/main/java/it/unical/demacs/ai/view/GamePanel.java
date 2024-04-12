package it.unical.demacs.ai.view;

import it.unical.demacs.ai.model.Game;
import it.unical.demacs.ai.model.Player;
import it.unical.demacs.ai.model.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        for (Settings.Directions dir : Settings.Directions.values()) {
            if (Settings.dirPath.containsKey(dir)) {
                ImageIcon t = new ImageIcon("src/main/resources/" + Settings.dirPath.get(dir) + ".png");
                Image tscaled = t.getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);

                dirImg.put(dir, tscaled);
            }
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, 700, 750);

        g.setColor(Color.WHITE);
        // Draw the grid
        for (int i = 0; i < Settings.boardDim; i++) {
            for (int j = 0; j < Settings.boardDim; j++) {
                g.fillRect(i * cellSize + offset, j * cellSize + offset, cellSize, cellSize);
            }
        }

        g.setColor(Color.BLACK);
        for (int i = 0; i < Settings.boardDim; i++) {
            for (int j = 0; j < Settings.boardDim; j++) {
                g.drawRect(i * cellSize + offset, j * cellSize + offset, cellSize, cellSize);
            }
        }

        // North
        if (Settings.dirCol.containsKey(Settings.Directions.UP)) {
            g.setColor(Settings.dirCol.get(Settings.Directions.UP));
            g.fillRect(offset, offset - 25, lineLength, 5);
        }

        // South
        if (Settings.dirCol.containsKey(Settings.Directions.DOWN)) {
            g.setColor(Settings.dirCol.get(Settings.Directions.DOWN));
            g.fillRect(offset, offset + lineLength + 20, lineLength, 5);
        }

        // East
        if (Settings.dirCol.containsKey(Settings.Directions.RIGHT)) {
            g.setColor(Settings.dirCol.get(Settings.Directions.RIGHT));
            g.fillRect(offset + lineLength + 20, offset, 5, lineLength);
        }

        // West
        if (Settings.dirCol.containsKey(Settings.Directions.LEFT)) {
            g.setColor(Settings.dirCol.get(Settings.Directions.LEFT));
            g.fillRect(offset - 25, offset, 5, lineLength);
        }

        // Draw the players
        for (Player p : Game.getInstance().getPlayers()) {
            if (Settings.dirCol.containsKey(p.getDirection())) {
                g.drawImage(dirImg.get(p.getDirection()), p.getCoord().second * cellSize + offset, p.getCoord().first * cellSize + offset, null);
            }
        }

        // Draw the walls
        for(int i = 0; i < (Settings.boardDim-1); i++){
            for(int j = 0; j < (Settings.boardDim-1); j++){
                if(Game.getInstance().getWallBoard().get(i).get(j) == Settings.Orientations.VOID){
                    continue;
                }
                g.setColor(Settings.dirCol.get(Game.getInstance().getWallBoardPossession().get(i).get(j)));
                if(Game.getInstance().getWallBoard().get(i).get(j) == Settings.Orientations.HORIZONTAL){
                    g.fillRect(i * cellSize + offset + 4, (j+1) * cellSize + offset - 2, cellSize*2 - 8, 5);
                }
                else {
                    g.fillRect((i+1) * cellSize + offset - 2, j * cellSize + offset + 4, 5, cellSize * 2 - 8);
                }
            }
        }

        // Select font
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));

        // Draw the wall available
        if(Game.getInstance().getWallsAvailable().containsKey(Settings.Directions.UP))
        {
            g.setColor(Settings.dirCol.get(Settings.Directions.UP));
            g.drawString(Game.getInstance().getWallsAvailable().get(Settings.Directions.UP).toString(), offset + lineLength/2 - 10, offset - 35);
        }

        if(Game.getInstance().getWallsAvailable().containsKey(Settings.Directions.DOWN))
        {
            g.setColor(Settings.dirCol.get(Settings.Directions.DOWN));
            g.drawString("" + Game.getInstance().getWallsAvailable().get(Settings.Directions.DOWN), offset + lineLength/2 - 10, offset + lineLength + 45);
        }

        if(Game.getInstance().getWallsAvailable().containsKey(Settings.Directions.RIGHT))
        {
            g.setColor(Settings.dirCol.get(Settings.Directions.RIGHT));
            g.drawString("" + Game.getInstance().getWallsAvailable().get(Settings.Directions.RIGHT), offset + lineLength + 35, offset + lineLength/2);
        }

        if(Game.getInstance().getWallsAvailable().containsKey(Settings.Directions.LEFT))
        {
            g.setColor(Settings.dirCol.get(Settings.Directions.LEFT));
            g.drawString("" + Game.getInstance().getWallsAvailable().get(Settings.Directions.LEFT), offset - 45, offset + lineLength/2);
        }

    }
}

