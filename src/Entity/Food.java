package Entity;

import Entity.IGameEntity;

import java.awt.*;

public class Food implements IGameEntity {

    int energyGiven;

    public Food(){

        energyGiven = 10;
    }


    @Override
    public void draw(Graphics g,int x , int y, int size) {
        g.setColor(Color.green);
        g.fillRect(x,y,size,size);
    }
}
