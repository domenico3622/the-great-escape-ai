package it.unical.demacs.ai.model.ai.GrissinVanBon.atoms;

import it.unical.demacs.ai.utils.Coordinates;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("player")
public class Player {
    @Param(0)
    private int id;
    @Param(1)
    private int R;
    @Param(2)
    private int C;
    @Param(3)
    private int W;
    @Param(4)
    private String D; //Direction

    public Player(int id, int r, int c, int w, String d) {
        this.id = id;
        R = r;
        C = c;
        W = w;
        D = d;
    }
    public Player(){}

    public int getR() {
        return R;
    }
    public int getC() {
        return C;
    }

    public void setR(int r) {
        R = r;
    }

    public void setC(int c) {
        C = c;
    }

    public void setW(int w) {
        W = w;
    }

    public void setD(String d) {
        D = d;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", R=" + R +
                ", C=" + C +
                ", W=" + W +
                ", D=" + D +
                '}';
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Coordinates getRC() {
        return new Coordinates(R, C);
    }
    public int getW() {
        return W;
    }
    public String getD() {
        return D;
    }


}
