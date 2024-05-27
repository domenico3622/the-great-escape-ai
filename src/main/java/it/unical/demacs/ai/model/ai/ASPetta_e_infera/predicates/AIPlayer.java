package it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("playerRaw")
public class AIPlayer {

    @Param(0)
    private int id;

    @Param(1)
    private int x;

    @Param(2)
    private int y;

    @Param(3)
    private String dir;

    @Param(4)
    private int wallCount;

    public AIPlayer() {
    }

    public AIPlayer(int id, int x, int y, String direction, int wallCount) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = direction;
        this.wallCount = wallCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getWallCount() {
        return wallCount;
    }

    public void setWallCount(int wallCount) {
        this.wallCount = wallCount;
    }

}
