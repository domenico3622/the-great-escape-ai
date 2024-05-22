package it.unical.demacs.ai.model.ai.JYPapi;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("nextPos")
public class NextPos {

    @Param(0)
    private int newRow;

    @Param(1)
    private int newCol;

    public NextPos(int newRow, int newCol) {
        this.newRow = newRow;
        this.newCol = newCol;
    }

    public NextPos() {
    }

    public int getNewRow() {
        return newRow;
    }

    public void setNewRow(int newRow) {
        this.newRow = newRow;
    }

    public int getNewCol() {
        return newCol;
    }

    public void setNewCol(int newCol) {
        this.newCol = newCol;
    }

    @Override
    public String toString() {
        return "NextPos{" +
                "newRow=" + newRow +
                ", newCol=" + newCol +
                '}';
    }
}
