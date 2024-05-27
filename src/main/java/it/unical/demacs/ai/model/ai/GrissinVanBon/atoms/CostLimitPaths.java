package it.unical.demacs.ai.model.ai.GrissinVanBon.atoms;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("costLimitPaths")
public class CostLimitPaths {
    @Param(0)
    private int costLimit;

    public CostLimitPaths(int costLimit) {
        this.costLimit = costLimit;
    }

    public CostLimitPaths() {}

    public int getCostLimit() {
        return costLimit;
    }

    public void setCostLimit(int costLimit) {
        this.costLimit = costLimit;
    }

    @Override
    public String toString() {
        return "CostLimitPaths{" +
                "costLimit=" + costLimit +
                '}';
    }
}
