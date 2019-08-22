package DataStructure;

import Entity.Creature;
import Entity.Food;
import Entity.IGameEntity;
import Settings.*;

import java.util.Random;

public class Map { //square map

    private Random ran;
    private IGameEntity[][] grid;
    private int creatureAmount = Settings.InitialCreatureAmount;
    private int initialFoodAmount = Settings.InitialFoodAmount;

    public Map(){
        grid = new IGameEntity[Settings.gridSize][Settings.gridSize];
        ran = new Random();

        initMap();
    }


    private void initMap(){

        //place creatures at edge of map randomly
        for(int i=0; i<creatureAmount; i++){
            spawnRandomCoordAtEdgeOfMap();
        }

        //place food
        spawnRandomFood(initialFoodAmount);

        /*
        grid[0][0] = new Creature();
        grid[1][0] = new Creature();
        grid[1][2] = new Food();
        grid[1][5] = new Food();
        grid[3][2] = new Food();
        grid[3][7] = new Food();
        grid[5][9] = new Food();
        grid[4][10] = new Food();
        */
    }

    private void spawnRandomCoordAtEdgeOfMap(){
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
                y = ran.nextInt(Settings.gridSize);
                if(x !=0){ //set proper x
                    x = Settings.gridSize-1;
                }
            }
            else //yaxis is fixed
            {
                x = ran.nextInt(Settings.gridSize);
                y = ran.nextInt(2); //0 is top, 1 is bottom of grid
                if(y !=0){ //set proper y
                    y = Settings.gridSize-1;
                }
            }

            //check to see if there's already a creature there...
            if(grid[x][y] == null){
                notFoundSpot = false;
            }
        }
        grid[x][y] = new Creature();

    }

    public void spawnRandomFood(int k){

        int foodPlaced = 0;
        while(foodPlaced != k){
            int x=0;
            int y=0;

            x= ran.nextInt(Settings.gridSize-2) + 1; //places food anywhere not on edge of map x-axis
            y= ran.nextInt(Settings.gridSize-2) + 1; //places food anywhere not on edge of map y-axis

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
