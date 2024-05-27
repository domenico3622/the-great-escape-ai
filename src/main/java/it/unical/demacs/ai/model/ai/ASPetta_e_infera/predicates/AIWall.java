package it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("wallRaw")
public class AIWall {

    @Param(0)
    private int id;

    @Param(1)
    private int x1;

    @Param(2)
    private int y1;

    @Param(3)
    private int x2;

    @Param(4)
    private int y2;

    public AIWall() {
        id = 0;
        x1 = 0;
        y1 = 0;
        x2 = 0;
        y2 = 0;
    }

    public AIWall(int id, int x1, int y1, int x2, int y2) {
        this.id = id;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}
