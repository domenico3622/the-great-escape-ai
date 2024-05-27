package it.unical.demacs.ai.model.ai.JYPapi;

import it.unical.demacs.ai.model.ai.Agent;

import it.unical.demacs.ai.model.Game;
import it.unical.demacs.ai.model.Player;
import it.unical.demacs.ai.model.Wall;
import it.unical.demacs.ai.utils.Coordinates;
import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Agent4 implements Agent{

    Mapper mapper = Mapper.getInstance();
    private Game game = Game.getInstance();

    private static String encodingSource="src/main/java/it/unical/demacs/ai/model/ai/JYPapi/encoding4";
    private static Handler handler;

    private List<JYPlayer> JYPlayers = new ArrayList<>();
    private List<OurWall> ourWalls = new ArrayList<>();

    private NextPos nextPos=null;
    private NewWall newWall=null;

    private Player myPlayer;

    public Agent4(Player myPlayer){this.myPlayer=myPlayer;}




    private void setWallList() // setta la lista dei muri newWall
    {
        ourWalls.clear();
        int size = game.getWallBoard().size();
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size; j++)
            {
                Wall wall = game.getWallBoard().get(i).get(j);
                if (wall.getOwner() != null)
                {
                    OurWall ourWall = mapper.mapWallToOurWall(wall, i, j);
                    ourWalls.add(ourWall);
                }
            }
        }
    }


    private void setPlayerList() // setta la lista dei giocatori JYPlayer
    {
        JYPlayers.clear();


        for(int i = 0; i < game.getPlayers().size(); i++)
        {
            Player player = game.getPlayers().get(i);
            JYPlayer newPlayer = mapper.mapPlayerToASPPlayer(player, i);
            JYPlayers.add(newPlayer);
        }
    }


    @Override
    public void act() {

        handler= new DesktopHandler(new DLV2DesktopService("lib/dlv2.exe"));


        //call alle funzioni per settare le nostre liste di gioco:
        setPlayerList();
        setWallList();

        try{
            ASPMapper.getInstance().registerClass(NextPos.class);
            ASPMapper.getInstance().registerClass(NewWall.class);
            ASPMapper.getInstance().registerClass(MyId.class);
            ASPMapper.getInstance().registerClass(JYPlayer.class);
            ASPMapper.getInstance().registerClass(OurWall.class);
            ASPMapper.getInstance().registerClass(RandomNumb.class);
        } catch (ObjectNotValidException | IllegalAnnotationException e) {
            throw new RuntimeException(e);
        }
        InputProgram facts = new ASPInputProgram();

        for(JYPlayer player: JYPlayers)
        {
            try {
                System.out.println(player.getId());
                facts.addObjectInput(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Random randomizer = new Random();


        try{
            RandomNumb randomNum= new RandomNumb();

            if(randomizer.nextInt(0,7)%2==0){
                randomNum.setRandomNum(1);
            }else {
                randomNum.setRandomNum(0);
            }

            System.out.println(randomNum.getRandomNum()+ " random number");
            facts.addObjectInput(randomNum);
        }catch (Exception e) {
            e.printStackTrace();
        }

       for( OurWall wall: ourWalls)
        {
            try {
                facts.addObjectInput(wall);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       for(int i=0; i<game.getPlayers().size(); i++){
            if(game.getPlayers().get(i)==myPlayer){
                try {
                    MyId myId = new MyId(i);
                    facts.addObjectInput(myId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        handler.addProgram(facts);

        InputProgram encoding= new ASPInputProgram();
        encoding.addFilesPath(encodingSource);
        handler.addProgram(encoding);

        Output o = handler.startSync();
        AnswerSets answerSet = (AnswerSets) o;
        for(AnswerSet a: answerSet.getOptimalAnswerSets()) {
            try {
                for (Object obj : a.getAtoms()) {
                    if(!(obj instanceof NextPos | obj instanceof NewWall)) continue;
                    if(obj instanceof NextPos) {
                        nextPos = (NextPos) obj;
                    } else if (obj instanceof NewWall) {
                        newWall = (NewWall) obj;
                    }
                }
            } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException |
                     InstantiationException e) {
                throw new RuntimeException(e);
            }
            System.out.println("NextPos: " + nextPos);
            System.out.println("NewWall: " + newWall);
            if(nextPos!=null){
                Coordinates coord= new Coordinates(nextPos.getNewRow(), nextPos.getNewCol());
                myPlayer.setCoord(coord);
            }
            if(newWall!=null){
                Wall wall= mapper.mapNewWallToWall(newWall);
                System.out.println(wall.getOrientation());
                Coordinates coord= new Coordinates(newWall.getRowWall(), newWall.getColWall());
                game.placeWall(coord, wall.getOrientation(), myPlayer);
            }
            //non so che vogliono ritornati
            //una volta che ho le nuove posizioni e i nuovi muri, devo aggiornare la board nel gioco principale
        }
    }

    @Override
    public String getName() {
        return "JYPapi";
    }

}
