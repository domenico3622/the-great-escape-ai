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
    private String name;
    private int illegal;

    public Player(Coordinates _coord, Directions _goalDir, Color _color, String _dirPath){
        coord = _coord;
        goalDir = _goalDir;
        wallsAvailable = 10;
        color = _color;
        name = "";
        illegal = 0;
        dirPath = _dirPath;
    }

    public String toString() {
        return "Player: " + name;
    }

    public int getIllegal() {
        return illegal;
    }

    public void addIllegal(){
        illegal++;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}