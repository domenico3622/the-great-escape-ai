package it.unical.demacs.ai.model.ai.GrissinVanBon;

import it.unical.demacs.ai.model.Game;
import it.unical.demacs.ai.model.Settings;
import it.unical.demacs.ai.model.ai.Agent;
import it.unical.demacs.ai.model.ai.GrissinVanBon.atoms.*;
import it.unical.demacs.ai.utils.Coordinates;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.clingo.ClingoAnswerSets;
import it.unical.mat.embasp.specializations.clingo.desktop.ClingoDesktopService;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

import java.util.*;

public class Agent2 implements Agent{
    private static String encodingResource="src/main/java/it/unical/demacs/ai/model/ai/GrissinVanBon/aiDlv2.asp";
    private static Handler handler;
    private int myId;
    public Agent2(int id) {
        myId = id;
    }

    @Override
    public void act() {
        handler = new DesktopHandler(new DLV2DesktopService(Settings.executablePath("dlv2")));

        //OptionDescriptor option=new OptionDescriptor("-n=1"+" --printonlyoptimum");
        //handler.addOption(option);

        InputProgram program = new ASPInputProgram();
        program.addFilesPath(encodingResource);

        //int myID = 0;
        List<it.unical.demacs.ai.model.Player> players = Game.getInstance().getPlayers();

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

            // Add max wall for player
            program.addObjectInput(new MaxWall(Game.getInstance().getMaxWallXPlayer()));
            //System.out.println("MaxWall: " + Game.getInstance().getMaxWallXPlayer());

            program.addObjectInput(new Me(myId));

            // Add NumPlayers
            program.addObjectInput(new NumPlayers(players.size()));

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
                program.addObjectInput(new Player(i, player.getCoord().row, player.getCoord().column, player.getWallsAvailable(),d));
                //System.out.println("Player: " + i + " " + player.getCoord().row + " " + player.getCoord().column + " " + player.getWallsAvailable() + " " + d);
            }

            int numWalls = 0;
            // Add walls
            for (int i = 0; i < Game.getInstance().getWallBoard().size(); i++) {
                for (int j = 0; j < Game.getInstance().getWallBoard().get(i).size(); j++) {
                    if (Game.getInstance().getWallBoard().get(i).get(j).getOrientation() == Settings.Orientations.HORIZONTAL ){
                        program.addObjectInput(new Wall(i,j,i+1,j,i,j+1,i+1,j+1));
                        numWalls++;
                        //System.out.println("Wall: " + i + " " + j + " " + (i+1) + " " + j + " " + i + " " + (j+1) + " " + (i+1) + " " + (j+1));
                    }
                    else if (Game.getInstance().getWallBoard().get(i).get(j).getOrientation() == Settings.Orientations.VERTICAL) {
                        program.addObjectInput(new Wall(i, j, i, j + 1, i + 1, j, i + 1, j + 1));
                        numWalls++;
                        //System.out.println("Wall: " + i + " " + j + " " + i + " " + (j+1) + " " + (i+1) + " " + j + " " + (i+1) + " " + (j+1));
                    }
                }
            }
            /** in base al numero di muri presenti nel gioco, posso fare una stima del limite che devo mettere ai cammini
            minimi per ASP. Ad esempio, se nella mappa ci sono 0 muri, sicuro il cammino minimo di tutti sarà 8, quindi
            posso mettere come costo limite 8, per ottimizzare al massimo i cammini minimi.
            se ci sono 2 muri, o "n" muri, ragiono per "paranoia" pensando che tutti sono stati messi per ostacolare me.
            se ce ne sono 2, il cammino minimo sarà 12, quindi metto come limite 12. e cosi via. In generale
            il limite sarà 8 + (n*2), con n numero di muri presenti nella mappa.
            Utilissimo per la fase iniziale mid game.
            nella fase iniziale la completezza è garantita anche teoricamente. Non puo uscire un cammino minimo superiore
            a questa formula */
            int upperBoundCammini = 8+ numWalls;
            //int upperBoundCammini = 8+(numWalls*2); il *2 posso non metterlo(2)?, in quanto il muro mi allunga di 2, ma
            //io sicuramente non sto fermo, quindi mediamente mi allunga di 1.

            // verso la fase endgame ragionare per paranoia è troppo poco ottimale, quindi metto un limite fisso di 30,
            // giocando numerose partite si è visto che il limite di 30 è un buon compromesso, quasi impossibile che ci
            //sia un cammino superiore di questo in quanto tutti si ostacolano a vicenda e nel frattempo si avanza anche.
            if (upperBoundCammini > 30) {
                upperBoundCammini = 30;
            }
            program.addObjectInput(new CostLimitPaths(upperBoundCammini));

            /*
            for (int i = 0; i < Game.getInstance().getPlayers().size(); i++) {
                it.unical.demacs.ai.model.Player p = Game.getInstance().getPlayers().get(i);
                int dist = dijkstra(p);
                program.addObjectInput(new MinDistanceInitial(i, dist));
            }
             */
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
            System.out.println(a);
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
    }

    static class Cell {
        int row, col, distance;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    static int dijkstra(it.unical.demacs.ai.model.Player p) {
        int startRow = p.getCoord().row;
        int startCol = p.getCoord().column;
        Settings.Directions winDir = p.getDirection();
        int rows = Settings.boardDim;
        int cols = Settings.boardDim;
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // Movimenti possibili: down, up, right, left

        // Mappa per tenere traccia delle distanze minime
        Map<Cell, Integer> distances = new HashMap<>();
        Cell startCell = new Cell(startRow, startCol);
        distances.put(startCell, 0);

        // Set per tenere traccia dei nodi visitati
        Set<Cell> visited = new HashSet<>();

        // Coda di priorità per scegliere i nodi con la distanza minima
        PriorityQueue<Cell> minHeap = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        minHeap.add(startCell);

        while (!minHeap.isEmpty()) {
            Cell current = minHeap.poll();
            int dist = distances.get(current);
            it.unical.demacs.ai.model.Player playerUpdate = new it.unical.demacs.ai.model.Player(new Coordinates(current.row, current.col), winDir, p.getColor(), p.getPath());

            if (winDir == Settings.Directions.UP && current.row == 0) {
                return dist;
            } else if (winDir == Settings.Directions.DOWN && current.row == rows - 1) {
                return dist;
            } else if (winDir == Settings.Directions.LEFT && current.col == 0) {
                return dist;
            } else if (winDir == Settings.Directions.RIGHT && current.col == cols - 1) {
                return dist;
            }

            // Segna il nodo attuale come visitato
            visited.add(current);

            // Scansiona i movimenti possibili dalla cella attuale
            for (int[] dir : directions) {
                int newRow = current.row + dir[0];
                int newCol = current.col + dir[1];
                Settings.Directions newDir;
                if (dir[0] == 1) {
                    newDir = Settings.Directions.DOWN;
                } else if (dir[0] == -1) {
                    newDir = Settings.Directions.UP;
                } else if (dir[1] == 1) {
                    newDir = Settings.Directions.RIGHT;
                } else {
                    newDir = Settings.Directions.LEFT;
                }
                Cell next = new Cell(newRow, newCol);

                // Controlla se il nodo successivo è già stato visitato
                if (!visited.contains(next) && newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
                        && Game.getInstance().canMove(playerUpdate, 0, 0, newDir)) {
                    int newDist = dist + 1;
                    if (!distances.containsKey(next) || newDist < distances.get(next)) {
                        distances.put(next, newDist);
                        minHeap.add(next);
                    }
                }
            }
        }
        // Se non è possibile raggiungere il bordo, restituisci -1
        return -1;
    }


    @Override
    public String getName() {
        return "Grissin Van Bon";
    }

}
