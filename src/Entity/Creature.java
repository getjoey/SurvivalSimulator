package Entity;

import Settings.MapSettings;

import java.awt.*;
import java.util.Random;

public class Creature implements IGameEntity {

    private int initialEnergy;
    private int energy; //current life energy, when <0 creature dies
    private int vision; //vision is its range of sight, ie 5 would be 5 grid blocks in every direction
    private int cx; //current x pos on grid
    private int cy; //current y pos on grid
    private float baseSpeed; //determines turn order
    private int energyTurnCost; //cost of energy each turn (tick of gameloop)
    private float reproductionChance; //chance to reproduce
    private Color color;
    private GridSpaceType visionArray[][]; //what they can see on grid
    private Random ran;

    public Creature(){

    }

    public void initializeCreature(int e, int v, int etc, float rc, float bs, Color c){
        ran = new Random();

        initialEnergy = e;
        energy = e;
        vision = v;
        energyTurnCost = etc;
        reproductionChance = rc; //0.1 = 10%
        visionArray = new GridSpaceType[vision*2+1][vision*2+1];
        color = c;
        baseSpeed = bs + ((float)ran.nextInt(10))/100;
    }


    //draws a Blue circle as creature with a Cyan outline
    public void draw(Graphics g,int x , int y, int size){
        if(MapSettings.drawVisionOn)
        {
            drawVision(g,x,y);
        }
        //g.fillRect(x,y,size,size);
        g.setColor(Color.CYAN);
        g.drawOval(x,y,size,size);
        g.setColor(color);
        g.fillOval(x,y,size,size);
    }

    //optional mostly used for debugging
    public void drawVision(Graphics g, int x, int y){
        g.setColor(new Color(10,0,0,54));
        for(int i=0; i<visionArray.length;i++)
        {
            for(int j=0; j<visionArray.length; j++)
            {
                g.fillRect((x/ MapSettings.squareSize+i-vision)* MapSettings.squareSize,
                        (y/ MapSettings.squareSize+j-vision)* MapSettings.squareSize,
                        MapSettings.squareSize,
                        MapSettings.squareSize);
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
                if((cx+x) >=0 && (cx+x) < MapSettings.gridSize && (cy+y)>=0 && (cy+y) < MapSettings.gridSize){
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
            return new Point(MapSettings.gridSize/2, MapSettings.gridSize/2);
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
        return baseSpeed;
    }


    public void reduceEnergyDieReproduce(IGameEntity[][] grid){
        //reduce energy each turn
        energy -= energyTurnCost;

        //die
        if(energy <=0){
            System.out.println("dead");
            grid[cx][cy] = null;
        }
        if(ran.nextFloat() <= reproductionChance){
            //System.out.println("baby");
            reproduce(grid);
        }

    }

    public void reproduce(IGameEntity[][] grid){
        //copy the genes
        //place new creature//next to parent.
        Creature baby = new Creature();
        baby.initializeCreature(initialEnergy,vision,energyTurnCost,reproductionChance,baseSpeed,color);

        if(grid[this.cx-1][this.cy-1] == null){
            grid[this.cx-1][this.cy-1] = baby;
        }
        else if (grid[this.cx-1][this.cy] == null){
            grid[this.cx-1][this.cy] = baby;
        }
        else if (grid[this.cx+1][this.cy] == null) {
            grid[this.cx+1][this.cy] = baby;
        }


    }

}
