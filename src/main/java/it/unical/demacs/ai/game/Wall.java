package it.unical.demacs.ai.game;
import it.unical.demacs.ai.utils.Pair;

public class Wall 
{
    private Pair<Integer, Integer> cellA;
    private Pair<Integer, Integer> cellB;

    public Wall(Pair<Integer, Integer> _cellA, Pair<Integer, Integer> _cellB)
    {
        cellA = _cellA;
        cellB = _cellB;
    }

    public Pair<Integer, Integer> getCellA() {
        return cellA;
    }

    public Pair<Integer, Integer> getCellB() {
        return cellB;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;

        if (!(obj instanceof Wall)) return false;

        Wall temp = (Wall) obj;

        return (cellA.equals(temp.getCellA()) && cellB.equals(temp.getCellB())) || (cellA.equals(temp.getCellB()) && cellB.equals(temp.getCellA()));
    }
}
