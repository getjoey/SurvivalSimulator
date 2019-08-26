package View;

import Controllers.Controller;
import Entity.IGameEntity;
import Settings.*;

import javax.swing.*;
import java.awt.*;

public class SimulationDrawingPane extends JPanel implements Runnable{

    private static SimulationDrawingPane instance = null;
    private Controller simulationController;

    private SimulationDrawingPane() {
        simulationController = Controller.getInstance();
    }

    public static synchronized SimulationDrawingPane getInstance() {
        if (instance == null) {
            instance = new SimulationDrawingPane();
        }
        return instance;
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawEntities(g);
        drawGrid(g);
    }

    public void drawEntities(Graphics g) {
        //draw entities on grid
        //drawing top to bottom. towards right

        IGameEntity[][] grid = simulationController.getMap().getGrid();
        for (int x = 0; x < GeneralSettings.gridSize; x++) {
            for (int y = 0; y < GeneralSettings.gridSize; y++) {
                if(grid[x][y] != null){
                    grid[x][y].draw(g, x * GeneralSettings.squareSize, y * GeneralSettings.squareSize, GeneralSettings.squareSize);
                }

            }
        }
    }

    //draw grid (optional) //can be removed in settings
    public void drawGrid (Graphics g)
    {
        if(GeneralSettings.gridDisplayOn){
            for (int i = 0; i <= GeneralSettings.gridSize; i++)
            {
                g.setColor(Color.GRAY);
                g.drawLine((i * GeneralSettings.squareSize),
                        0,
                        (i * GeneralSettings.squareSize),
                        (GeneralSettings.gridSize * GeneralSettings.squareSize));
                g.drawLine(0,
                        (i * GeneralSettings.squareSize),
                        (GeneralSettings.gridSize * GeneralSettings.squareSize),
                        (i * GeneralSettings.squareSize));
            }
        }
    }


    @Override
    public void run() {
        while(true){
            this.repaint();
        }
    }
}

