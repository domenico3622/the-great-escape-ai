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
        for(Player player : Game.getInstance().getPlayers()){
            ImageIcon t = new ImageIcon("src/main/resources/" + player.getPath() + ".png");
            Image tscaled = t.getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
            dirImg.put(player.getDirection(), tscaled);
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
        for(Player player: Game.getInstance().getPlayers()){
            g.setColor(player.getColor());
            if ((player.getDirection() == Settings.Directions.UP)) {
                g.fillRect(offset, offset - 25, lineLength, 5);
            } else if ((player.getDirection() == Settings.Directions.DOWN)) {
                g.fillRect(offset, offset + lineLength + 20, lineLength, 5);
            } else if ((player.getDirection() == Settings.Directions.RIGHT)) {
                g.fillRect(offset + lineLength + 20, offset, 5, lineLength);
            } else if ((player.getDirection() == Settings.Directions.LEFT)) {
                g.fillRect(offset - 25, offset, 5, lineLength);
            }
        }
        // Draw the players
        for (Player p : Game.getInstance().getPlayers()) {
            g.drawImage(dirImg.get(p.getDirection()), p.getCoord().column * cellSize + offset, p.getCoord().row * cellSize + offset, null);
        }

        // Draw the walls
        for(int rowIndex = 0; rowIndex < (Settings.boardDim-1); rowIndex++){
            for(int columnIndex = 0; columnIndex < (Settings.boardDim-1); columnIndex++){
                if(Game.getInstance().getWallBoard().get(rowIndex).get(columnIndex).getOrientation() == Settings.Orientations.VOID){
                    continue;
                }
                g.setColor(Game.getInstance().getWallBoard().get(rowIndex).get(columnIndex).getOwner().getColor());
                if(Game.getInstance().getWallBoard().get(rowIndex).get(columnIndex).getOrientation() == Settings.Orientations.HORIZONTAL){
                    g.fillRect((columnIndex) * cellSize + offset + 4, (rowIndex+1) * cellSize + offset - 2, cellSize*2 - 8, 5);
                } else {
                    g.fillRect((columnIndex+1) * cellSize + offset - 2, (rowIndex) * cellSize + offset + 4, 5, cellSize * 2 - 8);
                }
            }
        }

        // Select font
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));

        // Draw the wall available
        for(Player player: Game.getInstance().getPlayers()){
            if(player.getDirection() == Settings.Directions.UP) {
                g.setColor(player.getColor());
                g.drawString(Integer.toString(player.getWallsAvailable()).toString(), offset + lineLength/2 - 10, offset - 35);
            } else if(player.getDirection() == Settings.Directions.DOWN) {
                g.setColor(player.getColor());
                g.drawString(Integer.toString(player.getWallsAvailable()).toString(), offset + lineLength/2 - 10, offset + lineLength + 45);
            } else if(player.getDirection() == Settings.Directions.RIGHT) {
                g.setColor(player.getColor());
                g.drawString(Integer.toString(player.getWallsAvailable()).toString(), offset + lineLength + 35, offset + lineLength/2);
            } else if(player.getDirection() == Settings.Directions.LEFT) {
                g.setColor(player.getColor());
                g.drawString(Integer.toString(player.getWallsAvailable()).toString(), offset - 45, offset + lineLength/2);
            }
        }
        this.repaint();
    }
}

