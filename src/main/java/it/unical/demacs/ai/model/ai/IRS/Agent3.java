package it.unical.demacs.ai.model.ai.IRS;
import it.unical.demacs.ai.model.Game;
import it.unical.demacs.ai.model.Player;
import it.unical.demacs.ai.model.Settings;
import it.unical.demacs.ai.model.Wall;
import it.unical.demacs.ai.model.ai.Agent;
import it.unical.demacs.ai.model.ai.IRS.atoms.*;
import it.unical.demacs.ai.utils.Coordinates;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.OptionDescriptor;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.*;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import java.util.List;


public class Agent3 implements Agent{

    private static MovePlayer movePlayer;
    private static PlaceWall placeWall;
    private static OverWall overWall;
    private Player myPlayer;
    private static String encodingResource="src/main/java/it/unical/demacs/ai/model/ai/IRS/greatEscape.txt";

    private static Handler handler;


    @Override
    public void act() {
        Game game=Game.getInstance();
        List<Player> playerList=game.getPlayers();
        overWall=null;
        placeWall=null;
        movePlayer=null;
        handler = new DesktopHandler(new DLV2DesktopService(Settings.executablePath("dlv2")));
        try {
            ASPMapper.getInstance().registerClass(PlayerAtom.class);
            ASPMapper.getInstance().registerClass(WallAtom.class);
            ASPMapper.getInstance().registerClass(OverWall.class);
            ASPMapper.getInstance().registerClass(NextPlayer.class);
            ASPMapper.getInstance().registerClass(MovePlayer.class);
            ASPMapper.getInstance().registerClass(PlaceWall.class);
            ASPMapper.getInstance().registerClass(MinPath.class);
            ASPMapper.getInstance().registerClass(Direction.class);
            ASPMapper.getInstance().registerClass(Dimension.class);
        } catch (ObjectNotValidException | IllegalAnnotationException e1) {
            e1.printStackTrace();
        }
        InputProgram facts= new ASPInputProgram();
        try {
            facts.addObjectInput(new Direction(new SymbolicConstant(myPlayer.getDirection().toString().toLowerCase())));
            int count=0;
            boolean next=false;
            for(Player player: playerList){
                if(next==true){
                    facts.addObjectInput(new NextPlayer(new SymbolicConstant(player.getDirection().toString().toLowerCase())));
                    next=false;
                }
                facts.addObjectInput(new PlayerAtom(count, player.getCoord().row, player.getCoord().column, new SymbolicConstant(player.getDirection().toString().toLowerCase()), player.getWallsAvailable()));
                count++;
                if(player.equals(myPlayer)){
                    next=true;
                }
            }
            if(next==true){
                facts.addObjectInput(new NextPlayer(new SymbolicConstant(playerList.get(0).getDirection().toString().toLowerCase())));
            }
            List<List<Wall>> walls=game.getWallBoard();
            for(int rowIndex = 0; rowIndex < (Settings.boardDim-1); rowIndex++){
                for(int columnIndex = 0; columnIndex < (Settings.boardDim-1); columnIndex++){
                    setWall(rowIndex, columnIndex, walls, facts);
                }
            }
            PathFinder pathFinder=new PathFinder();
            for(Player player: playerList){
                int distance=pathFinder.shortestPath(player);
                facts.addObjectInput(new MinPath(new SymbolicConstant(player.getDirection().toString().toLowerCase()), distance));
            }
            facts.addObjectInput(new Dimension(Settings.boardDim));
            for(int i=0; i<Settings.boardDim; i++){
                for(int j=0; j<Settings.boardDim; j++){
                    facts.addObjectInput(new Cell(i, j));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.addProgram(facts);
        InputProgram encoding= new ASPInputProgram();
        OptionDescriptor option=new OptionDescriptor("--printonlyoptimum");
        handler.addOption(option);
        encoding.addFilesPath(encodingResource);
        handler.addProgram(encoding);
        Output o =  handler.startSync();
        AnswerSets answersets = (AnswerSets) o;
        for(AnswerSet a:answersets.getAnswersets()){
            try {
                for(Object obj:a.getAtoms()){
                    if(obj instanceof MovePlayer) {
                        movePlayer = (MovePlayer) obj;
                        //System.out.println(movePlayer);
                        Game.getInstance().moveTo(new Coordinates(movePlayer.getX(), movePlayer.getY()));
                    }
                    else if(obj instanceof PlaceWall) {
                        placeWall = (PlaceWall) obj;
                        //System.out.println(placeWall);
                    }
                    else if(obj instanceof OverWall) {
                        overWall = (OverWall) obj;
                        //System.out.println(overWall);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Coordinates coord;
            if(movePlayer==null && overWall!=null){
                coord= new Coordinates(overWall.getX1(), overWall.getY1());
                if(overWall.isVertical()){
                    if (Game.getInstance().canPlaceWall(coord,Settings.Orientations.VERTICAL, myPlayer)){
                        Game.getInstance().placeWall(coord, Settings.Orientations.VERTICAL, myPlayer);
                    }
                }else {
                    if(Game.getInstance().canPlaceWall(coord, Settings.Orientations.HORIZONTAL, myPlayer)) {
                        Game.getInstance().placeWall(coord, Settings.Orientations.HORIZONTAL, myPlayer);
                    }
                }
            }else if(movePlayer==null){
                coord=new Coordinates(placeWall.getX1(), placeWall.getY1());
                if(placeWall.isVertical()){
                    if(Game.getInstance().canPlaceWall(coord, Settings.Orientations.VERTICAL, myPlayer)){
                        Game.getInstance().placeWall(coord, Settings.Orientations.VERTICAL, myPlayer);
                    }
                } else {
                    if(Game.getInstance().canPlaceWall(coord, Settings.Orientations.HORIZONTAL, myPlayer)){
                        Game.getInstance().placeWall(coord, Settings.Orientations.HORIZONTAL, myPlayer);
                    }
                }
            }
        }


    }

    public Agent3(Player player){
        myPlayer=player;
    }
    @Override
    public String getName() {
        return "IRS";
    }

    public void setWall(int rowIndex, int columnIndex, List<List<Wall>> walls, InputProgram facts) throws Exception {
        if(walls.get(rowIndex).get(columnIndex).getOrientation() == Settings.Orientations.VOID){
            return;
        }
        Wall wall=walls.get(rowIndex).get(columnIndex);
        String orientation=wall.getOrientation().toString().toLowerCase();
        if(orientation.equals("horizontal")){
            facts.addObjectInput(new WallAtom(rowIndex, columnIndex, rowIndex+1, columnIndex, rowIndex, columnIndex+1, rowIndex+1, columnIndex+1));
        } else {
            facts.addObjectInput(new WallAtom(rowIndex, columnIndex, rowIndex, columnIndex+1, rowIndex+1, columnIndex, rowIndex+1, columnIndex+1));
        }
    }

    public Player getPlayer(){
        return myPlayer;
    }

}
