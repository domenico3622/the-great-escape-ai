package it.unical.demacs.ai.model.ai.JYPapi;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("wall")
public class OurWall {
    @Param(0)
    int row;

    @Param(1)
    int col;

    @Param(2)
    String orientation;

    public OurWall(int row, int col, String orientation) {
        this.row = row;
        this.col = col;
        this.orientation = orientation;
    }

    public OurWall() {}

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }


}
