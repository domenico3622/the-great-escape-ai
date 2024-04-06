package it.unical.demacs.ai.game;
import it.unical.demacs.ai.utils.Pair;

public class Player
{
    private Pair<Integer, Integer> coord;

    public Player()
    {
        // TODO inizializzare le coordinate qui in base a dove inizierà il giocatore
    }

    public Pair<Integer, Integer> getCoord() { return coord; }
    public void setCoord(Pair<Integer, Integer> _coord)
    {
        coord = _coord;
    }
}