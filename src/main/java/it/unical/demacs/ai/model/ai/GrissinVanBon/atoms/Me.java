package it.unical.demacs.ai.model.ai.GrissinVanBon.atoms;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("me")
public class Me {
    @Param(0)
    private int id;

    public Me(int id) {
        this.id = id;
    }
    public Me() {
    }
    @Override
    public String toString() {
        return "Me{}";
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
