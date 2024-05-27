package it.unical.demacs.ai.view;

import it.unical.demacs.ai.model.Game;
import it.unical.demacs.ai.model.Player;
import it.unical.demacs.ai.model.Settings;
import it.unical.demacs.ai.utils.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainMenu extends JPanel{

    private IconItem[] icons;
    private JComboBox<IconItem> NPlayer;
    private JComboBox<IconItem> SPlayer;
    private JComboBox<IconItem> EPlayer;
    private JComboBox<IconItem> WPlayer;

    private JComboBox<String> NSolver;
    private JComboBox<String> SSolver;
    private JComboBox<String> ESolver;
    private JComboBox<String> WSolver;

    private JSlider sliderWalls;
    private JSlider sliderMatrix;

    String[] solvers = new String[]{"No Player", "ASPetta e Infera", "Grissin Van Bon", "JYPapi", "IRS", "PALO"};

    private JButton startButton;

    public MainMenu() {
        super();


        icons = new IconItem[]{
                new IconItem(getIconResized("calimeri"), "Calimeri", new Color(43, 169, 19), "calimeri"),
                new IconItem(getIconResized("dodaro"), "Dodaro", new Color(0, 0, 0), "dodaro"),
                new IconItem(getIconResized("ianni"), "Ianni", new Color(0, 85, 184), "ianni"),
                new IconItem(getIconResized("perri"), "Perri", new Color(236, 0, 140), "perri"),
                new IconItem(getIconResized("spataro"), "Spataro", new Color(244, 212, 34), "spataro"),
                new IconItem(getIconResized("vanbon"), "Van Bon", new Color(244, 34, 34), "vanbon"),
        };

        NSolver = new JComboBox<>(solvers);
        SSolver = new JComboBox<>(solvers);
        ESolver = new JComboBox<>(solvers);
        WSolver = new JComboBox<>(solvers);

        NPlayer = new JComboBox<>(icons);
        SPlayer = new JComboBox<>(icons);
        EPlayer = new JComboBox<>(icons);
        WPlayer = new JComboBox<>(icons);

        renderer(NPlayer);
        renderer(SPlayer);
        renderer(EPlayer);
        renderer(WPlayer);

        NPlayer.setEnabled(false);
        SPlayer.setEnabled(false);
        EPlayer.setEnabled(false);
        WPlayer.setEnabled(false);

        NPlayer.setSelectedItem(null);
        SPlayer.setSelectedItem(null);
        EPlayer.setSelectedItem(null);
        WPlayer.setSelectedItem(null);

        sliderWalls = new JSlider(3, 10, 5);
        sliderMatrix = new JSlider(6, 15, 9);

        sliderWalls.setPaintTicks(true);
        sliderWalls.setMajorTickSpacing(1);
        sliderWalls.setPaintLabels(true);

        sliderMatrix.setPaintTicks(true);
        sliderMatrix.setMajorTickSpacing(1);
        sliderMatrix.setPaintLabels(true);



        NSolver.addActionListener(e -> {
            if (NSolver.getSelectedItem().equals("No Player")) {
                NPlayer.setEnabled(false);
                NPlayer.setSelectedItem(null);
            } else {
                NPlayer.setEnabled(true);
            }
        });

        SSolver.addActionListener(e -> {
            if (SSolver.getSelectedItem().equals("No Player")) {
                SPlayer.setEnabled(false);
                SPlayer.setSelectedItem(null);
            } else {
                SPlayer.setEnabled(true);
            }
        });

        ESolver.addActionListener(e -> {
            if (ESolver.getSelectedItem().equals("No Player")) {
                EPlayer.setEnabled(false);
                EPlayer.setSelectedItem(null);
            } else {
                EPlayer.setEnabled(true);
            }
        });

        WSolver.addActionListener(e -> {
            if (WSolver.getSelectedItem().equals("No Player")) {
                WPlayer.setEnabled(false);
                WPlayer.setSelectedItem(null);
            } else {
                WPlayer.setEnabled(true);
            }
        });

        startButton = new JButton("Start");

        startButton.addActionListener(e -> {
            if ((NSolver.getSelectedItem() != "No Player" && NPlayer.getSelectedItem() == null) || (SSolver.getSelectedItem() != "No Player" && SPlayer.getSelectedItem() == null) || (ESolver.getSelectedItem() != "No Player" && EPlayer.getSelectedItem() == null) || (WSolver.getSelectedItem() != "No Player" && WPlayer.getSelectedItem() == null)){
                JOptionPane.showMessageDialog(this, "Tutti i player attivi devono avere un Icon", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ArrayList<Integer> iconIndex = new ArrayList<>();
            if (NPlayer.getSelectedItem() != null) {
                iconIndex.add(NPlayer.getSelectedIndex());
            }
            if (SPlayer.getSelectedItem() != null) {
                iconIndex.add(SPlayer.getSelectedIndex());
            }
            if (EPlayer.getSelectedItem() != null) {
                iconIndex.add(EPlayer.getSelectedIndex());
            }
            if (WPlayer.getSelectedItem() != null) {
                iconIndex.add(WPlayer.getSelectedIndex());
            }

            if (iconIndex.size() < 2){
                JOptionPane.showMessageDialog(this, "Non ci sono abbastanza giocatori", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Controlla se ci sono duplicati di icona
            for (int i = 0; i < iconIndex.size(); i++) {
                for (int j = i + 1; j < iconIndex.size(); j++) {
                    if (iconIndex.get(i).equals(iconIndex.get(j))) {
                        JOptionPane.showMessageDialog(this, "Non possono esserci due player con la stessa icona", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // Imposta le impostazioni
            Settings.boardDim = sliderMatrix.getValue();
            Map<Settings.Directions, Integer> wallsAvailable = new HashMap<>();
            Random Random = new Random();
            Player temp = null;
            if (NPlayer.getSelectedItem() != null) {
                temp = new Player(new Coordinates(0, Random.nextInt(0, Settings.boardDim)), Settings.Directions.DOWN, icons[NPlayer.getSelectedIndex()].color, icons[NPlayer.getSelectedIndex()].getResourceName());
                Game.getInstance().addPlayer(temp);
                temp.setName(NSolver.getSelectedItem().toString());
                wallsAvailable.put(Settings.Directions.DOWN, sliderWalls.getValue());
            }
            if (SPlayer.getSelectedItem() != null) {
                temp = new Player(new Coordinates(Settings.boardDim - 1, Random.nextInt(0, Settings.boardDim)), Settings.Directions.UP, icons[SPlayer.getSelectedIndex()].color, icons[SPlayer.getSelectedIndex()].getResourceName());
                Game.getInstance().addPlayer(temp);
                temp.setName(SSolver.getSelectedItem().toString());
                wallsAvailable.put(Settings.Directions.UP, sliderWalls.getValue());
            }
            if (EPlayer.getSelectedItem() != null) {
                temp = new Player(new Coordinates(Random.nextInt(0, Settings.boardDim), Settings.boardDim - 1), Settings.Directions.LEFT, icons[EPlayer.getSelectedIndex()].color, icons[EPlayer.getSelectedIndex()].getResourceName());
                Game.getInstance().addPlayer(temp);
                temp.setName(ESolver.getSelectedItem().toString());
                wallsAvailable.put(Settings.Directions.LEFT, sliderWalls.getValue());
            }
            if (WPlayer.getSelectedItem() != null) {
                temp = new Player(new Coordinates(Random.nextInt(0, Settings.boardDim), 0), Settings.Directions.RIGHT, icons[WPlayer.getSelectedIndex()].color, icons[WPlayer.getSelectedIndex()].getResourceName());
                Game.getInstance().addPlayer(temp);
                temp.setName(WSolver.getSelectedItem().toString());
                wallsAvailable.put(Settings.Directions.RIGHT, sliderWalls.getValue());
            }

            Game.getInstance().setWallsAvailable(wallsAvailable);
            Game.getInstance().chooseRandomPlayer();
            //imposta il numero massimo di muri per giocatore
            Game.getInstance().setMaxWallXPlayer(sliderWalls.getValue());


            // Chiudi il dialog
            SwingUtilities.getWindowAncestor(this).dispose();
        });

        JPanel AISelectionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        AISelectionPanel.add(new JLabel("Position"), c);
        AISelectionPanel.add(new JLabel("Nord:"), c);
        AISelectionPanel.add(new JLabel("Sud:"), c);
        AISelectionPanel.add(new JLabel("East:"), c);
        AISelectionPanel.add(new JLabel("West:"), c);

        c.gridx = 1;
        c.weightx = 3;

        AISelectionPanel.add(new JLabel("Solver"), c);
        AISelectionPanel.add(NSolver, c);
        AISelectionPanel.add(SSolver, c);
        AISelectionPanel.add(ESolver, c);
        AISelectionPanel.add(WSolver, c);

        c.gridx = 4;
        c.weightx = 1;

        AISelectionPanel.add(new JLabel("Player Icon"), c);
        AISelectionPanel.add(NPlayer, c);
        AISelectionPanel.add(SPlayer, c);
        AISelectionPanel.add(EPlayer, c);
        AISelectionPanel.add(WPlayer, c);

        c.gridy = 5;
        c.gridx = 0;
        c.gridwidth = 5;
        AISelectionPanel.add(new JSeparator(), c);

        c.gridy = 6;
        c.gridwidth = 1;

        AISelectionPanel.add(new JLabel("Walls"), c);

        c.gridx = 1;
        c.gridwidth = 4;

        AISelectionPanel.add(sliderWalls, c);
    
        c.gridy = 7;
        c.gridx = 0;
        c.gridwidth = 1;

        AISelectionPanel.add(new JLabel("Matrix"), c);

        c.gridx = 1;
        c.gridwidth = 4;

        AISelectionPanel.add(sliderMatrix, c);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 5;

        AISelectionPanel.add(startButton, c);

        AISelectionPanel.setPreferredSize(new Dimension(400, 400)) ;

        add(AISelectionPanel);
    }

    // Classe per gli elementi del JComboBox
    private class IconItem {
        private ImageIcon icon;
        private String description;
        private Color color;
        private String resourceName;

        public IconItem(ImageIcon icon, String description, Color c, String resourceName) {
            this.icon = icon;
            this.description = description;
            this.color = c;
            this.resourceName = resourceName;
        }

        public ImageIcon getIcon() {
            return icon;
        }

        public String getDescription() {
            return description;
        }

        public Color getColor() {
            return color;
        }

        public String getResourceName() {
            return resourceName;
        }
    }

    public void renderer(JComboBox comboBox) {
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof IconItem) {
                    IconItem item = (IconItem) value;
                    setIcon(item.getIcon());
                    setText(item.getDescription());
                }
                return this;
            }
        });
    }

    public ImageIcon getIconResized(String name) {
        Image image = new ImageIcon("src/main/resources/" + name + ".png").getImage();
        Image imageResized = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        return new ImageIcon(imageResized);
    }


}


