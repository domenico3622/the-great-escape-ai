package it.unical.demacs.ai.model;

import it.unical.demacs.ai.model.Settings.Directions;
import it.unical.demacs.ai.utils.Pair;

public class Player
{
    private Pair<Integer, Integer> coord;
    private Directions goalDir;

    public Player(Pair<Integer, Integer> _coord, Directions _goalDir){
        coord = _coord;
        goalDir = _goalDir;
    }

    public Pair<Integer, Integer> getCoord() { return coord; }
    
    public Directions getDirection() { return goalDir; }
    public void setCoord(Pair<Integer, Integer> _coord) { coord = _coord; }
}