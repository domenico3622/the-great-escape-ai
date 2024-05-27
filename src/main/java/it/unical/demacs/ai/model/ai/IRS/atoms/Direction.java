package it.unical.demacs.ai.model.ai.IRS.atoms;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;

@Id("direction")
public class Direction {
    @Param(0)
    private SymbolicConstant direction;
    public Direction(SymbolicConstant direction) {
        this.direction = direction;
    }

    public SymbolicConstant getDirection() {
        return direction;
    }

    public void setDirection(SymbolicConstant direction) {
        this.direction = direction;
    }
    public Direction() {
    }




}
