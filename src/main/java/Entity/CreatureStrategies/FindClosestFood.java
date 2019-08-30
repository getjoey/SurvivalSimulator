package Entity.CreatureStrategies;

import Entity.Creature;
import Entity.Food;
import Entity.GridSpaceType;
import Entity.IGameEntity;

import java.awt.*;

public class FindClosestFood extends FindMoveStrategy {


    //return point to nearest food
    @Override
    public void produceMove(Creature creature) {
        Point p = findNearestDesiredGridSpaceInVision(creature, GridSpaceType.F); //find nearest food (omnivore type)
        creature.setNextMove(getNextPosition( new Point(creature.getCx(),creature.getCy()),p));
    }

    @Override
    public void doMove(Creature creature, IGameEntity[][] grid) {
        //eat food
        if(grid[creature.getNextMove().x][creature.getNextMove().y] instanceof Food){
            //absorb foods energy, remove food
            creature.setEnergy(creature.getEnergy()+((Food)grid[creature.getNextMove().x][creature.getNextMove().y]).getEnergyGiven());
        }
        //move location
        changeLocationOnGrid(creature, grid);
    }


    protected void changeLocationOnGrid(Creature creature, IGameEntity[][] grid){
        grid[creature.getNextMove().x][creature.getNextMove().y] = null;
        grid[creature.getNextMove().x][creature.getNextMove().y] = creature;
        grid[creature.getCx()][creature.getCy()] = null;
        creature.setCx(creature.getNextMove().x);
        creature.setCy(creature.getNextMove().y);
    }


}
