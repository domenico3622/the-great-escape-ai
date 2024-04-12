package it.unical.demacs.ai.model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Settings
{
    public static int boardDim; // dimensioni della board
    public static enum Orientations {VERTICAL, HORIZONTAL, VOID};
    public static enum Directions {RIGHT, LEFT, UP, DOWN, VOID};
    public static Map<Directions, Color> dirCol = new HashMap<>(); // Associa a ogni direzione un colore
    public static Map<Directions, String> dirPath = new HashMap<>(); // Associa a ogni direzione un path
    
}
