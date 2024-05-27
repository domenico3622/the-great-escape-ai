package it.unical.demacs.ai.model.ai.IRS.atoms;


import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;

@Id("dim")
public class Dimension {
    @Param(0)
    private int dim;
    public Dimension(int dim) {
        this.dim = dim;
    }

    public int getDim() {
        return dim;
    }

    public void setDim(int dim) {
        this.dim = dim;
    }
    public Dimension() {
    }
}
