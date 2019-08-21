import java.awt.*;
import java.util.ArrayList;

public class Creature implements IGameEntity {


    int energy;
    int vision; //vision is its range of sight, ie 5 would be 5 grid blocks in every direction



    public Creature(){
        initializeCreature();
    }

    private void initializeCreature(){
        energy = 100;
        vision = 3;

    }

    public void draw(Graphics g,int x , int y, int size){
        //g.fillRect(x,y,size,size);
        g.setColor(Color.BLUE);
        g.drawOval(x,y,size,size);
        g.setColor(Color.CYAN);
        g.fillOval(x,y,size,size);
    }

    public void move(IGameEntity[][] grid, int x, int y){

        //visionScan(grid,x,y);



    }

    /*
    private void visionScan(IGameEntity[][] grid, int x, int y){

        int visionArray[][] = getVisionArray(grid, x,  y);
        for(int i=0; i<visionArray.length;i++){
            for(int j=0; j<visionArray.length;j++){
                System.out.print(visionArray[i][j]+" ");
            }
            System.out.println();
        }

        //if priority is food, find nearest food
        //if smarter, it can determine if someone else will get to that food first
        //if aggressive it'll target someone and fight them, set them as location etc...


        //find closest food




        //if nothing found wander

    }
    */

    private int[][] getVisionArray(IGameEntity[][] grid, int cx, int cy){

        int visionArray[][] = new int[vision*2+1][vision*2+1]; //+1 because you are in 1 spot. so if vision is 3, you get back a 4x4
        /*
        for(int xx=-vision; xx<vision+1; xx++)
        {
            for(int yy=-vision; yy<vision+1; yy++)
            {
                if(cx+xx >=0 && cx+xx < Settings.gridSize && cy+yy>=0 & cy+yy<Settings.gridSize){ //its in grid
                    //check map
                    if(grid.get(cy+yy).get(cx+xx) instanceof Creature){
                        visionArray[yy+vision][xx+vision] = 2; //2 is a creature
                    }
                    else if(grid.get(cy+yy).get(cx+xx) instanceof Food){
                        visionArray[yy+vision][xx+vision] = 3; //3 it's food
                    }
                    else{
                        visionArray[yy+vision][xx+vision] = 4; //its an empty movable space
                    }

                }else{
                    //System.out.println((cx+xx)+" "+(cy+yy));
                    //System.out.println("B"+(xx+vision)+" "+(yy+vision));
                    visionArray[yy+vision][xx+vision] = 0; //not a possible location since its not on map
                }
            }
        }
        */

        /*
        for(int xx=-vision; xx<vision+1; xx++)
        {
            for(int yy=-vision; yy<vision+1; yy++)
            {
                if(cx+xx >=0 && cx+xx < Settings.gridSize && cy+yy>=0 & cy+yy<Settings.gridSize){ //its in grid
                    //check map
                    if(grid.get(cx+xx).get(cy+yy) instanceof Creature){
                        visionArray[xx+vision][yy+vision] = 2; //2 is a creature
                    }
                    else if(grid.get(cx+xx).get(cy+yy) instanceof Food){
                        visionArray[xx+vision][yy+vision] = 3; //3 it's food
                    }
                    else{
                        visionArray[xx+vision][yy+vision] = 4; //its an empty movable space
                    }

                }else{
                    //System.out.println((cx+xx)+" "+(cy+yy));
                    //System.out.println("B"+(xx+vision)+" "+(yy+vision));
                    visionArray[xx+vision][yy+vision] = 0; //not a possible location since its not on map
                }
            }
        }
        //put yourself as 1
        visionArray[vision][vision] = 1;
        */


        return visionArray;
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
