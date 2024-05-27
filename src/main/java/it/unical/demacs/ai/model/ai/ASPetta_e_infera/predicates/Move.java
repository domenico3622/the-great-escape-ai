package it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates;

import it.unical.demacs.ai.model.Settings;
import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;

@Id("moveAspettaEInfera")
public class Move {

    @Param(0)
    private SymbolicConstant dir;

    public Move() {
        dir = null;
    }

    public Move(SymbolicConstant move) {
        this.dir = move;
    }

    public SymbolicConstant getDir() {
        return dir;
    }

    public void setDir(SymbolicConstant dir) {
        this.dir = dir;
    }

    public Settings.Directions getDirection(){
        if(dir.toString().equals("up")){
            return Settings.Directions.UP;
        } else if(dir.toString().equals("down")){
            return Settings.Directions.DOWN;
        } else if(dir.toString().equals("left")){
            return Settings.Directions.LEFT;
        } else if(dir.toString().equals("right")){
            return Settings.Directions.RIGHT;
        } else {
            return Settings.Directions.VOID;
        }
    }

}
