package Controllers;

import DataStructure.Map;
import Entity.Creature;
import Entity.IGameEntity;
import Settings.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    static Controller instance = null;
    Map map;
    HashMap<Creature,Point> moveList;



    private Controller( )
    {
        map = new Map();
        moveList = new HashMap<>();
    }

    public static synchronized Controller getInstance(){
        if(instance ==null){
            instance = new Controller();
        }
      return instance;
    }


    //each frame this is called
    public void getMoves()
    {
        moveList.clear();
        IGameEntity[][] grid = map.getGrid();
        for (int x = 0; x < Settings.gridSize; x++)
        {
            for (int y = 0; y < Settings.gridSize; y++) {

                if(grid[x][y] != null)
                {
                    if(grid[x][y] instanceof Creature)
                    {
                        moveList.put((Creature)grid[x][y],((Creature)grid[x][y]).getMove(grid,x,y)  );
                    }
                }
            }
        }

        //should do a sorting algorithm for speed.... TO DO...!!!!


    }

    public void doMoves(){
        for (HashMap.Entry<Creature, Point> entry : moveList.entrySet())
        {
            entry.getKey().doMove(map.getGrid(),entry.getValue());
        }
    }


    public Map getMap() {
        return map;
    }
}
