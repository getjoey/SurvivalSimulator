package Controllers;

import DataStructure.GridMap;
import Entity.Creature;
import Entity.IGameEntity;
import Settings.MapSettings;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Controller {

    private static Controller instance = null;
    private GridMap map;
    private ArrayList<Creature> creatureList;


    private Controller( )
    {
        map = new GridMap();
        creatureList = new ArrayList<>();
    }

    public static synchronized Controller getInstance(){
        if(instance ==null){
            instance = new Controller();
        }
      return instance;
    }


    //each frame this is called
    public void sortCreaturesBySpeed()
    {
        creatureList.clear();
        IGameEntity[][] grid = map.getGrid();
        for (int x = 0; x < MapSettings.gridSize; x++)
        {
            for (int y = 0; y < MapSettings.gridSize; y++) {

                if(grid[x][y] != null)
                {
                    if(grid[x][y] instanceof Creature)
                    {
                        creatureList.add((Creature)grid[x][y]);
                    }
                }
            }
        }

        //sort list by speed
        creatureList.sort(new Comparator<Creature>() {
            @Override
            public int compare(Creature o1, Creature o2) {
                if(o1.getSpeed() > o2.getSpeed()){
                    return -1;
                }
                else if(o1.getSpeed() == o2.getSpeed()){
                    return 0;
                }
                else
                    return 1;
                }
        });

    }


    //execution
    public void doMoves(){
        for(Creature c : creatureList){
            c.getMove(map.getGrid());
            c.doMove(map.getGrid());
            c.reduceEnergyDieReproduce(map.getGrid());
        }
    }

    public void spawnNewFoods(){
        map.spawnRandomFood(MapSettings.foodSpawnEachTurn);
    }



    public GridMap getMap() {
        return map;
    }

    public void resetGame()
    {
        System.out.println("resetting game");
        map = new GridMap();
        creatureList.clear();
    }

    public void printStatistics(){
        System.out.println("There are "+creatureList.size()+" creatures on map");

        int countA =0;
        int countB =0;
        for(int i=0; i<creatureList.size();i++){
            if(creatureList.get(i).getType().equals("A")){
                countA++;
            }
            if(creatureList.get(i).getType().equals("B")){
                countB++;
            }
        }
        System.out.println("of which...");
        System.out.println("A type = "+countA);
        System.out.println("B type = "+countB);

    }
}
