package it.unical.demacs.ai.game;
import it.unical.demacs.ai.utils.Pair;
import java.util.*;

public class Game 
{
    private static Game game = new Game();
    private Map<Integer, Pair<Wall, Wall>> walls;
    private int wallId;

    private Game()
    {
        walls = new HashMap<>();
        wallId = 1;
    }
    
    public static Game getInstance() { return game; }
    
    public boolean canMove(Player player, Pair<Integer, Integer> newPos)
    {
        if (newPos.first > Settings.boardDim || newPos.first < 1 || newPos.second > Settings.boardDim || newPos.second < 1) 
            return false;

        if (Math.abs(player.getCoord().first - newPos.first) + Math.abs(player.getCoord().second - newPos.second) != 1)
            return false;

        for (Integer key : walls.keySet())
        {
            Pair<Wall, Wall> wall = walls.get(key);
            if (wall.first.equals(new Wall(player.getCoord(), newPos)) || wall.second.equals(new Wall(player.getCoord(), newPos))) 
                return false;
        
        }
        return true;
    }

    public boolean canPlaceWall(Pair<Wall, Wall> toPlace)
    {
        for (Integer key : walls.keySet())
            if (walls.get(key).equals(toPlace))
                return false;

        for (Integer key : walls.keySet())
        {
            Pair<Wall, Wall> temp = walls.get(key);
            if ((toPlace.first.equals(new Wall(temp.first.getCellA(), temp.second.getCellA())) && 
                toPlace.second.equals(new Wall(temp.first.getCellB(), temp.second.getCellB()))) || 
                (toPlace.second.equals(new Wall(temp.first.getCellA(), temp.second.getCellA())) && 
                toPlace.first.equals(new Wall(temp.first.getCellB(), temp.second.getCellB()))))

                return false;
        }
        return true;
    }


    public void placeWall(Pair<Wall, Wall> toPlace)
    {
        walls.put(wallId, toPlace);
        wallId++;
    }

    public static void main(String [] args){
        Wall wall1 = new Wall(new Pair<Integer,Integer>(1, 1), new Pair<Integer,Integer>(1, 2));
        Wall wall2 = new Wall(new Pair<Integer,Integer>(2, 1), new Pair<Integer,Integer>(2, 2));
        Wall wall3 = new Wall(new Pair<Integer,Integer>(1, 1), new Pair<Integer,Integer>(2, 1));
        Wall wall4 = new Wall(new Pair<Integer,Integer>(1, 2), new Pair<Integer,Integer>(2, 2));
        Game.getInstance().placeWall(new Pair<>(wall3, wall4));
        System.out.println(Game.getInstance().canPlaceWall(new Pair<>(wall2, wall1)));
    }
    
}
