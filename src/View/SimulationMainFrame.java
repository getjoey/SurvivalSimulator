package View;

import Settings.Settings;

import javax.swing.*;
import java.awt.*;

public class SimulationMainFrame extends JFrame {

    SimulationDrawingPane simulationDrawingPane;

    public SimulationMainFrame(){
        initFrame();
    }

    public void initFrame(){
        this.setTitle("Simulator");
        int size = Settings.gridSize*Settings.squareSize;
        this.setSize(size+Settings.paneOffsetX*2,size+Settings.paneOffsetY*2); //square frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        simulationDrawingPane = SimulationDrawingPane.getInstance();
        this.getContentPane().add(simulationDrawingPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

}
