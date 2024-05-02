package it.unical.demacs.ai.model.ai.inputAgent;

import it.unical.demacs.ai.model.Game;
import it.unical.demacs.ai.model.Player;
import it.unical.demacs.ai.model.Settings;
import it.unical.demacs.ai.model.ai.Agent;
import it.unical.demacs.ai.utils.Coordinates;

import java.util.Scanner;

public class InputAgent implements Agent {

    private Player myPlayer;

    public InputAgent(Player player)
    {
        myPlayer = player;
    }


    @Override
    public void act() {

        Scanner scanner = new Scanner(System.in);
        String rawInput = scanner.next();

        String[] input = rawInput.split(",");

        if (input[input.length - 1].equals("h"))
        {
            Coordinates coord = new Coordinates(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
            if (Game.getInstance().canPlaceWall(coord, Settings.Orientations.HORIZONTAL, myPlayer))
                Game.getInstance().placeWall(coord, Settings.Orientations.HORIZONTAL, myPlayer);
            else
                System.out.println("NON PUOI PIAZZARE IL MURO ORIZZONTALE");
        } else if (input[input.length - 1].equals("v")) {
            Coordinates coord = new Coordinates(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
            if (Game.getInstance().canPlaceWall(coord, Settings.Orientations.VERTICAL, myPlayer))
                Game.getInstance().placeWall(coord, Settings.Orientations.VERTICAL, myPlayer);
            else
                System.out.println("NON PUOI PIAZZARE IL MURO VERTICALE");
        }
        else
        {
            switch (input[0])
            {
                case "up" ->
                {
                    if (Game.getInstance().canMove(myPlayer, 0, 0, Settings.Directions.UP))
                        Game.getInstance().move(Settings.Directions.UP);
                }
                case "down" ->
                {
                    if (Game.getInstance().canMove(myPlayer, 0, 0, Settings.Directions.DOWN))
                        Game.getInstance().move(Settings.Directions.DOWN);
                }
                case "left" ->
                {
                    if (Game.getInstance().canMove(myPlayer, 0, 0, Settings.Directions.LEFT))
                        Game.getInstance().move(Settings.Directions.LEFT);
                }
                case "right" ->
                {
                    if (Game.getInstance().canMove(myPlayer, 0, 0, Settings.Directions.RIGHT))
                        Game.getInstance().move(Settings.Directions.RIGHT);
                }
            }
        }


    }

    @Override
    public String getName() {
        return "PALO";
    }
}
