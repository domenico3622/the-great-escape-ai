package it.unical.demacs.ai.model.ai.GrissinVanBon.atoms;

import it.unical.demacs.ai.utils.Coordinates;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("newPos")
public class NewPos {
    @Param(0)
    private int R;
    @Param(1)
    private int C;

    public NewPos(int r, int c) {
        R = r;
        C = c;
    }
    public NewPos() {
    }
    public Coordinates getRC() {
        return new Coordinates(R, C);
    }
    public int getR() {
        return R;
    }
    public int getC() {
        return C;
    }

    @Override
    public String toString() {
        return "NewPos{" +
                "R=" + R +
                ", C=" + C +
                '}';
    }

    public void setR(int r) {
        R = r;
    }

    public void setC(int c) {
        C = c;
    }
}
