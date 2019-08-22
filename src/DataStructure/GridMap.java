package DataStructure;

import Entity.Creature;
import Entity.CreatureFactory;
import Entity.Food;
import Entity.IGameEntity;
import Settings.*;

import java.util.Random;

public class GridMap { //square map

    private Random ran;
    private IGameEntity[][] grid;
    private CreatureFactory creatureFactory;

    public GridMap(){
        grid = new IGameEntity[MapSettings.gridSize][MapSettings.gridSize];
        ran = new Random();
        creatureFactory = new CreatureFactory();

        initMap();
    }


    private void initMap(){

        //place creatures at edge of map randomly
        //Creature type A
        for(int i=0; i<CreatureASettings.initialAmount; i++){
            spawnRandomCoordAtEdgeOfMap("A");
        }

        //Creature Type B
        for(int i=0; i<CreatureBSettings.initialAmount; i++){
            spawnRandomCoordAtEdgeOfMap("B");
        }

        //place food
        spawnRandomFood(MapSettings.InitialFoodAmount);

    }

    private void spawnRandomCoordAtEdgeOfMap(String type){
        int x =0;
        int y =0;
        int xyAxis = ran.nextInt(2); //0 is fixed x axis, 1 is fixed yaxis
        boolean notFoundSpot = true;

        while(notFoundSpot){
            x=0; y=0;
            xyAxis = ran.nextInt(2); //0 is fixed x axis, 1 is fixed yaxis
            if(xyAxis == 0)//xaxis is fixed
            {
                x = ran.nextInt(2); //0 is left side, 1 is right side of grid
                y = ran.nextInt(MapSettings.gridSize);
                if(x !=0){ //set proper x
                    x = MapSettings.gridSize-1;
                }
            }
            else //yaxis is fixed
            {
                x = ran.nextInt(MapSettings.gridSize);
                y = ran.nextInt(2); //0 is top, 1 is bottom of grid
                if(y !=0){ //set proper y
                    y = MapSettings.gridSize-1;
                }
            }

            //check to see if there's already a creature there...
            if(grid[x][y] == null){
                notFoundSpot = false;
            }
        }
        grid[x][y] = creatureFactory.makeCreature(type);
        ((Creature)grid[x][y]).setCy(y);
        ((Creature)grid[x][y]).setCx(x);

    }

    public void spawnRandomFood(int k){

        int foodPlaced = 0;
        while(foodPlaced != k){
            int x=0;
            int y=0;

            x= ran.nextInt(MapSettings.gridSize-2) + 1; //places food anywhere not on edge of map x-axis
            y= ran.nextInt(MapSettings.gridSize-2) + 1; //places food anywhere not on edge of map y-axis

            if(grid[x][y] == null){
                grid[x][y] = new Food();
                foodPlaced++;
            }
        }
    }



    public IGameEntity[][] getGrid(){
        return grid;
    }
}
