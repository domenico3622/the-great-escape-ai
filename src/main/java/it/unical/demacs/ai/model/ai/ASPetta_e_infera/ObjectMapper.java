package it.unical.demacs.ai.model.ai.ASPetta_e_infera;

import it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates.AIMe;
import it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates.AIPlayer;
import it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates.AIDimension;
import it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates.AIWall;
import it.unical.mat.embasp.languages.asp.SymbolicConstant;
import it.unical.demacs.ai.model.Game;
import it.unical.demacs.ai.model.Settings;
import it.unical.demacs.ai.model.Settings.Orientations;
import it.unical.demacs.ai.model.Player;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    Settings.Directions myDir;

    public ObjectMapper(Settings.Directions myDir) {
        this.myDir = myDir;
    }

    public AIDimension getDimension() {
        return new AIDimension(Settings.boardDim);
    }

    public List<AIWall> getWalls() {
        int wallId = 0;
        List<AIWall> wallList = new ArrayList<>();
        for (int row = 0; row < Settings.boardDim - 1; row++) {
            for (int column = 0; column < Settings.boardDim - 1; column++) {
                if (Game.getInstance().getWallBoard().get(row).get(column)
                        .getOrientation() == Orientations.HORIZONTAL) {
                    wallList.add(new AIWall(++wallId, row+1, column+1, row + 2, column+1));
                    wallList.add(new AIWall(wallId, row+1, column + 2, row + 2, column + 2));
                } else if (Game.getInstance().getWallBoard().get(row).get(column)
                        .getOrientation() == Orientations.VERTICAL) {
                    wallList.add(new AIWall(++wallId, row+1, column+1, row+1, column + 2));
                    wallList.add(new AIWall(wallId, row + 2, column+1, row + 2, column + 2));
                }
            }
        }
        return wallList;
    }

    public List<AIPlayer> getPlayers() {
        List<AIPlayer> playerList = new ArrayList<>();
        int playerId = 0;
        for (Player p : Game.getInstance().getPlayers()) {
            playerList.add(new AIPlayer(playerId++, p.getCoord().row+1, p.getCoord().column+1, p.getDirection().toString(), p.getWallsAvailable()));
        }
        return playerList;
    }

    public AIMe getMe() {
        for (int i = 0; i < Game.getInstance().getPlayers().size(); i++) {
            if (Game.getInstance().getPlayers().get(i).getDirection() == myDir) {
                return new AIMe(i);
            }
        }
        return null;
    }

}
