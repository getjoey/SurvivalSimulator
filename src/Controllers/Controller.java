package Controllers;

import DataStructure.Map;
import Entity.Creature;
import Entity.IGameEntity;
import Settings.Settings;

public class Controller {

    static Controller instance = null;
    Map map;
    int creatureAmount = 100;
    int foodAmount = 200;


    private Controller( )
    {
        map = new Map(foodAmount , creatureAmount);
    }

    public static synchronized Controller getInstance(){
        if(instance ==null){
            instance = new Controller();
        }
      return instance;
    }


    //each frame this is called
    public void move()
    {
        IGameEntity[][] grid = map.getGrid();

        for (int x = 0; x < Settings.gridSize; x++)
        {
            for (int y = 0; y < Settings.gridSize; y++) {

                if(grid[x][y] != null)
                {
                    if(grid[x][y] instanceof Creature)
                    {
                        ((Creature)grid[x][y]).move(grid,x,y);
                    }
                }

            }
        }
    }


    public Map getMap() {
        return map;
    }
}
