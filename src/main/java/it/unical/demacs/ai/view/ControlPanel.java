package it.unical.demacs.ai.view;

import it.unical.demacs.ai.model.Game;
import it.unical.demacs.ai.model.Settings;
import it.unical.demacs.ai.utils.Coordinates;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private JButton bSopra;
    private JButton bSotto;
    private JButton bDestra;
    private JButton bSinistra;

    private JTextField fWallX;
    private JTextField fWallY;
    private JRadioButton rWallVert;
    private JRadioButton rWallOriz;
    private JButton bWallPlace;

    private JLabel lTurno;
    private JLabel lAnnunci;

    public ControlPanel(){
        super();
        setLayout(new GridBagLayout());

        bSopra = new JButton("↑");
        bSotto = new JButton("↓");
        bDestra = new JButton("→");
        bSinistra = new JButton("←");

        fWallX = new JTextField();
        fWallY = new JTextField();

        rWallVert = new JRadioButton("↕");
        rWallOriz = new JRadioButton("↔");

        ButtonGroup bg = new ButtonGroup();
        bg.add(rWallVert);
        bg.add(rWallOriz);

        bWallPlace = new JButton("Place Wall");

        lTurno = new JLabel("Turno");
        lAnnunci = new JLabel("Annuncio");

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        c.gridy = 0;
        c.gridwidth = 0;
        add(new JLabel("The Great Escape"), c);

        c.gridy=1;
        c.gridwidth = 3;
        add(new JSeparator(), c);

        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        add(new JLabel("Move"), c);

        c.gridy = 3;
        c.gridx = 1;
        c.gridwidth = 1;
        add(bSopra, c);

        c.gridy = 4;
        c.gridx = 0;
        add(bSinistra, c);

        c.gridx = 2;
        add(bDestra, c);

        c.gridy = 5;
        c.gridx = 1;
        add(bSotto, c);

        c.gridy = 6;
        c.gridx=0;
        c.gridwidth = 3;
        add(new JSeparator(), c);

        c.gridy = 7;
        c.gridx = 0;
        c.gridwidth = 3;
        add(new JLabel("Place Wall"), c);

        c.gridy = 8;
        c.gridwidth = 1;
        add(new JLabel("Row"), c);

        c.gridx = 1;
        add(fWallX, c);

        c.gridy = 9;
        c.gridx = 0;
        add(new JLabel("Column"), c);

        c.gridx = 1;
        add(fWallY, c);

        c.gridy = 10;
        c.gridx = 0;
        add(rWallVert, c);

        c.gridx = 1;
        add(rWallOriz, c);

        c.gridy = 11;
        c.gridwidth = 3;
        c.gridx = 0;
        add(bWallPlace, c);

        c.gridy = 12;
        c.gridx = 0;
        c.gridwidth = 3;
        add(new JSeparator(), c);

        c.gridy = 13;
        c.gridx = 0;
        c.gridwidth = 3;
        add(lTurno, c);

        c.gridy = 14;
        add(lAnnunci, c);

        bSopra.addActionListener(e -> {
            if (Game.getInstance().canMove(Settings.Directions.UP)) {
                Game.getInstance().move(Settings.Directions.UP);
            } else {
                System.out.println("hai perso");
            }
        });

        bSotto.addActionListener(e -> {
            if (Game.getInstance().canMove(Settings.Directions.DOWN)) {
                Game.getInstance().move(Settings.Directions.DOWN);
            } else {
                System.out.println("hai perso");
            }
        });

        bDestra.addActionListener(e -> {
            if (Game.getInstance().canMove(Settings.Directions.RIGHT)) {
                Game.getInstance().move(Settings.Directions.RIGHT);
            } else {
                System.out.println("hai perso");
            }
        });

        bSinistra.addActionListener(e -> {
            if (Game.getInstance().canMove(Settings.Directions.LEFT)) {
                Game.getInstance().move(Settings.Directions.LEFT);
            } else {
                System.out.println("hai perso");
            }
        });

        bWallPlace.addActionListener(e -> {
            Settings.Orientations orientation = rWallVert.isSelected() ? Settings.Orientations.VERTICAL : Settings.Orientations.HORIZONTAL;
            Integer row = Integer.parseInt(fWallX.getText());
            Integer column = Integer.parseInt(fWallY.getText());
            Coordinates p = new Coordinates(row, column);
            if (Game.getInstance().canPlaceWall(p, orientation, Game.getInstance().getCurrentPlayer())) {
                Game.getInstance().placeWall(p, orientation, Game.getInstance().getCurrentPlayer());
            }

        });
    }

    public void setTurno(String frase){
        lTurno.setText("Turno: " + frase);
    }

    public void setAnnuncio(String frase){
        lAnnunci.setText("Annuncio: " + frase);
    }
}
