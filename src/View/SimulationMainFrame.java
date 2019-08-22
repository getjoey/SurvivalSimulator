package View;

import GameLoop.SimulatorLoop;
import Settings.MapSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SimulationMainFrame extends JFrame implements KeyListener {

    private SimulationDrawingPane simulationDrawingPane;
    private SimulatorLoop gameLoop;

    public SimulationMainFrame(){
        initFrame();
        addKeyListener(this);
        gameLoop = SimulatorLoop.getInstance();
    }

    public void initFrame(){
        this.setTitle("Simulator");
        int size = MapSettings.gridSize* MapSettings.squareSize;
        this.setSize(size+ MapSettings.paneOffsetX*2,size+ MapSettings.paneOffsetY*2); //square frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        simulationDrawingPane = SimulationDrawingPane.getInstance();
        this.getContentPane().add(simulationDrawingPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        gameLoop.setKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
