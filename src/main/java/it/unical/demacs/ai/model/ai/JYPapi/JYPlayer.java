package it.unical.demacs.ai.model.ai.JYPapi;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("player")
public class JYPlayer {
    @Param(0)
    private int id;

    @Param(1)
    private String ob;        //obiettivo

    @Param(2)
    private int numWalls;   //numero di muri che il giocatore ha ancora da piazzare

    @Param(3)
    private int row;        //riga in cui si trova il giocatore

    @Param(4)
    private int col;        //colonna in cui si trova il giocatore


    public JYPlayer(int id, String ob, int numWalls, int row, int col) {
        this.id = id;
        this.ob = ob;
        this.numWalls = numWalls;
        this.row = row;
        this.col = col;
    }

    public JYPlayer() {}

    public int getRow()
    {   return row;  }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumWalls() {
        return numWalls;
    }

    public void setNumWalls(int numWalls) {
        this.numWalls = numWalls;
    }

    public String getOb() {
        return ob;
    }

    public void setOb(String ob) {
        this.ob = ob;
    }
}
