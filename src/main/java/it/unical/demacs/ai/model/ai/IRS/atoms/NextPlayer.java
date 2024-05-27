package it.unical.demacs.ai.model.ai.IRS.atoms;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;

@Id("next")
public class NextPlayer {
    @Param(0)
    private SymbolicConstant direction;

    public NextPlayer(SymbolicConstant direction) {
        this.direction = direction;
    }

    public SymbolicConstant getDirection() {
        return direction;
    }

    public void setDirection(SymbolicConstant direction) {
        this.direction = direction;
    }

    public NextPlayer() {
    }


}
