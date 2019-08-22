package Controllers;

import DataStructure.Map;
import Entity.Creature;
import Entity.IGameEntity;
import Settings.Settings;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    private static Controller instance = null;
    private Map map;
    private ArrayList<Pair<Creature,Point>> moveList;


    private Controller( )
    {
        map = new Map();
        moveList = new ArrayList<>();
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
                        moveList.add(new Pair( ((Creature)grid[x][y]),((Creature)grid[x][y]).getMove(grid,x,y)));
                    }
                }
            }
        }

        //sorts moves by speed, so faster creatures, move first
        sortMoves();
    }

    //Order by fastest.
    public void sortMoves(){
        Collections.sort(moveList, new Comparator<Pair<Creature,Point>>(){
            @Override
            public int compare(Pair<Creature, Point> o1, Pair<Creature, Point> o2) {
                if(o1.getKey().getSpeed() > o2.getKey().getSpeed()){
                    return -1;
                }else if(o1.getKey().getSpeed() == o2.getKey().getSpeed()){
                    return 0;
                }
                else return 1;
            }
        });

    }

    //execution
    public void doMoves(){
        for(Pair<Creature, Point> p : moveList){
            p.getKey().doMove(map.getGrid(), p.getValue());
        }
    }


    public Map getMap() {
        return map;
    }

    public void resetGame()
    {
        System.out.println("resetting game");
        map = new Map();
        moveList = new ArrayList<>();
    }
}
