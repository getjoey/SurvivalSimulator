package View;

import Controllers.CreatureController;
import Entity.IGameEntity;
import Settings.*;

import javax.swing.*;
import java.awt.*;

public class SimulationDrawingPane extends JPanel implements Runnable{

    private static SimulationDrawingPane instance = null;
    private CreatureController simulationCreatureController;

    private SimulationDrawingPane() {
        simulationCreatureController = CreatureController.getInstance();
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

        IGameEntity[][] grid = simulationCreatureController.getMap().getGrid();
        for (int x = 0; x < MapSettings.gridSize; x++) {
            for (int y = 0; y < MapSettings.gridSize; y++) {
                if(grid[x][y] != null){
                    grid[x][y].draw(g, x * MapSettings.squareSize, y * MapSettings.squareSize, MapSettings.squareSize);
                }

            }
        }
    }

    //draw grid (optional) //can be removed in settings
    public void drawGrid (Graphics g)
    {
        if(MapSettings.gridDisplayOn){
            for (int i = 0; i <= MapSettings.gridSize; i++)
            {
                g.setColor(Color.GRAY);
                g.drawLine((i * MapSettings.squareSize),
                        0,
                        (i * MapSettings.squareSize),
                        (MapSettings.gridSize * MapSettings.squareSize));
                g.drawLine(0,
                        (i * MapSettings.squareSize),
                        (MapSettings.gridSize * MapSettings.squareSize),
                        (i * MapSettings.squareSize));
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

