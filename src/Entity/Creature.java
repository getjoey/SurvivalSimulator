package Entity;

import Settings.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Creature implements IGameEntity {

    private int energy;
    private int vision; //vision is its range of sight, ie 5 would be 5 grid blocks in every direction
    private int cx;
    private int cy;
    private float speed;
    private int energyTurnCost;
    private GridSpaceType visionArray[][];


    public Creature(){
        initializeCreature();
    }

    private void initializeCreature(){
        energy = 100;
        vision = 7;
        energyTurnCost = 2;
        visionArray = new GridSpaceType[vision*2+1][vision*2+1];
        Random ran = new Random();
        speed = ran.nextFloat()+0.1f;
    }

    //draws a Blue circle as creature with a Cyan outline
    public void draw(Graphics g,int x , int y, int size){
        if(Settings.drawVisionOn)
        {
            drawVision(g,x,y);
        }
        //g.fillRect(x,y,size,size);
        g.setColor(Color.BLUE);
        g.drawOval(x,y,size,size);
        g.setColor(Color.CYAN);
        g.fillOval(x,y,size,size);
    }

    //optional mostly used for debugging
    public void drawVision(Graphics g, int x, int y){
        g.setColor(new Color(10,0,0,54));
        for(int i=0; i<visionArray.length;i++)
        {
            for(int j=0; j<visionArray.length; j++)
            {
                g.fillRect((x/Settings.squareSize+i-vision)* Settings.squareSize,
                        (y/Settings.squareSize+j-vision)* Settings.squareSize,
                        Settings.squareSize,
                        Settings.squareSize);
            }
        }
    }

    //FIND A GET AN ACTUAL GOOD MOVEMENT --------------------
    public Point getMove(IGameEntity[][] grid, int cx, int cy){  //cx and cy are current position in grid
        this.cx = cx;
        this.cy = cy;
        visionScan(grid);
        return getMovement(grid);
    }

    //updates vision
    private void visionScan(IGameEntity[][] grid){
        //visionArray
        for(int x=-vision; x<=vision; x++)
        {
            for(int y=-vision; y<=vision; y++)
            {
                //its on the map (not out of bounds check)
                if((cx+x) >=0 && (cx+x) < Settings.gridSize && (cy+y)>=0 && (cy+y) < Settings.gridSize){
                    if(grid[cx+x][cy+y] instanceof Creature){
                        visionArray[x+vision][y+vision] = GridSpaceType.C; //creature
                    }else if(grid[cx+x][cy+y] instanceof Food){
                        visionArray[x+vision][y+vision] = GridSpaceType.F; //food
                    }else{
                        visionArray[x+vision][y+vision] = GridSpaceType.W; //walkable space
                    }
                }
                else{ //off the map
                    visionArray[x+vision][y+vision] = GridSpaceType.N; //NOT walkable out of bounds ignore!
                }
            }
        }
        visionArray[vision][vision] = GridSpaceType.S; //self is in middle of grid always
    }

    private Point getMovement(IGameEntity[][] grid){
        Point p = findNearestDesiredGridSpace(GridSpaceType.F);
        //System.out.println("Bnext = "+next);
        //System.out.println("Adest = "+p);
        //System.out.println();

        return getNextPosition(new Point(cx,cy),p);
    }

    //if goal is to find food... put food as desired which is (GridSpaceType.F)
    private Point findNearestDesiredGridSpace(GridSpaceType desired){
        int x=0;
        int y=0;

        double dist = 0; //0 means you, if this is final result, then no desired item/food wtv has been found
        double temp;

        //your pos is in middle of vision array
        for(int i=0; i<visionArray.length; i++)
        {
            for(int j=0; j<visionArray.length; j++)
            {
                if(visionArray[i][j] == desired){
                    temp = Math.sqrt( Math.pow(Math.abs(vision-i),2) +  Math.pow(Math.abs(vision-j),2));
                    if(dist == 0){
                        dist = temp;
                        x=i;
                        y=j;
                    }else if(temp <dist){
                        dist = temp;
                        x=i;
                        y=j;
                    }
                }
            }
        }

        if(dist == 0){ //no point was found
            return new Point(Settings.gridSize/2,Settings.gridSize/2);
            //return new Point(cx,cy); //should wander towards center? TO DO!!!!!!!
        }

        return convertVisionPointToBigGridPoint(new Point(x,y));
    }

    //converts small vision coordinates to bigger grid coordinates
    private Point convertVisionPointToBigGridPoint(Point a){
        int x = a.x -vision + cx  ;
        int y= a.y -vision + cy;
        return new Point(x,y);
    }

    private Point getNextPosition(Point s, Point d){
        int x= s.x;
        int y= s.y;
        if(d.x > s.x ){
            x++;
        }else if(d.x < s.x){
            x--;
        }

        if(d.y > s.y ){
            y++;
        }else if(d.y < s.y){
            y--;
        }
        return new Point(x,y);


    }
    //--------------------




    //ACTUAL MOVEMENT --------------------
    public void doMove(IGameEntity[][] grid, Point move){
        if(move == new Point(cx,cy)){
            //do nothing!
        }
        else if(checkCollisionTrue(move,grid)){
            confrontCollision(move,grid);
        }else{
            grid[move.x][move.y] = this;
            grid[cx][cy] = null;
        }
        this.cx = move.x;
        this.cy = move.y;

    }

    private boolean checkCollisionTrue(Point next, IGameEntity[][] grid){
        if(grid[next.x][next.y] != null){
            return true;
        }
        else
            return false;
    }

    private void confrontCollision(Point next, IGameEntity[][] grid){
        if(grid[next.x][next.y] instanceof Food){
            //absorb its energy, remove food
            this.energy += ((Food)grid[next.x][next.y]).getEnergyGiven();
            grid[next.x][next.y] = null;
            grid[next.x][next.y] = this;
            grid[cx][cy] = null;
        }
    }
    //---------------------------------


    public float getSpeed() {
        return speed;
    }


    public void reduceEnergy(IGameEntity[][] grid){
        //reduce energy each turn
        energy -= energyTurnCost;

        //die
        if(energy <=0){
            System.out.println("dead");
            grid[cx][cy] = null;
        }
    }
}
