package it.unical.demacs.ai.model;

import java.awt.Color;

import it.unical.demacs.ai.model.Settings.Directions;
import it.unical.demacs.ai.utils.Coordinates;

public class Player
{
    private Coordinates coord;
    private Directions goalDir;
    private int wallsAvailable;
    private Color color;
    private String dirPath;

    public Player(Coordinates _coord, Directions _goalDir, Color _color, String _dirPath){
        coord = _coord;
        goalDir = _goalDir;
        wallsAvailable = 10;
        color = _color;
        dirPath = _dirPath;
    }

    public Coordinates getCoord() { return coord; }
    
    public Directions getDirection() { return goalDir; }
    public void setCoord(Coordinates _coord) { coord = _coord; }

    public int getWallsAvailable() {
        return wallsAvailable;
    }

    public void setWallsAvailable(int wallsNum) {
        wallsAvailable = wallsNum;
    }

    public Color getColor() {
        return color;
    }

    public String getPath() {
        return dirPath;
    }
}