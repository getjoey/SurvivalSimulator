package Entity;

import Entity.CreatureStrategies.*;
import Settings.GeneralSettings;

import java.awt.*;
import java.util.Random;

public class Creature implements IGameEntity {

    private String type; //this is mainly for debugging and statistics since its not needed otherwise
    private int initialEnergy;
    private int energy; //current life energy, when <0 creature dies
    private int vision; //vision is its range of sight, ie 5 would be 5 grid blocks in every direction
    private int cx; //current x pos on grid
    private int cy; //current y pos on grid
    private float baseSpeed;
    private float realSpeed; //determines turn order
    private int energyTurnCost; //cost of energy each turn (tick of gameloop)
    private float reproductionChance; //chance to reproduce
    private Color color;
    private GridSpaceType visionArray[][]; //what they can see on grid
    private Random ran;
    private Point nextMove;

    //strategies
    private FindMoveStrategy FindMoveStrategy;

    public Creature(int e, int v, int etc, float rc, float bs, Color c, String t){
        ran = new Random();

        initialEnergy = e;
        energy = e;
        vision = v;
        energyTurnCost = etc;
        reproductionChance = rc; //0.1 = 10%
        visionArray = new GridSpaceType[vision*2+1][vision*2+1];
        color = c;
        type = t;
        baseSpeed = bs;
        realSpeed = bs + ((float)ran.nextInt(10))/100;


        //strats
        if(v == 7){
            FindMoveStrategy = new FindClosestFood();
        }else{
            FindMoveStrategy = new FindClosestEnemy();
        }


    }

    //DRAWING--------------------------------------------
    //--------------------------------------------------
    //draws a Blue circle as creature with a Cyan outline
    public void draw(Graphics g,int x , int y, int size){
        if(GeneralSettings.drawVisionOn)
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
                g.fillRect((x/ GeneralSettings.squareSize+i-vision)* GeneralSettings.squareSize,
                        (y/ GeneralSettings.squareSize+j-vision)* GeneralSettings.squareSize,
                        GeneralSettings.squareSize,
                        GeneralSettings.squareSize);
            }
        }
    }

    //executes all steps below
    public void act(IGameEntity[][] grid){
        visionScan(grid); //step1
        FindMoveStrategy.produceMove(this);
        //produceMove(grid);//step2
        //doMove(grid);//step3
        FindMoveStrategy.doMove(this,grid);
        reduceEnergyDieReproduce(grid);//step4
    }

    //STEP 1--------------------------------------------
    //UPDATING VISION------------------------------------
    //--------------------------------------------------
    public void visionScan(IGameEntity[][] grid){
        //visionArray
        for(int x=-vision; x<=vision; x++)
        {
            for(int y=-vision; y<=vision; y++)
            {
                //its on the map (not out of bounds check)
                if((cx+x) >=0 && (cx+x) < GeneralSettings.gridSize && (cy+y)>=0 && (cy+y) < GeneralSettings.gridSize){
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



    //REPRODUCTION--------------------------------------------
    //--------------------------------------------------
    public void reduceEnergyDieReproduce(IGameEntity[][] grid){
        //reduce energy
        energy -= energyTurnCost;

        //check if dead die
        if(energy <=0){
            grid[cx][cy] = null;
            System.out.println("Dead");
        }

        //chance to reproduce
        if(ran.nextFloat() <= reproductionChance && energy > 0){
            reproduce(grid);
        }

    }
    public void reproduce(IGameEntity[][] grid){
        //copy the genes
        //place new creature//next to parent.

        //split energy in half, create a baby with same stats except energy divided in two (like cell division)
        int halfEnergy = (this.energy+1)/2;
        Creature baby = new Creature(halfEnergy,vision,energyTurnCost,reproductionChance,baseSpeed,color,type);
        this.energy = halfEnergy;

        //find some spot around it thats free
        Point a = findPointAroundMe(GridSpaceType.F);

        grid[a.x][a.y] = baby;
        baby.setCx(a.x);
        baby.setCy(a.y);

    }

    //helper functions--------------------------------------------
    //--------------------------------------------------
    //converts small vision coordinates to bigger grid coordinates
    private Point convertVisionPointToBigGridPoint(Point a){
        int x = a.x -vision + cx  ;
        int y= a.y -vision + cy;
        return new Point(x,y);
    }

    //finds a free point around you
    private Point findPointAroundMe(GridSpaceType t){
        int xx = vision;
        int yy = vision;

        if(visionArray[vision-1][vision] == t){ //left
                xx = vision-1;
                yy = vision;
        }
        else if (visionArray[vision+1][vision] == t) { //right
                xx = vision+1;
                yy = vision;
        }
        else if (visionArray[vision-1][vision-1] == t) { //leftdown
                xx = vision-1;
                yy = vision-1;
        }
        else if (visionArray[vision-1][vision+1] == t) { //leftup
                xx = vision-1;
                yy = vision+1;
        }
        else if (visionArray[vision][vision+1] == t) { //up
                xx = vision;
                yy = vision+1;
        }
        else if (visionArray[vision][vision-1] == t) { //down
                xx = vision;
                yy = vision-1;
        }
        else if (visionArray[vision+1][vision+1] == t) { //rightup
                xx = vision+1;
                yy = vision+1;
        }
        else if (visionArray[vision+1][vision-1] == t) { //rightdown
                xx = vision+1;
                yy = vision-1;
        }

        return convertVisionPointToBigGridPoint(new Point(xx,yy));
    }




    //getter and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getInitialEnergy() {
        return initialEnergy;
    }

    public void setInitialEnergy(int initialEnergy) {
        this.initialEnergy = initialEnergy;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getVision() {
        return vision;
    }

    public void setVision(int vision) {
        this.vision = vision;
    }

    public int getCx() {
        return cx;
    }

    public void setCx(int cx) {
        this.cx = cx;
    }

    public int getCy() {
        return cy;
    }

    public void setCy(int cy) {
        this.cy = cy;
    }

    public float getBaseSpeed() {
        return baseSpeed;
    }

    public void setBaseSpeed(float baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    public float getRealSpeed() {
        return realSpeed;
    }

    public void setRealSpeed(float realSpeed) {
        this.realSpeed = realSpeed;
    }

    public int getEnergyTurnCost() {
        return energyTurnCost;
    }

    public void setEnergyTurnCost(int energyTurnCost) {
        this.energyTurnCost = energyTurnCost;
    }

    public float getReproductionChance() {
        return reproductionChance;
    }

    public void setReproductionChance(float reproductionChance) {
        this.reproductionChance = reproductionChance;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public GridSpaceType[][] getVisionArray() {
        return visionArray;
    }

    public void setVisionArray(GridSpaceType[][] visionArray) {
        this.visionArray = visionArray;
    }

    public Random getRan() {
        return ran;
    }

    public void setRan(Random ran) {
        this.ran = ran;
    }

    public Point getNextMove() {
        return nextMove;
    }

    public void setNextMove(Point nextMove) {
        this.nextMove = nextMove;
    }
}
