import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Map { //square map

    Random ran;
    IGameEntity[][] grid;
    int foodAmount;
    int creatureAmount;

    public Map( int foodAmount, int creatureAmount){
        this.foodAmount = foodAmount;
        this.creatureAmount = creatureAmount;

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
        spawnRandomFood();

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

    private void spawnRandomFood(){

        int foodPlaced = 0;
        while(foodPlaced != foodAmount){
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