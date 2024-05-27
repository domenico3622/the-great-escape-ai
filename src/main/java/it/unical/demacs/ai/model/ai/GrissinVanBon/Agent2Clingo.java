package it.unical.demacs.ai.model.ai.GrissinVanBon;

import it.unical.demacs.ai.model.Game;
import it.unical.demacs.ai.model.Settings;
import it.unical.demacs.ai.model.ai.Agent;
import it.unical.demacs.ai.model.ai.GrissinVanBon.atoms.*;
import it.unical.demacs.ai.utils.Coordinates;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.clingo.desktop.ClingoDesktopService;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Agent2Clingo implements Agent{
    private static String encodingResource="src/main/java/it/unical/demacs/ai/model/ai/GrissinVanBon/aiClingo.asp";
    private static String fileInputPerClingo="src/main/java/it/unical/demacs/ai/model/ai/GrissinVanBon/atomiPerInput.txt";
    private static Handler handler;
    private int myId;
    public Agent2Clingo(int id) {
        myId = id;
    }
    @Override
    public void act() {
        // Apre il file in modalità scrittura e lo chiude immediatamente, svuotandolo
        handler = new DesktopHandler(new ClingoDesktopService("lib/clingo.exe"));

        //OptionDescriptor option=new OptionDescriptor("-n=1"+" --printonlyoptimum");
        //handler.addOption(option);

        InputProgram program = new ASPInputProgram();
        program.addFilesPath(encodingResource);

        //int myID = 0;
        List<it.unical.demacs.ai.model.Player> players = Game.getInstance().getPlayers();

        String atomiInInput = "";

        try{
            ASPMapper.getInstance().registerClass(MaxWall.class);
            ASPMapper.getInstance().registerClass(Me.class);
            ASPMapper.getInstance().registerClass(NewPos.class);
            ASPMapper.getInstance().registerClass(NewWall.class);
            ASPMapper.getInstance().registerClass(NumPlayers.class);
            ASPMapper.getInstance().registerClass(Player.class);
            ASPMapper.getInstance().registerClass(Wall.class);
            ASPMapper.getInstance().registerClass(CostLimitPaths.class);
            ASPMapper.getInstance().registerClass(MinDistanceInitial.class);

            //ad ogni turno del mio player devo svuotare il file esterno dove scrivo i fatti da inviare in input ad asp.
            // Altrimenti rimarrebbero i fatti del turno precedente
            Files.newBufferedWriter(Paths.get(fileInputPerClingo), StandardOpenOption.TRUNCATE_EXISTING).close();

            // Add max wall for player
            atomiInInput += "maxWall("+Game.getInstance().getMaxWallXPlayer()+").";
            //System.out.println("MaxWall: " + Game.getInstance().getMaxWallXPlayer());

            atomiInInput += "me("+myId+").";

            // Add NumPlayers
            atomiInInput += "numPlayers("+players.size()+").";

            // Add players
            for (int i = 0; i < players.size(); i++) {
                it.unical.demacs.ai.model.Player player = players.get(i);
                Settings.Directions direction = player.getDirection();
                String d = "";
                if (direction == Settings.Directions.UP) {
                    d="N";
                } else if (direction == Settings.Directions.DOWN) {
                    d="S";
                } else if (direction == Settings.Directions.LEFT) {
                    d="W";
                } else if (direction == Settings.Directions.RIGHT) {
                    d = "E";
                }
                atomiInInput += "player("+i+","+player.getCoord().row+","+player.getCoord().column+","+player.getWallsAvailable()+",\""+d+"\").";
                //System.out.println("Player: " + i + " " + player.getCoord().row + " " + player.getCoord().column + " " + player.getWallsAvailable() + " " + d);
            }

            int numWalls = 0;
            // Add walls
            for (int i = 0; i < Game.getInstance().getWallBoard().size(); i++) {
                for (int j = 0; j < Game.getInstance().getWallBoard().get(i).size(); j++) {
                    if (Game.getInstance().getWallBoard().get(i).get(j).getOrientation() == Settings.Orientations.HORIZONTAL ){
                        atomiInInput += "wall("+i+","+j+","+(i+1)+","+j+","+i+","+(j+1)+","+(i+1)+","+(j+1)+").";
                        numWalls++;
                        //System.out.println("Wall: " + i + " " + j + " " + (i+1) + " " + j + " " + i + " " + (j+1) + " " + (i+1) + " " + (j+1));
                    }
                    else if (Game.getInstance().getWallBoard().get(i).get(j).getOrientation() == Settings.Orientations.VERTICAL) {
                        atomiInInput += "wall("+i+","+j+","+(i)+","+(j+1)+","+(i+1)+","+(j)+","+(i+1)+","+(j+1)+").";
                        numWalls++;
                        //System.out.println("Wall: " + i + " " + j + " " + i + " " + (j+1) + " " + (i+1) + " " + j + " " + (i+1) + " " + (j+1));
                    }
                }
            }
            //in base al numero di muri presenti nel gioco, posso fare una stima del limite che devo mettere ai cammini
            //minimi per ASP. Ad esempio, se nella mappa ci sono 0 muri, sicuro il cammino minimo di tutti sarà 8, quindi
            //posso mettere come costo limite 8, per ottimizzare al massimo i cammini minimi.
            //se ci sono 2 muri, o "n" muri, ragiono per "paranoia" pensando che tutti sono stati messi per ostacolare me.
            //se ce ne sono 2, il cammino minimo sarà 12, quindi metto come limite 12. e cosi via. In generale
            //il limite sarà 8 + (n*2), con n numero di muri presenti nella mappa.
            //Utilissimo per la fase iniziale mid game.
            //nella fase iniziale la completezza è garantita anche teoricamente. Non puo uscire un cammino minimo superiore
            //a questa formula
            int upperBoundCammini = 8+ numWalls;
            //int upperBoundCammini = 8+(numWalls*2); il *2 non posso metterlo(2)?, in quanto il muro mi allunga di 2, ma
            //io sicuramente non sto fermo, quindi mediamente mi allunga di 1.

            // verso la fase endgame ragionare per paranoia è troppo poco ottimale, quindi metto un limite fisso di 30,
            // giocando numerose partite si è visto che il limite di 30 è un buon compromesso, quasi impossibile che ci
            //sia un cammino superiore di questo in quanto tutti si ostacolano a vicenda e nel frattempo si avanza anche.
            if (upperBoundCammini > 30) {
                upperBoundCammini = 30;
            }
            atomiInInput += "costLimitPaths("+upperBoundCammini+").";

            // DA PERSONA NORMALE:
            Files.write(Paths.get(fileInputPerClingo), atomiInInput.getBytes(StandardCharsets.UTF_8));
            program.addFilesPath(fileInputPerClingo);

            // da psicopatico:
            //Files.write(Paths.get(encodingResource), (atomiInInput + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println("STA ARRIVANDO LA SOLUZIONE -------------------------");
        handler.addProgram(program);
        Output output = handler.startSync();
        Coordinates initialPos = Game.getInstance().getPlayers().get(myId).getCoord();
        AnswerSets answers = (AnswerSets) output;
        for (AnswerSet a: answers.getOptimalAnswerSets()) {
            //System.out.println(a);
            try {
                for (Object obj: a.getAtoms()) {
                    //System.out.println(obj);
                    if (obj instanceof NewPos) {
                        NewPos newPos = (NewPos) obj;
                        int diffR = newPos.getR() - initialPos.row;
                        //System.out.println("InitialPos: " + initialPos.row + " " + initialPos.column);
                        //System.out.println("DiffR: " + diffR);
                        int diffC = newPos.getC() - initialPos.column;
                        //System.out.println("DiffC: " + diffC);
                        if (diffR == 1) {
                            Game.getInstance().move(Settings.Directions.DOWN);
                        } else if (diffR == -1) {
                            Game.getInstance().move(Settings.Directions.UP);
                        } else if (diffC == 1) {
                            Game.getInstance().move(Settings.Directions.RIGHT);
                        } else if (diffC == -1) {
                            Game.getInstance().move(Settings.Directions.LEFT);
                        }
                    }
                    else if (obj instanceof NewWall) {
                        NewWall newWall = (NewWall) obj;
                        Coordinates posMatrix = newWall.getR1C1();
                        Settings.Orientations orientation;
                        if (newWall.getR1C1().row == newWall.getR2C2().row) {
                            orientation = Settings.Orientations.VERTICAL;
                        } else {
                            orientation = Settings.Orientations.HORIZONTAL;
                        }
                        Game.getInstance().placeWall(posMatrix, orientation, players.get(myId));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //System.out.println("FINE SOLUZIONE -------------------------");
        //System.out.println(atomiInInput);

        // se scrivo direttamente nel file asp, una volta ottenuto l'Answer Set devo pensare io ad eliminare i fatti inviati in input
        //eraseLast(encodingResource);
    }

    // se voglio inserire direttamente i fatti io nel file asp dopo aver parserizzato, senza passare la funzione
    // di embasp per renderlo più efficiente, se effettuare la scrittura in un file esterno intermedio, scrivo direttamente li
    public void eraseLast(String filePathInput) {
        try {
            // Leggi tutte le righe del file
            List<String> lines = Files.readAllLines(Paths.get(filePathInput));

            if (!lines.isEmpty()) {
                // Rimuovi l'ultima riga
                lines.remove(lines.size() - 1);

                // Scrivi di nuovo tutte le righe nel file
                Files.write(Paths.get(filePathInput), lines, StandardOpenOption.TRUNCATE_EXISTING);
                //System.out.println("Last line removed successfully");
            } else {
                //System.out.println("File is already empty");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getName() {
        return "Grissin Van Bon";
    }

}
