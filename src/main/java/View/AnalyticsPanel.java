package View;

import Controllers.GridMapController;
import Settings.JSONrw;

import javax.swing.*;
import java.awt.*;

public class AnalyticsPanel extends JPanel implements Runnable{

    public static AnalyticsPanel instance = null;
    private GridMapController simulationGridMapController;

    private AnalyticsPanel(){
        simulationGridMapController = GridMapController.getInstance();
    }

    public synchronized static AnalyticsPanel getInstance(){
        if(instance==null){
            instance = new AnalyticsPanel();
        }
        return instance;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int amountA = simulationGridMapController.getAmountA();
        int amountB = simulationGridMapController.getAmountB();
        int total = amountA+amountB+1;
        double perA = ((double)amountA/(double)total)*100;
        double perB = ((double)amountB/(double)total)*100;
        //System.out.println(perA+","+perB);
        int offSet =0;
        if((int)perA ==0 || (int)perB ==0){
            offSet=1;
        }

        //draw A
        g.setColor(JSONrw.getColorA());
        g.fillRect(10,10,(((int)perA)*2)+offSet,40);
        g.setColor(Color.black);
        g.drawString("A",0,15);

        //draw B
        g.setColor(JSONrw.getColorB());
        g.fillRect(10,60,(((int)perB)*2)+offSet,40);
        g.setColor(Color.black);
        g.drawString("B",0,65);

        g.drawString("Total Creatures = "+total,200,150);

    }



    @Override
    public void run() {
        while(true){
            this.repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
