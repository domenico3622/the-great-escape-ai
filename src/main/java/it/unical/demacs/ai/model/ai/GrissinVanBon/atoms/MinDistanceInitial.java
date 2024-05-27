package it.unical.demacs.ai.model.ai.GrissinVanBon.atoms;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("minDistanceInitial")
public class MinDistanceInitial {
    @Param(0)
    private int idPlayer;
    @Param(1)
    private int distanceToWin;

    public MinDistanceInitial(int idPlayer, int distanceToWin) {
        this.idPlayer = idPlayer;
        this.distanceToWin = distanceToWin;
    }
    public MinDistanceInitial() {}

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public int getDistanceToWin() {
        return distanceToWin;
    }

    public void setDistanceToWin(int distanceToWin) {
        this.distanceToWin = distanceToWin;
    }

    @Override
    public String toString() {
        return "MinDistanceInitial{" +
                "idPlayer=" + idPlayer +
                ", distanceToWin=" + distanceToWin +
                '}';
    }
}


