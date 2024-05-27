package it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;
import it.unical.demacs.ai.model.Settings;
import it.unical.demacs.ai.utils.Coordinates;

@Id("placeWallAspettaEInfera")
public class InWall {

    @Param(0)
    private int x1;

    @Param(1)
    private int y1;

    @Param(2)
    private int x2;

    @Param(3)
    private int y2;

    @Param(4)
    private int x3;

    @Param(5)
    private int y3;

    @Param(6)
    private int x4;

    @Param(7)
    private int y4;

    @Param(8)
    private SymbolicConstant orientation;

    public InWall() {
        x1 = 0;
        y1 = 0;
        x2 = 0;
        y2 = 0;
        x3 = 0;
        y3 = 0;
        x4 = 0;
        y4 = 0;
        orientation = null;
    }

    public InWall(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, SymbolicConstant orientation) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.x4 = x4;
        this.y4 = y4;
        this.orientation = orientation;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getX3() {
        return x3;
    }

    public void setX3(int x3) {
        this.x3 = x3;
    }

    public int getY3() {
        return y3;
    }

    public void setY3(int y3) {
        this.y3 = y3;
    }

    public int getX4() {
        return x4;
    }

    public void setX4(int x4) {
        this.x4 = x4;
    }

    public int getY4() {
        return y4;
    }

    public void setY4(int y4) {
        this.y4 = y4;
    }

    public SymbolicConstant getOrientation(){
        return orientation;
    }

    public void setOrientation(SymbolicConstant orientation){
        this.orientation = orientation;
    }

    public Coordinates getCoords(){
        int row = Math.min(Math.min(x1-1, x2-1), Math.min(x3-1, x4-1));
        int column = Math.min(Math.min(y1-1, y2-1), Math.min(y3-1, y4-1));
        return new Coordinates(row, column);
    }

    public Settings.Orientations getOrientationEnum(){
        if(orientation.toString().equals("horizontal"))
            return Settings.Orientations.HORIZONTAL;
        else
            return Settings.Orientations.VERTICAL;
    }

}
