package it.unical.demacs.ai.model.ai.IRS.atoms;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;

@Id("minPath")
public class MinPath {
    @Param(0)
    private SymbolicConstant direction;
    @Param(1)
    private int num;


    public MinPath(SymbolicConstant direction, int num) {
        this.direction = direction;
        this.num = num;
    }

    public SymbolicConstant getDirection() {
        return direction;
    }

    public void setDirection(SymbolicConstant direction) {
        this.direction = direction;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public MinPath() {
    }




}
