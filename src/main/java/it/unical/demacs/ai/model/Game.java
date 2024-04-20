package it.unical.demacs.ai.model;

import it.unical.demacs.ai.model.Settings.Directions;
import it.unical.demacs.ai.model.Settings.Orientations;
import it.unical.demacs.ai.utils.Coordinates;

import java.util.*;

public class Game {

    private static Game game = null;                      // istanza di game
    private List<List<Wall>> wallBoard;                 // matrice dei muri
    private List<Player> players;                               // lista dei giocatori
    private int currentActivePlayer;

    private Game(){
        currentActivePlayer = -1;
        players = new ArrayList<>();                            // inizializzo l'array dei giocatori
        wallBoard = new ArrayList<>();                          // inizializzo la matrice dei muri
        for(int rowIndex = 0; rowIndex < (Settings.boardDim-1); rowIndex++){
            List<Wall> row = new ArrayList<>();
            for(int columnIndex = 0; columnIndex < (Settings.boardDim-1); columnIndex++){
                row.add(new Wall());
            }
            wallBoard.add(row);
        }
    }

    /**
     * metodo per scegliere un giocatore casuale
     * @implNote sceglie un giocatore casuale tra quelli presenti nella partita e lo setta come giocatore corrente
     */
    public void chooseRandomPlayer()
    {
        Random random = new Random();
        currentActivePlayer = random.nextInt(0, players.size());
    }

    /**
     * metodo per ottenere il giocatore corrente
     * @return il giocatore corrente
     */
    public Player getCurrentPlayer() {
        return players.get(currentActivePlayer);
    }

    /**
     * metodo per cambiare turno della partita
     */
    public void nextTurn(){
        currentActivePlayer = (currentActivePlayer + 1) % players.size();
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
     * metodo per calcolare se il giocatore attivo si può muovere di un passo nella direzione dir
     * @param dir la direzione verso cui si vuole muovere
     * @return true se il giocatore può muoversi, false altrimenti
     * @implNote richiama il metodo overload canMove, con offsetColumn = 0 e offsetRow = 0
     */
     public boolean canMove(Directions dir){     // metodo che dice se il giocatore passato può fare una mossa nella direzione passata
        return canMove(players.get(currentActivePlayer), 0, 0, dir);      // richiama il metodo overload, con offsetColumn = 0 e offsetRow = 0
    }

    /**
     * metodo per calcolare se la posizione passata è valida
     * @param pos la posizione da controllare
     * @param isWall true se si vuole controllare se la posizione è valida per un muro, false altrimenti
     * @return true se la posizione è valida, false altrimenti
     */
    public boolean validPosition(Coordinates pos, boolean isWall){
        if (isWall){
            return pos.column >= 0 && pos.column < (Settings.boardDim-1) && pos.row >= 0 && pos.row < (Settings.boardDim-1);
        } else {
            return pos.column >= 0 && pos.column < Settings.boardDim && pos.row >= 0 && pos.row < Settings.boardDim;
        }
    }

    /**
     * metodo per calcolare se un giocatore si può muovere di un passo nella direzione dir
     * @param player il giocatore di cui si vuole controllare il movimento
     * @param offsetColumn spostamento lungo la x ddel giocatore, per simulare la sua posizione in una cella diversa da quella in cui si trova
     * @param offsetRow spostamento lungo le y del giocatore, per simulare la sua posizione in una cella diversa da quella in cui si trova
     * @param dir la direzione verso cui si vuole muovere
     * @return true se il giocatore può muoversi, false altrimenti
     */
    public boolean canMove(Player player, int offsetRow, int offsetColumn, Directions dir){                                               // metodo che inidca se posso effettuare una mossa di un giocatore, spostato di (offsetColumn, offsetRow), nella direzione passata
        if(validPosition(new Coordinates(player.getCoord().row + offsetRow + ((dir == Directions.UP) ? -1 : ((dir == Directions.DOWN) ? 1 : 0)), player.getCoord().column + offsetColumn + ((dir == Directions.LEFT) ? -1 : (dir == Directions.RIGHT) ? 1 : 0)), false)){
            // se sto andando a destra e mi trovo sul bordo superiore o inferiore della mappa posso evitare di controllare rispettivamente il muro sopra e sotto di me. negli altri casi controllo che non ci sia un muro verticale che mi blocca
            boolean cond5 = dir == Directions.RIGHT && (player.getCoord().row + offsetRow > (Settings.boardDim-2) || wallBoard.get(player.getCoord().row + offsetRow).get(player.getCoord().column + offsetColumn).getOrientation() != Orientations.VERTICAL) && (player.getCoord().row + offsetRow < 1 || wallBoard.get(player.getCoord().row + offsetRow-1).get(player.getCoord().column + offsetColumn).getOrientation() != Orientations.VERTICAL);
            // se sto andando a sinistra e mi trovo sul bordo superiore o inferiore della mappa posso evitare di controllare rispettivamente il muro sopra e sotto di me. negli altri casi controllo che non ci sia un muro verticale che mi blocca
            boolean cond6 = dir == Directions.LEFT && (player.getCoord().row + offsetRow > (Settings.boardDim-2) || wallBoard.get(player.getCoord().row + offsetRow).get(player.getCoord().column + offsetColumn-1).getOrientation() != Orientations.VERTICAL) && (player.getCoord().row + offsetRow < 1 || wallBoard.get(player.getCoord().row + offsetRow-1).get(player.getCoord().column + offsetColumn-1).getOrientation() != Orientations.VERTICAL);
            // se sto andando su e mi trovo sul bordo destro o sinistro della mappa posso evitare di controllare rispettivamente il muro sopra e sotto di me. negli altri casi controllo che non ci sia un muro orizzontale che mi blocca
            boolean cond7 = dir == Directions.UP && (player.getCoord().column + offsetColumn > (Settings.boardDim-2) || wallBoard.get(player.getCoord().row + offsetRow-1).get(player.getCoord().column + offsetColumn).getOrientation() != Orientations.HORIZONTAL) && (player.getCoord().column + offsetColumn < 1 || wallBoard.get(player.getCoord().row + offsetRow-1).get(player.getCoord().column + offsetColumn-1).getOrientation() != Orientations.HORIZONTAL);
            // se sto andando giu e mi trovo sul bordo destro o sinistro della mappa posso evitare di controllare rispettivamente il muro sopra e sotto di me. negli altri casi controllo che non ci sia un muro orizzontale che mi blocca
            boolean cond8 = dir == Directions.DOWN && (player.getCoord().column + offsetColumn > (Settings.boardDim-2) || wallBoard.get(player.getCoord().row + offsetRow).get(player.getCoord().column + offsetColumn).getOrientation() != Orientations.HORIZONTAL) && (player.getCoord().column + offsetColumn < 1 || wallBoard.get(player.getCoord().row + offsetRow).get(player.getCoord().column + offsetColumn-1).getOrientation() != Orientations.HORIZONTAL);
            return (cond5 || cond6 || cond7 || cond8);
        }
        return false;
    }


    /**
     * metodo per muovere il giocatore attivo nella direzione dir
     * @param dir la direzione verso cui si vuole muovere
     */
    public void move(Directions dir){
        Player player = getCurrentPlayer();
        if (dir == Directions.RIGHT) {
            player.setCoord(new Coordinates(player.getCoord().row, player.getCoord().column + 1));
        } else if (dir == Directions.LEFT) {
            player.setCoord(new Coordinates(player.getCoord().row, player.getCoord().column - 1));
        } else if (dir == Directions.UP) {
            player.setCoord(new Coordinates(player.getCoord().row - 1, player.getCoord().column));
        } else if (dir == Directions.DOWN) {
            player.setCoord(new Coordinates(player.getCoord().row + 1, player.getCoord().column));
        }
    }

    /**
     * metodo per coontrollare che un muro si possa aggiungere, in una determinata posizione e con un determinato orientamento
     * @param pos posizione del muro da aggiungere
     * @param orientation orientamento del muro da aggiungere
     * @return true se il muro si può aggiungere, false altrimenti
     */
    public boolean canPlaceWall(Coordinates pos, Orientations orientation, Player player){
        // controllo che il giocatore abbia ancora muri disponibili
        if(player.getWallsAvailable() < 1){
            return false;
        }
        // controllo che le coordinate in cui voglio piazzare il muro siano valide e non sia stato ancora piazzato un muro
        if(validPosition(pos, true) && wallBoard.get(pos.row).get(pos.column).getOrientation() == Orientations.VOID){
            // logicamente se voglio piazzare un muro in 0,0 non devo controllare se sopra di me ci sono altri muri.
            // in caso contrario, controllo che non ci siano muri che si intersecano. successivamente avrò bisogno di piazzare
            // temporaneamente il muro per capire se questo chiude qualche giocatore.
            for(int i = -1;i < 2; i++){
                for(int j = -1; j < 2; j++){
                    if ((i == 0 || j == 0) && (i != 0 || j != 0)){
                        if(validPosition(new Coordinates(pos.row + i, pos.column + j), true) && wallBoard.get(pos.row + i).get(pos.column + j).getOrientation() == orientation){
                            return false;
                        }
                    }
                }
            }
            game.placeWall(pos, orientation, player);
            boolean ret = canGoPlayers();
            game.placeWall(pos, Orientations.VOID, player);
            player.setWallsAvailable(player.getWallsAvailable()+1);
            return ret;
        }
        return false;
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
            for (int i = 0; i < Settings.boardDim; i++){
                for (int j = 0; j < Settings.boardDim; j++){
                    System.out.print(canBeReached.get(i).get(j) + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        return canGo;
    }

    /**
     * metodo che calcola se un giocatore può raggiungere una posizione vincente
     * @param player giocatore di cui si vuole calcolare se può vincere
     * @param offsetColumn spostamento lungo la x ddel giocatore, per simulare la sua posizione in una cella diversa da quella in cui si trova
     * @param offsetRow spostamento lungo le y del giocatore, per simulare la sua posizione in una cella diversa da quella in cui si trova
     * @param canBeReached matrice che contiene 1 se è una posizione vincente, false altrimenti
     * @return true se il giocatore passato può raggiungere la destinazione, false altrimenti
     */
    private boolean canGoPlayersImpl(Player player, int offsetRow, int offsetColumn, List<List<Integer>> canBeReached){
        if (! validPosition(new Coordinates(player.getCoord().row + offsetRow, player.getCoord().column + offsetColumn), false)){
            return false;
        } else if(winPosition(player, offsetRow, offsetColumn)) {
            canBeReached.get(player.getCoord().row + offsetRow).set(player.getCoord().column + offsetColumn, 1);
            return true;
        }  else if (canBeReached.get(player.getCoord().row + offsetRow).get(player.getCoord().column + offsetColumn) != 0) {
            return canBeReached.get(player.getCoord().row + offsetRow).get(player.getCoord().column + offsetColumn) > 0;
        } else {
            canBeReached.get(player.getCoord().row + offsetRow).set(player.getCoord().column + offsetColumn, - 1);
            List<Integer> possible = new ArrayList<>();
            if(canGoPlayersImpl(player, offsetRow+1, offsetColumn, canBeReached) && canMove(player, offsetRow, offsetColumn, Directions.DOWN)){
                possible.add(canBeReached.get(player.getCoord().row + offsetRow + 1).get(player.getCoord().column + offsetColumn));
            }
            if (canGoPlayersImpl(player, offsetRow-1, offsetColumn, canBeReached) && canMove(player, offsetRow, offsetColumn, Directions.UP)){
                possible.add(canBeReached.get(player.getCoord().row + offsetRow - 1).get(player.getCoord().column + offsetColumn));
            }
            if (canGoPlayersImpl(player, offsetRow, offsetColumn+1, canBeReached) && canMove(player, offsetRow, offsetColumn, Directions.RIGHT)){
                possible.add(canBeReached.get(player.getCoord().row + offsetRow).get(player.getCoord().column + offsetColumn + 1));
            }
            if (canGoPlayersImpl(player, offsetRow, offsetColumn-1, canBeReached) && canMove(player, offsetRow, offsetColumn, Directions.LEFT)){
                possible.add(canBeReached.get(player.getCoord().row + offsetRow).get(player.getCoord().column + offsetColumn - 1));
            }
            if(possible.size() > 0){
                canBeReached.get(player.getCoord().row + offsetRow).set(player.getCoord().column + offsetColumn, Collections.min(possible) + 1);
                return true;
            } else {
                canBeReached.get(player.getCoord().row + offsetRow).set(player.getCoord().column + offsetColumn, -1);
                return false;
            }
        }
    }

    /**
     * metodo per calcolare se un giocatore si trova su una posizione vincente
     * @param player giocatore di cui si vuole conoscere la posizione
     * @param offsetColumn spostamento lungo la x di un giocatore, per simulare la sua posizione in una cella diversa da quella in cui si trova
     * @param offsetRow spostamento lungo la y di un giocatore, per simulare la sua posizione in una cella diversa da quella in cui si trova
     * @return true se il giocatore si trova su una posizione vincente, false altrimenti
     */
    public boolean winPosition(Player player, int offsetRow, int offsetColumn){
        if(player.getDirection() == Directions.RIGHT && player.getCoord().column + offsetColumn == (Settings.boardDim-1))
            return true;
        if(player.getDirection() == Directions.LEFT && player.getCoord().column + offsetColumn == 0)
            return true;
        if(player.getDirection() == Directions.UP && player.getCoord().row + offsetRow == 0)
            return true;
        if(player.getDirection() == Directions.DOWN && player.getCoord().row + offsetRow == (Settings.boardDim-1))
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
     * metodo per aggiungere un muro alla partita con proprietario
     * @param pos un pair che indica le coordinate del muro da aggiungere
     * @param orientation in che direzione il muro è orientato
     * @param player il giocatore che ha piazzato il muro
     * @implNote attenzione! non controlla che il muro sia posizionabile, lo aggiunge e basta.
     * @implNote se nella stessa posizione è già presente un muro, lo sovrascrive
     */
    public void placeWall(Coordinates pos, Orientations orientation, Player player){
        if (orientation == Orientations.VOID) {
            wallBoard.get(pos.row).set(pos.column, new Wall());
            return;
        }
        wallBoard.get(pos.row).set(pos.column, new Wall(orientation, player));
        player.setWallsAvailable(player.getWallsAvailable()-1);
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
    public List<List<Wall>> getWallBoard(){
        return wallBoard;
    }

    /**
     * metodo per ottenere il numero dei muri disponibili per ogni player
     * @return la lista del numero dei muri disponibili
     */
    public void setWallsAvailable(Map<Directions, Integer> wallsAvailable) {
        for(Player player : players){
            player.setWallsAvailable(wallsAvailable.get(player.getDirection()));
        }
    }

    public Map<Directions, Integer> getWallsAvailable() {
        Map<Directions, Integer> ret = new HashMap<>();
        for(Player player : players){
            ret.put(player.getDirection(), player.getWallsAvailable());
        }
        return ret;
    }

}