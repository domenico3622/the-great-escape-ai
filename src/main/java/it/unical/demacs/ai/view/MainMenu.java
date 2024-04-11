package it.unical.demacs.ai.view;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel{

    JCheckBox NcheckBox;
    JCheckBox ScheckBox;
    JCheckBox EcheckBox;
    JCheckBox WcheckBox;

    private JComboBox<String> NSolver;
    private JComboBox<String> SSolver;
    private JComboBox<String> ESolver;
    private JComboBox<String> WSolver;

    String[] solvers = new String[]{"" , "PG", "RA", "BV", "DV"};

    private JButton startButton;

    public MainMenu() {
        super();

        NcheckBox = new JCheckBox();
        ScheckBox = new JCheckBox();
        EcheckBox = new JCheckBox();
        WcheckBox = new JCheckBox();

        NSolver = new JComboBox<>(solvers);
        SSolver = new JComboBox<>(solvers);
        ESolver = new JComboBox<>(solvers);
        WSolver = new JComboBox<>(solvers);

        startButton = new JButton("Start");

        JPanel AISelectionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        AISelectionPanel.add(new JLabel("Position"), c);
        AISelectionPanel.add(new JLabel("N:"), c);
        AISelectionPanel.add(new JLabel("S:"), c);
        AISelectionPanel.add(new JLabel("E:"), c);
        AISelectionPanel.add(new JLabel("W:"), c);

        c.gridx = 1;
        AISelectionPanel.add(new JLabel("Activate"), c);
        AISelectionPanel.add(NcheckBox, c);
        AISelectionPanel.add(ScheckBox, c);
        AISelectionPanel.add(EcheckBox, c);
        AISelectionPanel.add(WcheckBox, c);

        c.gridx = 2;
        c.weightx = 2;

        AISelectionPanel.add(new JLabel("Solver"), c);
        AISelectionPanel.add(NSolver, c);
        AISelectionPanel.add(SSolver, c);
        AISelectionPanel.add(ESolver, c);
        AISelectionPanel.add(WSolver, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 3;

        AISelectionPanel.add(startButton, c);

        AISelectionPanel.setPreferredSize(new Dimension(400, 200)) ;

        add(AISelectionPanel);
    }

}
