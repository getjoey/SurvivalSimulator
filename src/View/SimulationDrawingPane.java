package View;

import Controllers.Controller;
import Entity.IGameEntity;
import Settings.*;

import javax.swing.*;
import java.awt.*;

public class SimulationDrawingPane extends JPanel {

    static SimulationDrawingPane instance = null;
    Controller simulationController;

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
        for (int x = 0; x < Settings.gridSize; x++) {
            for (int y = 0; y < Settings.gridSize; y++) {
                if(grid[x][y] != null){
                    grid[x][y].draw(g, x * Settings.squareSize, y * Settings.squareSize, Settings.squareSize);
                }

            }
        }
    }

    //draw grid (optional) //can be removed in settings
    public void drawGrid (Graphics g)
    {
        if(Settings.gridDisplayOn){
            for (int i = 0; i <= Settings.gridSize; i++)
            {
                g.setColor(Color.GRAY);
                g.drawLine((i * Settings.squareSize),
                        0,
                        (i * Settings.squareSize),
                        (Settings.gridSize * Settings.squareSize));
                g.drawLine(0,
                        (i * Settings.squareSize),
                        (Settings.gridSize * Settings.squareSize),
                        (i * Settings.squareSize));
            }
        }
    }


}

