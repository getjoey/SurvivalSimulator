import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
        /*
        int x=0;
        int y=0;
        for(ArrayList<IGameEntity> col : simulationController.getMap().getGrid())
        {
            for(IGameEntity ge : col)
            {
                if(ge != null)
                {
                    ge.draw(g,x*Settings.squareSize,y*Settings.squareSize,Settings.squareSize);
                }
                y++;
            }
            y=0;
            x++;
        }
    }
    */
        public void drawGrid (Graphics g)
        {
            //draw grid
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

