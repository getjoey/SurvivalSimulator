package Entity.CreatureStrategies;

import Entity.Creature;
import Entity.GridSpaceType;
import Entity.IGameEntity;
import Settings.GeneralSettings;

import java.awt.*;
import java.util.Random;

public abstract class FindMoveStrategy {


    public abstract void produceMove(Creature creature);
    public abstract void doMove(Creature creature, IGameEntity[][] grid);
    protected abstract void changeLocationOnGrid(Creature creature, IGameEntity[][] grid);

    public Point convertVisionPointToBigGridPoint(Creature creature, Point a){
        int x = a.x -creature.getVision() + creature.getCx()  ;
        int y= a.y -creature.getVision() + creature.getCy();
        return new Point(x,y);
    }

    protected Point getNextPosition(Point s, Point d) {
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
        //doesnt work fully, may be a creature there
        return new Point(x,y);
    }

    protected Point findNearestDesiredGridSpaceInVision(Creature creature, GridSpaceType desired) {
        int x=0;
        int y=0;
        Random ran = new Random();

        double dist = 0; //0 means you, if this is final result, then no desired item/food wtv has been found
        double temp;

        //your pos is in middle of vision array
        for(int i=0; i<creature.getVisionArray().length; i++)
        {
            for(int j=0; j<creature.getVisionArray().length; j++)
            {
                if(creature.getVisionArray()[i][j] == desired){
                    temp = Math.sqrt( Math.pow(Math.abs(creature.getVision()-i),2) +  Math.pow(Math.abs(creature.getVision()-j),2));
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
            return new Point(ran.nextInt(GeneralSettings.gridSize), ran.nextInt(GeneralSettings.gridSize)); //wander randomly
            //return new Point(MapSettings.gridSize/2, MapSettings.gridSize/2); //go to middle
            //return new Point(cx,cy); //do nothing
        }

        return convertVisionPointToBigGridPoint(creature, new Point(x,y));
    }




}
