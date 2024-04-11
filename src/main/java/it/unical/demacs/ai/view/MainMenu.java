package it.unical.demacs.ai.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    String[] solvers = new String[]{"No Player" , "NO IA", "ASPetta e Infera", "Grissin Van Bon", "JYPapi", "IRS"};

    private JButton startButton;

    public MainMenu() {
        super();


        icons = new IconItem[]{
                new IconItem(getIconResized("calimeri"), "Calimeri"),
                new IconItem(getIconResized("dodaro"), "Dodaro"),
                new IconItem(getIconResized("ianni"), "Ianni"),
                new IconItem(getIconResized("perri"), "Perri"),
                new IconItem(getIconResized("spataro"), "Spataro"),
                new IconItem(getIconResized("vanbon"), "Van Bon"),
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

        sliderWalls = new JSlider(1, 10, 3);
        sliderWalls.setPaintTicks(true);
        sliderWalls.setPaintLabels(true);


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
        c.weightx = 2;

        AISelectionPanel.add(new JLabel("Solver"), c);
        AISelectionPanel.add(NSolver, c);
        AISelectionPanel.add(SSolver, c);
        AISelectionPanel.add(ESolver, c);
        AISelectionPanel.add(WSolver, c);

        c.gridx = 3;
        AISelectionPanel.add(new JLabel("Player Icon"), c);
        AISelectionPanel.add(NPlayer, c);
        AISelectionPanel.add(SPlayer, c);
        AISelectionPanel.add(EPlayer, c);
        AISelectionPanel.add(WPlayer, c);

        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 4;

        AISelectionPanel.add(new JSlider(1, 10, 3), c);

        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 4;

        AISelectionPanel.add(startButton, c);

        AISelectionPanel.setPreferredSize(new Dimension(400, 280)) ;

        add(AISelectionPanel);
    }

    // Classe per gli elementi del JComboBox
    private class IconItem {
        private ImageIcon icon;
        private String description;

        public IconItem(ImageIcon icon, String description) {
            this.icon = icon;
            this.description = description;
        }

        public ImageIcon getIcon() {
            return icon;
        }

        public String getDescription() {
            return description;
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


