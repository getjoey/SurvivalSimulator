package View;

import Controllers.Controller;
import Settings.JSONrw;

import javax.swing.*;
import java.awt.*;

public class AnalyticsPanel extends JPanel implements Runnable{

    public static AnalyticsPanel instance = null;
    private Controller simulationController;
    private JSONrw dataReader;

    private AnalyticsPanel(){
        simulationController = Controller.getInstance();
        dataReader = JSONrw.getInstance();
    }

    public synchronized static AnalyticsPanel getInstance(){
        if(instance==null){
            instance = new AnalyticsPanel();
        }
        return instance;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int amountA = simulationController.getAmountA();
        int amountB = simulationController.getAmountB();
        int total = amountA+amountB+1;
        double perA = ((double)amountA/(double)total)*100;
        double perB = ((double)amountB/(double)total)*100;
        //System.out.println(perA+","+perB);


        //draw A
        g.setColor(dataReader.getColorA());
        g.fillRect(10,10,((int)perA)*2,40);
        g.setColor(Color.black);
        g.drawString("A",3,15);

        //draw B
        g.setColor(dataReader.getColorB());
        g.fillRect(10,60,((int)perB)*2,40);
        g.setColor(Color.black);
        g.drawString("B",3,65);

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
