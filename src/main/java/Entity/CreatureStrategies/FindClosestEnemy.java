package Entity.CreatureStrategies;

import Entity.Creature;
import Entity.Food;
import Entity.GridSpaceType;
import Entity.IGameEntity;

import java.awt.*;

public class FindClosestEnemy extends FindMoveStrategy {

    @Override
    public void produceMove(Creature creature) {
        Point p = findNearestDesiredGridSpaceInVision(creature, GridSpaceType.C); //find nearest food (omnivore type)
        creature.setNextMove(getNextPosition( new Point(creature.getCx(),creature.getCy()),p));
    }

    @Override
    public void doMove(Creature creature, IGameEntity[][] grid) {

        if(grid[creature.getNextMove().x][creature.getNextMove().y] instanceof Creature){
            //eat creature if not your type
            Creature enemy = (Creature)grid[creature.getNextMove().x][creature.getNextMove().y];
            if(!enemy.getType().equals(creature.getType())){
                creature.setEnergy(creature.getEnergy()+enemy.getEnergy());
                System.out.println(creature.getEnergy());
            }

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
