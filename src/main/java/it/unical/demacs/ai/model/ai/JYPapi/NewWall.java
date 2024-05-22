package it.unical.demacs.ai.model.ai.JYPapi;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("newWall")
public class NewWall {

    @Param(0)
    private int rowWall;

    @Param(1)
    private int colWall;

    @Param(2)
    private String orientation;

    public NewWall(int rowWall, int colWall, String orientation) {
        this.rowWall = rowWall;
        this.colWall = colWall;
        this.orientation = orientation;
    }

    public NewWall() {
    }

    public int getRowWall() {
        return rowWall;
    }

    public void setRowWall(int rowWall) {
        this.rowWall = rowWall;
    }

    public int getColWall() {
        return colWall;
    }

    public void setColWall(int colWall) {
        this.colWall = colWall;
    }

    public String getOrientation() {
        return orientation;
    }

    @Override
    public String toString() {
        return "NewWall{" +
                "rowWall=" + rowWall +
                ", colWall=" + colWall +
                ", orientation='" + orientation + '\'' +
                '}';
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
}
