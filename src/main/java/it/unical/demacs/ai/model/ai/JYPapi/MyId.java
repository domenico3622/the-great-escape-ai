package it.unical.demacs.ai.model.ai.JYPapi;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("myId")
public class MyId {
    @Param(0)
    private int id;

    public MyId(int id) {
        this.id = id;
    }

    public MyId() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
