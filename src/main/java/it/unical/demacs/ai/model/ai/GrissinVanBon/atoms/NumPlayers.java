package it.unical.demacs.ai.model.ai.GrissinVanBon.atoms;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("numPlayers")
public class NumPlayers {
    @Param(0)
    private int NP;
    public NumPlayers(int np) {
        NP = np;
    }
    public NumPlayers() {
    }
    public int getNP() {
        return NP;
    }
    public void setNP(int np) {
        NP = np;
    }
}
