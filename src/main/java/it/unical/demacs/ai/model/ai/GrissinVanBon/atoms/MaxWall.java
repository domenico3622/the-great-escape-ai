package it.unical.demacs.ai.model.ai.GrissinVanBon.atoms;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("maxWall")
public class MaxWall {
    @Param(0)
    private int MW;
    public MaxWall(int mw) {
        MW = mw;
    }
    public MaxWall() {
    }
    public int getMW() {
        return MW;
    }
    public void setMW(int mw) {
        MW = mw;
    }

}
