package View;

import GameLoop.SimulatorLoop;
import Settings.GeneralSettings;

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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        simulationDrawingPane = SimulationDrawingPane.getInstance();
        this.getContentPane().add(simulationDrawingPane);
        this.setVisible(true);
        this.pack();
        //this.setResizable(false);
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
