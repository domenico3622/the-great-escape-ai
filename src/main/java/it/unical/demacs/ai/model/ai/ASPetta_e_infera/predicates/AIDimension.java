package it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("dimension")
public class AIDimension {
    
    @Param(0)
    private int d;

    public AIDimension() {
        d = 0;
    }

    public AIDimension(int d){
        this.d = d;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

}
