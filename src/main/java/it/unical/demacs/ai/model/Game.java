package it.unical.demacs.ai.model;

import it.unical.demacs.ai.model.Settings.Directions;
import it.unical.demacs.ai.model.Settings.Orientations;
import it.unical.demacs.ai.utils.Pair;

import java.util.*;

public class Game {

    private static Game game = null;                      // istanza di game
    private List<List<Orientations>> wallBoard;                 // matrice dei muri
    private List<List<Integer>> wallBoardPossession;            // matrice che indica chi ha piazzato i muri
    private List<Player> players;                               // lista dei giocatori
    private List<Integer> wallsAvailable;

    private Game(){
        players = new ArrayList<>();                            // inizializzo l'array dei giocatori
        wallBoard = new ArrayList<>();                          // inizializzo la matrice dei muri
        wallBoardPossession = new ArrayList<>();                // inizializzo la matrice che indica chi ha piazzato i muri
        wallsAvailable = new ArrayList<>();
        for(int i = 0; i < (Settings.boardDim-1); i++){
            List<Orientations> row = new ArrayList<>();
            List<Integer> row2 = new ArrayList<>();
            for(int j = 0; j < (Settings.boardDim-1); j++){
                row.add(Orientations.VOID);
                row2.add(-1);
            }
            wallBoard.add(row);
            wallBoardPossession.add(row2);
        }
    }
    
    /**
     * metodo per ottenere l'istanza singleton della partita
     * @return l'istanza singleton della partita
     */
    public static Game getInstance() {
        if (game == null)
            game = new Game();
        return game;
    }           // metodo singleton

    /**
     * metodo per calcolare se un giocatore si può muovere di un passo nella direzione dir
     * @param player il giocatore di cui si vuole controllare il movimento
     * @param dir la direzione verso cui si vuole muovere
     * @return true se il giocatore può muoversi, false altrimenti
     * @implNote richiama il metodo overload canMove, con offsetX = 0 e offsetY = 0
     */
    public boolean canMove(Player player, Directions dir){      // metodo che dice se il giocatore passato può fare una mossa nella direzione passata
        return canMove(player, 0, 0, dir);      // richiama il metodo overload, con offsetX = 0 e offsetY = 0
    }

    /**
     * metodo per calcolare se un giocatore si può muovere di un passo nella direzione dir
     * @param player il giocatore di cui si vuole controllare il movimento
     * @param offsetX spostamento lungo la x ddel giocatore, per simulare la sua posizione in una cella diversa da quella in cui si trova
     * @param offsetY spostamento lungo le y del giocatore, per simulare la sua posizione in una cella diversa da quella in cui si trova
     * @param dir la direzione verso cui si vuole muovere
     * @return true se il giocatore può muoversi, false altrimenti
     */
    public boolean canMove(Player player, int offsetX, int offsetY, Directions dir){                                                // metodo che inidca se posso effettuare una mossa di un giocatore, spostato di (offsetX, offsetY), nella direzione passata
        boolean cond1 = player.getCoord().first + offsetX + ((dir == Directions.RIGHT) ? 1 : 0) <= (Settings.boardDim-1);       // controllo che, il giocatore non va a destra oppure che esiste la posizione a destra (in termine di indici)
        boolean cond2 = player.getCoord().second + offsetY + ((dir == Directions.UP) ? -1 : 0) >= 0;        // controllo che, il giocatore non va su oppure che esiste la posizione su (in termine di indici)
        boolean cond3 = player.getCoord().first + offsetX + ((dir == Directions.LEFT) ? -1 : 0) >= 0;       // controllo che, il giocatore non va a sinistra oppure che esiste la posizione a sinistra (in termine di indici)
        boolean cond4 = player.getCoord().second + offsetY + ((dir == Directions.DOWN) ? 1 : 0) <= (Settings.boardDim-1);       // controllo che, il giocatore non va giù oppure che esiste la posizione giù (in termine di indici)
        // se sto andando a destra e mi trovo sul bordo superiore o inferiore della mappa posso evitare di controllare rispettivamente il muro sopra e sotto di me. negli altri casi controllo che non ci sia un muro verticale che mi blocca
        boolean cond5 = dir == Directions.RIGHT && (player.getCoord().second + offsetY > (Settings.boardDim-2) || wallBoard.get(player.getCoord().first + offsetX).get(player.getCoord().second + offsetY) != Orientations.VERTICAL) && (player.getCoord().second + offsetY < 1 || wallBoard.get(player.getCoord().first + offsetX).get(player.getCoord().second + offsetY-1) != Orientations.VERTICAL);
        // se sto andando a sinistra e mi trovo sul bordo superiore o inferiore della mappa posso evitare di controllare rispettivamente il muro sopra e sotto di me. negli altri casi controllo che non ci sia un muro verticale che mi blocca
        boolean cond6 = dir == Directions.LEFT && (player.getCoord().second + offsetY > (Settings.boardDim-2) || wallBoard.get(player.getCoord().first + offsetX-1).get(player.getCoord().second + offsetY) != Orientations.VERTICAL) && (player.getCoord().second + offsetY < 1 || wallBoard.get(player.getCoord().first + offsetX-1).get(player.getCoord().second + offsetY-1) != Orientations.VERTICAL);
        // se sto andando su e mi trovo sul bordo destro o sinistro della mappa posso evitare di controllare rispettivamente il muro sopra e sotto di me. negli altri casi controllo che non ci sia un muro orizzontale che mi blocca
        boolean cond7 = dir == Directions.UP && (player.getCoord().first + offsetX > (Settings.boardDim-2) || wallBoard.get(player.getCoord().first + offsetX).get(player.getCoord().second + offsetY-1) != Orientations.HORIZONTAL) && (player.getCoord().first + offsetX < 1 || wallBoard.get(player.getCoord().first + offsetX-1).get(player.getCoord().second + offsetY-1) != Orientations.HORIZONTAL);
        // se sto andando giu e mi trovo sul bordo destro o sinistro della mappa posso evitare di controllare rispettivamente il muro sopra e sotto di me. negli altri casi controllo che non ci sia un muro orizzontale che mi blocca
        boolean cond8 = dir == Directions.DOWN && (player.getCoord().first + offsetX > (Settings.boardDim-2) || wallBoard.get(player.getCoord().first + offsetX).get(player.getCoord().second + offsetY) != Orientations.HORIZONTAL) && (player.getCoord().first + offsetX < 1 || wallBoard.get(player.getCoord().first + offsetX-1).get(player.getCoord().second + offsetY) != Orientations.HORIZONTAL);
        return cond1 && cond2 && cond3 && cond4 && (cond5 || cond6 || cond7 || cond8);
    }

    /**
     * metodo per coontrollare che un muro si possa aggiungere, in una determinata posizione e con un determinato orientamento
     * @param pos posizione del muro da aggiungere
     * @param orientation orientamento del muro da aggiungere
     * @return true se il muro si può aggiungere, false altrimenti
     */
    public boolean canPlaceWall(Pair<Integer, Integer> pos, Orientations orientation){
        // controllo che le coordinate in cui voglio piazzare il muro siano valide e non sia stato ancora piazzato un muro
        if(pos.first >= 0 && pos.first < (Settings.boardDim-1) && pos.second >= 0 && pos.second < (Settings.boardDim-1) && wallBoard.get(pos.first).get(pos.second) == Orientations.VOID){
            // logicamente se voglio piazzare un muro in 0,0 non devo controllare se sopra di me ci sono altri muri.
            // in caso contrario, controllo che non ci siano muri che si intersecano. successivamente avrò bisogno di piazzare
            // temporaneamente il muro per capire se questo chiude qualche giocatore.
            if((pos.first-1 < 0 || wallBoard.get(pos.first-1).get(pos.second) != Orientations.HORIZONTAL || orientation != Orientations.HORIZONTAL) && (pos.first+1 > (Settings.boardDim-2) || wallBoard.get(pos.first+1).get(pos.second) != Orientations.HORIZONTAL || orientation != Orientations.HORIZONTAL) && (pos.second-1 < 0 || wallBoard.get(pos.first).get(pos.second-1) != Orientations.VERTICAL || orientation != Orientations.VERTICAL) && (pos.second+1 > (Settings.boardDim-2) || wallBoard.get(pos.first).get(pos.second+1) != Orientations.VERTICAL || orientation != Orientations.VERTICAL)){
                game.placeWall(pos, orientation);
                boolean ret = canGoPlayers();
                game.placeWall(pos, Orientations.VOID);
                return ret;
            }
        }
        return false;
    }

    /**
     * metodo per controllare che un muro si possa aggiungere, in una determinata posizione e con un determinato orientamento e con un determinato giocatore
     * @param pos posizione del muro da aggiungere
     * @param orientation orientamento del muro da aggiungere
     * @param player giocatore che vuole piazzare il muro
     * @return true se il muro si può aggiungere, false altrimenti
     */
    public boolean canPlaceWall(Pair<Integer, Integer> pos, Orientations orientation, Player player) {
        // controllo che il giocatore abbia ancora muri disponibili
        if(wallsAvailable.get(players.indexOf(player)) < 1){
            return false;
        }
        return canPlaceWall(pos, orientation);
    }

    /**
     * Metodo che calcola se i giocatori possono raggiungere la loro destinazione
     * @return true se tutti i giocatori possono raggiungere la loro destinazione, false altrimenti
     */
    public boolean canGoPlayers(){
        boolean canGo = true;
        for(Player player : players) {
            List<List<Integer>> canBeReached = new ArrayList<>();
            for (int i = 0; i < Settings.boardDim; i++){
                List<Integer> row = new ArrayList<>();
                for (int j = 0; j < Settings.boardDim; j++)
                    row.add(0);
                canBeReached.add(row);
            }
            canGo = canGo && canGoPlayersImpl(player, 0, 0, canBeReached);
        }
        return canGo;
    }

    /**
     * metodo che calcola se un giocatore può raggiungere una posizione vincente
     * @param player giocatore di cui si vuole calcolare se può vincere
     * @param offsetX spostamento lungo la x ddel giocatore, per simulare la sua posizione in una cella diversa da quella in cui si trova
     * @param offsetY spostamento lungo le y del giocatore, per simulare la sua posizione in una cella diversa da quella in cui si trova
     * @param canBeReached matrice che contiene 1 se è una posizione vincente, false altrimenti
     * @return true se il giocatore passato può raggiungere la destinazione, false altrimenti
     */
    private boolean canGoPlayersImpl(Player player, int offsetX, int offsetY, List<List<Integer>> canBeReached){
        if(winPosition(player, offsetX, offsetY)) {
            canBeReached.get(player.getCoord().first + offsetX).set(player.getCoord().second + offsetY, 1);
            return true;
        } else if (player.getCoord().first + offsetX < 0 || player.getCoord().first + offsetX > (Settings.boardDim-1) || player.getCoord().second + offsetY < 0 || player.getCoord().second + offsetY > (Settings.boardDim-1)){
            return false;
        } else if (canBeReached.get(player.getCoord().first + offsetX).get(player.getCoord().second + offsetY) != 0) {
            return canBeReached.get(player.getCoord().first + offsetX).get(player.getCoord().second + offsetY) == 1;
        } else {
            canBeReached.get(player.getCoord().first + offsetX).set(player.getCoord().second + offsetY, -1);
            if(canGoPlayersImpl(player, offsetX+1, offsetY, canBeReached) && canMove(player, offsetX, offsetY, Directions.RIGHT)){
                canBeReached.get(player.getCoord().first + offsetX).set(player.getCoord().second + offsetY, 1);
                return true;
            } else if (canGoPlayersImpl(player, offsetX-1, offsetY, canBeReached) && canMove(player, offsetX, offsetY, Directions.LEFT)){
                canBeReached.get(player.getCoord().first + offsetX).set(player.getCoord().second + offsetY, 1);
                return true;
            } else if (canGoPlayersImpl(player, offsetX, offsetY+1, canBeReached) && canMove(player, offsetX, offsetY, Directions.DOWN)){
                canBeReached.get(player.getCoord().first + offsetX).set(player.getCoord().second + offsetY, 1);
                return true;
            } else if (canGoPlayersImpl(player, offsetX, offsetY-1, canBeReached) && canMove(player, offsetX, offsetY, Directions.UP)){
                canBeReached.get(player.getCoord().first + offsetX).set(player.getCoord().second + offsetY, 1);
                return true;
            } else {
                canBeReached.get(player.getCoord().first + offsetX).set(player.getCoord().second + offsetY, 2);
                return false;
            }
        }
    }

    /**
     * metodo per calcolare se un giocatore si trova su una posizione vincente
     * @param player giocatore di cui si vuole conoscere la posizione
     * @param offsetX spostamento lungo la x di un giocatore, per simulare la sua posizione in una cella diversa da quella in cui si trova
     * @param offsetY spostamento lungo la y di un giocatore, per simulare la sua posizione in una cella diversa da quella in cui si trova
     * @return true se il giocatore si trova su una posizione vincente, false altrimenti
     */
    public boolean winPosition(Player player, int offsetX, int offsetY){
        if(player.getDirection() == Directions.RIGHT && player.getCoord().first + offsetX == (Settings.boardDim-1))
            return true;
        if(player.getDirection() == Directions.LEFT && player.getCoord().first + offsetX == 0)
            return true;
        if(player.getDirection() == Directions.UP && player.getCoord().second + offsetY == 0)
            return true;
        if(player.getDirection() == Directions.DOWN && player.getCoord().second + offsetY == (Settings.boardDim-1))
            return true;
        return false;
    }

    /**
     * metodo per aggiungere un giocatore alla partita
     * @param player giocatore da aggiungere
     */
    public void addPlayer(Player player){
        players.add(player);
    }

    /**
     * metodo per aggiungere un muro alla partita senza proprietario
     * @param pos un pair che indica le coordinate del muro da aggiungere
     * @param orientation in che direzione il muro è orientato
     * @implNote attenzione! non controlla che il muro sia posizionabile, lo aggiunge e basta.
     * @implNote se nella stessa posizione è già presente un muro, lo sovrascrive
     */
    public void placeWall(Pair<Integer, Integer> pos, Orientations orientation){
        wallBoard.get(pos.first).set(pos.second, orientation);
    }

    /**
     * metodo per aggiungere un muro alla partita con proprietario
     * @param pos un pair che indica le coordinate del muro da aggiungere
     * @param orientation in che direzione il muro è orientato
     * @param player il giocatore che ha piazzato il muro
     * @implNote attenzione! non controlla che il muro sia posizionabile, lo aggiunge e basta.
     * @implNote se nella stessa posizione è già presente un muro, lo sovrascrive
     */
    public void placeWall(Pair<Integer, Integer> pos, Orientations orientation, Player player){
        wallBoard.get(pos.first).set(pos.second, orientation);
        wallBoardPossession.get(pos.first).set(pos.second, players.indexOf(player));
        wallsAvailable.set(players.indexOf(player), wallsAvailable.get(players.indexOf(player))-1);
    }

    /**
     * metodo per ottenere i giocatori della partita
     * @return la lista dei giocatori della partita
     */
    public List<Player> getPlayers(){
        return players;
    }

    /**
     * metodo per ottenere la matrice dei muri della partita
     * @return la matrice dei muri della partita
     */
    public List<List<Orientations>> getWallBoard(){
        return wallBoard;
    }

    /**
     * metodo per ottenere il numero dei muri disponibili per ogni player
     * @return la lista del numero dei muri disponibili
     */
    public void setWallsAvailable(List<Integer> wallsAvailable) {
        this.wallsAvailable = wallsAvailable;
    }
}