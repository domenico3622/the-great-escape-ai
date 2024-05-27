package it.unical.demacs.ai.model.ai.GrissinVanBon.atoms;

import it.unical.demacs.ai.utils.Coordinates;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("newWall")
public class NewWall {
    @Param(0)
    private int R1;
    @Param(1)
    private int C1;
    @Param(2)
    private int R2;
    @Param(3)
    private int C2;
    @Param(4)
    private int R3;
    @Param(5)
    private int C3;
    @Param(6)
    private int R4;
    @Param(7)
    private int C4;

    public NewWall(int r1, int c1, int r2, int c2, int r3, int c3, int r4, int c4) {
        R1 = r1;
        C1 = c1;
        R2 = r2;
        C2 = c2;
        R3 = r3;
        C3 = c3;
        R4 = r4;
        C4 = c4;
    }
    public NewWall(){}
    public Coordinates getR1C1() {
        return new Coordinates(R1, C1);
    }
    public Coordinates getR2C2() {
        return new Coordinates(R2, C2);
    }
    public Coordinates getR3C3() {
        return new Coordinates(R3, C3);
    }
    public Coordinates getR4C4() {
        return new Coordinates(R4, C4);
    }
    @Override
    public String toString() {
        return "NewWall{" +
                "R1=" + R1 +
                ", C1=" + C1 +
                ", R2=" + R2 +
                ", C2=" + C2 +
                ", R3=" + R3 +
                ", C3=" + C3 +
                ", R4=" + R4 +
                ", C4=" + C4 +
                '}';
    }

    public int getR1() {
        return R1;
    }

    public void setR1(int r1) {
        R1 = r1;
    }

    public int getC1() {
        return C1;
    }

    public void setC1(int c1) {
        C1 = c1;
    }

    public int getR2() {
        return R2;
    }

    public void setR2(int r2) {
        R2 = r2;
    }

    public int getC2() {
        return C2;
    }

    public void setC2(int c2) {
        C2 = c2;
    }

    public int getR3() {
        return R3;
    }

    public void setR3(int r3) {
        R3 = r3;
    }

    public int getC3() {
        return C3;
    }

    public void setC3(int c3) {
        C3 = c3;
    }

    public int getR4() {
        return R4;
    }

    public void setR4(int r4) {
        R4 = r4;
    }

    public int getC4() {
        return C4;
    }

    public void setC4(int c4) {
        C4 = c4;
    }
}
