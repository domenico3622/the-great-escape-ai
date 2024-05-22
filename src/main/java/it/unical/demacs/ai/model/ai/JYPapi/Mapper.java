package it.unical.demacs.ai.model.ai.JYPapi;

import it.unical.demacs.ai.model.Game;
import it.unical.demacs.ai.model.Player;
import it.unical.demacs.ai.model.Settings;
import it.unical.demacs.ai.model.Wall;
import it.unical.demacs.ai.utils.Coordinates;

import static it.unical.demacs.ai.model.Settings.Orientations.*;

public class Mapper {

    private static Mapper instance = null;
    private static Game game = Game.getInstance();

    private Mapper() {}

    public static Mapper getInstance() {
        if (instance == null) {
            instance = new Mapper();
        }
        return instance;
    }

    public NewWall mapWallToNewWall (Wall wall, int row, int col)
    {
        NewWall newWall = new NewWall();
        if (wall.getOrientation() != VOID)
        {   newWall.setOrientation(String.valueOf(wall.getOrientation().toString().charAt(0))); }
        else throw new IllegalArgumentException("Wall orientation is VOID");

        newWall.setColWall(row);
        newWall.setRowWall(col);

        return newWall;
    }

    public OurWall mapWallToOurWall (Wall wall, int row, int col)
    {
        OurWall ourWall = new OurWall();
        if (wall.getOrientation() != VOID)
        {   ourWall.setOrientation(String.valueOf(wall.getOrientation().toString().charAt(0))); }
        else throw new IllegalArgumentException("Wall orientation is VOID");

        ourWall.setRow(row);
        ourWall.setCol(col);

        return ourWall;
    }



    public Wall mapNewWallToWall (NewWall newWall)
    {
        Wall wall = new Wall();

        if (newWall.getOrientation().equals("\"V\""))

            wall.setOrientation(VERTICAL) ;
        else
            wall.setOrientation(HORIZONTAL);
        return wall;
    }


    public JYPlayer mapPlayerToASPPlayer (Player player, int index) {
        JYPlayer newPlayer = new JYPlayer();
        newPlayer.setRow(player.getCoord().row);
        newPlayer.setCol(player.getCoord().column);
        newPlayer.setId(index);
        newPlayer.setNumWalls(player.getWallsAvailable());
        Settings.Directions direction = player.getDirection();
        switch (direction) {
            case UP -> newPlayer.setOb("N");
            case DOWN -> newPlayer.setOb("S");
            case LEFT -> newPlayer.setOb("W");
            case RIGHT -> newPlayer.setOb("E");
            default -> throw new IllegalArgumentException("Invalid direction");
        }

        System.out.println(newPlayer.getId()+" "+newPlayer.getOb()+"player" );
        return newPlayer;
    }

    public Player mapASPPlayerToPlayer (JYPlayer JYPlayer, Player player)  //funzione che aggiorna le informazioni del player esistemte grazie a quelle ottenute dall'ASP
    {
        player.setCoord(new Coordinates(JYPlayer.getRow(), JYPlayer.getCol()));
        player.setWallsAvailable(JYPlayer.getNumWalls());
        return player;
    }


}