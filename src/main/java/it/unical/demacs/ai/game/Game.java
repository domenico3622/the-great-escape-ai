package it.unical.demacs.ai.game;
import it.unical.demacs.ai.utils.Pair;
import java.util.*;

public class Game 
{
    private static Game game = new Game();
    private Map<Integer, Pair<Wall, Wall>> walls;

    private Game()
    {
        walls = new HashMap<>();
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
}
