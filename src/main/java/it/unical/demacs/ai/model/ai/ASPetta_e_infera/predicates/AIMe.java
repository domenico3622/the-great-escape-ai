package it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("me")
public class AIMe {

    @Param(0)
    private int id;

    public AIMe() {
    }

    public AIMe(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
