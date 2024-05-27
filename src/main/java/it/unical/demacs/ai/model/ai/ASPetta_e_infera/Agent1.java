package it.unical.demacs.ai.model.ai.ASPetta_e_infera;

import it.unical.demacs.ai.model.Game;
import it.unical.demacs.ai.model.Player;
import it.unical.demacs.ai.model.ai.Agent;
import it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates.AIDimension;
import it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates.AIPlayer;
import it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates.AIWall;
import it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates.InWall;
import it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates.AIMe;
import it.unical.demacs.ai.model.ai.ASPetta_e_infera.predicates.Move;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import it.unical.mat.embasp.specializations.clingo.desktop.ClingoDesktopService;
import it.unical.demacs.ai.model.Settings;

public class Agent1 implements Agent {
    Handler handler;
    final String encodingPath = "src/main/java/it/unical/demacs/ai/model/ai/ASPetta_e_infera/encodings/encoding.lp";

    public Agent1(Player p){
        System.out.println("Agent1");
        String pathToSolver = selectSolverDLV();
        handler = new DesktopHandler(new DLV2DesktopService(pathToSolver));

        try {
            ASPMapper.getInstance().registerClass(AIDimension.class);
            ASPMapper.getInstance().registerClass(AIPlayer.class);
            ASPMapper.getInstance().registerClass(AIWall.class);
            ASPMapper.getInstance().registerClass(AIMe.class);
            ASPMapper.getInstance().registerClass(InWall.class);
            ASPMapper.getInstance().registerClass(Move.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Agent1() {
        System.out.println("Agent1");
        String pathToSolver = selectSolverDLV();
        handler = new DesktopHandler(new DLV2DesktopService(pathToSolver));

        try {
            ASPMapper.getInstance().registerClass(AIDimension.class);
            ASPMapper.getInstance().registerClass(AIPlayer.class);
            ASPMapper.getInstance().registerClass(AIWall.class);
            ASPMapper.getInstance().registerClass(AIMe.class);
            ASPMapper.getInstance().registerClass(InWall.class);
            ASPMapper.getInstance().registerClass(Move.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String selectSolverDLV() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "lib/dlv2.exe";
        } else if (os.contains("nix") || os.contains("nux")) {
            return "lib/dlv2";
        } else if (os.contains("mac")) {
            return "lib/dlv2-mac";
        }
        return null;
    }

    private String selectSolverClingo() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "lib/clingo.exe";
        } else if (os.contains("nix") || os.contains("nux")) {
            return "lib/clingo";
        } else if (os.contains("mac")) {
            return "lib/clingo-mac";
        }
        return null;
    }

    @Override
    public void act() {
        InputProgram program = new ASPInputProgram();
        program.addFilesPath(encodingPath);
        ObjectMapper objectMapper = new ObjectMapper(Game.getInstance().getCurrentPlayer().getDirection());
        try {
            program.addObjectInput(objectMapper.getDimension());
            for (AIWall wall : objectMapper.getWalls()) {
                program.addObjectInput(wall);
            }
            for (AIPlayer player : objectMapper.getPlayers()) {
                program.addObjectInput(player);
            }
            AIMe me = objectMapper.getMe();
            program.addObjectInput(me);
        } catch (Exception e) {
            e.printStackTrace();
        }

        handler.addProgram(program);

        Output output = handler.startSync();
        AnswerSets answers = (AnswerSets) output;
        for (AnswerSet a : answers.getOptimalAnswerSets()) {
            try {
                System.out.println(a.toString());
                for (Object obj : a.getAtoms()) {
                    if (obj instanceof Move) {
                        Move action = (Move) obj;
                        System.out.print("Move: ");
                        System.out.println(action.getDirection());
                        if (action.getDirection() == Settings.Directions.UP)
                            Game.getInstance().move(Settings.Directions.UP);
                        else if (action.getDirection() == Settings.Directions.DOWN)
                            Game.getInstance().move(Settings.Directions.DOWN);
                        else if (action.getDirection() == Settings.Directions.LEFT)
                            Game.getInstance().move(Settings.Directions.LEFT);
                        else if (action.getDirection() == Settings.Directions.RIGHT)
                            Game.getInstance().move(Settings.Directions.RIGHT);
                    } else if (obj instanceof InWall) {
                        InWall action = (InWall) obj;
                        if (Game.getInstance().canPlaceWall(action.getCoords(), action.getOrientationEnum(),
                                Game.getInstance().getCurrentPlayer())) {
                            Game.getInstance().placeWall(action.getCoords(), action.getOrientationEnum(),
                                    Game.getInstance().getCurrentPlayer());
                        }
                        System.out.print("InWall: ");
                        System.out.print(action.getX1());
                        System.out.print(" ");
                        System.out.print(action.getY1());
                        System.out.print(" ");
                        System.out.print(action.getX2());
                        System.out.print(" ");
                        System.out.print(action.getY2());
                        System.out.print(" ");
                        System.out.print(action.getX3());
                        System.out.print(" ");
                        System.out.print(action.getY3());
                        System.out.print(" ");
                        System.out.print(action.getX4());
                        System.out.print(" ");
                        System.out.print(action.getY4());
                        System.out.println(action.getOrientation());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }
        handler.removeAll();

    }

    @Override
    public String getName() {
        return "ASPetta & Infera";
    }

}