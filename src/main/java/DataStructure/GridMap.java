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
        grid = new IGameEntity[GeneralSettings.gridSize][GeneralSettings.gridSize];
        ran = new Random();
        creatureFactory = new CreatureFactory();

        initMap();
    }


    private void initMap(){

        //get data from json config file
        int amountA = Integer.parseInt(JSONrw.getCreatureA().get("initialAmount").toString());
        int amountB = Integer.parseInt(JSONrw.getCreatureB().get("initialAmount").toString());


        //Creature type A
        for(int i=0; i<amountA; i++){
            if(GeneralSettings.creaturesSpawnOnlyAtEdge){
                spawnRandomCoordAtEdgeOfMap("A");
            }
            else{
                spawnRandomCoordAnywhereOnMap("A");
            }

        }

        //Creature Type B
        for(int i=0; i<amountB; i++){
            if(GeneralSettings.creaturesSpawnOnlyAtEdge){
                spawnRandomCoordAtEdgeOfMap("B");
            }
            else{
                spawnRandomCoordAnywhereOnMap("B");
            }
        }

        //get data from json config file
        int initAmount = Integer.parseInt(JSONrw.getMapSettings().get("initialFoodAmount").toString());
        //place food
        spawnRandomFood(initAmount);

    }

    private void spawnRandomCoordAtEdgeOfMap(String type){
        int x =0;
        int y =0;
        int xyAxis; //0 is fixed x axis, 1 is fixed yaxis
        boolean notFoundSpot = true;

        while(notFoundSpot){
            x=0; y=0;
            xyAxis = ran.nextInt(2); //0 is fixed x axis, 1 is fixed yaxis
            if(xyAxis == 0)//xaxis is fixed
            {
                x = ran.nextInt(2); //0 is left side, 1 is right side of grid
                y = ran.nextInt(GeneralSettings.gridSize);
                if(x !=0){ //set proper x
                    x = GeneralSettings.gridSize-1;
                }
            }
            else //yaxis is fixed
            {
                x = ran.nextInt(GeneralSettings.gridSize);
                y = ran.nextInt(2); //0 is top, 1 is bottom of grid
                if(y !=0){ //set proper y
                    y = GeneralSettings.gridSize-1;
                }
            }

            //check to see if there's already a creature there...
            if(grid[x][y] == null){
                notFoundSpot = false;
            }
        }
        spawnCreature(x,y,type);

    }

    private void spawnRandomCoordAnywhereOnMap(String type){
        int x =0;
        int y =0;
        int xyAxis; //0 is fixed x axis, 1 is fixed yaxis
        boolean notFoundSpot = true;

        while(notFoundSpot){
            y = ran.nextInt(GeneralSettings.gridSize);
            x = ran.nextInt(GeneralSettings.gridSize);
            //check to see if there's already a creature there...
            if(grid[x][y] == null){
                notFoundSpot = false;
            }
        }
        spawnCreature(x,y,type);
    }

    private void spawnCreature(int x, int y, String type){
        grid[x][y] = creatureFactory.makeCreature(type);
        ((Creature)grid[x][y]).setCy(y);
        ((Creature)grid[x][y]).setCx(x);
    }

    public void spawnRandomFood(int k){

        int foodPlaced = 0;
        int currentFaultCount=0;
        int maxFaultCount = 10000; //this prevents it from going insane if maps full of food... should be fixed later
        while(foodPlaced != k && maxFaultCount > currentFaultCount){
            int x=0;
            int y=0;

            x= ran.nextInt(GeneralSettings.gridSize-2) + 1; //places food anywhere not on edge of map x-axis
            y= ran.nextInt(GeneralSettings.gridSize-2) + 1; //places food anywhere not on edge of map y-axis

            if(grid[x][y] == null){
                grid[x][y] = new Food();
                foodPlaced++;
            }else{
                currentFaultCount++;
            }
        }
        //System.out.println(currentFaultCount);
        if(maxFaultCount<=currentFaultCount){
            System.out.println("serious faulting occured. check code");
        }
    }



    public IGameEntity[][] getGrid(){
        return grid;
    }
}
