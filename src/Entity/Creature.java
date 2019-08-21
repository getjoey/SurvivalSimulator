package Entity;

import Settings.Settings;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class Creature implements IGameEntity {

    int energy;
    int vision; //vision is its range of sight, ie 5 would be 5 grid blocks in every direction
    GridSpaceType visionArray[][];


    public Creature(){
        initializeCreature();
    }

    private void initializeCreature(){
        energy = 100;
        vision = 3;
        visionArray = new GridSpaceType[vision*2+1][vision*2+1];
    }

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

    public void move(IGameEntity[][] grid, int cx, int cy){  //cx and cy are current position in grid
        visionScan(grid,cx,cy);
    }

    //updates vision
    private void visionScan(IGameEntity[][] grid, int cx, int cy){

        //visionArray
        for(int x=-vision; x<=vision; x++)
        {
            for(int y=-vision; y<=vision; y++)
            {
                //its a valid grid space
                if((cx+x) >=0 && (cx+x) < Settings.gridSize && (cy+y)>=0 && (cy+y) < Settings.gridSize){
                    if(grid[cx+x][cy+y] instanceof Creature){
                        visionArray[x+vision][y+vision] = GridSpaceType.C;
                    }else if(grid[cx+x][cy+y] instanceof Food){
                        visionArray[x+vision][y+vision] = GridSpaceType.F;
                    }else{
                        visionArray[x+vision][y+vision] = GridSpaceType.W;
                    }
                }
                else{ //off the map
                    visionArray[x+vision][y+vision] = GridSpaceType.N;
                }
                //grid[cx+x][cy+y]

            }
        }
        visionArray[vision][vision] = GridSpaceType.S; //self is in middle of grid always
        System.out.println();


        //debug print
        for(int i=0; i<visionArray.length;i++){
            for(int j=0; j<visionArray.length;j++){
                //System.out.print(visionArray[i][j]+" ");
            }
            //System.out.println();
        }

        //if priority is food, find nearest food
        //if smarter, it can determine if someone else will get to that food first
        //if aggressive it'll target someone and fight them, set them as location etc...
        //find closest food
        //if nothing found wander

    }

    private Point getNextPosition(Point s, Point d){

        int x= s.x;
        int y= s.y;

        if(d.x > s.x ){
            x++;
        }else if(d.x > s.y){
            x--;
        }

        if(d.y > s.y ){
            y++;
        }else if(d.y > s.y){
            y--;
        }

        return new Point(x,y);
    }



}
