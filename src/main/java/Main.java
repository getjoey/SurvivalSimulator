import GameLoop.SimulatorLoop;
import Settings.JSONrw;
import View.AnalyticsDisplayFrame;
import View.AnalyticsPanel;
import View.SimulationDrawingPane;
import View.SimulationMainFrame;


public class Main {

    public static void main(String[] args) {


        JSONrw.readConfigData(); //initialize data
        //Gui
        SimulationMainFrame simulationMainFrame = new SimulationMainFrame();
        AnalyticsDisplayFrame analyticsDisplayFrame = new AnalyticsDisplayFrame();

        //thread -drawing
        Thread threadD = new Thread(SimulationDrawingPane.getInstance());
        threadD.start();

        Thread threadA = new Thread(AnalyticsPanel.getInstance());
        threadA.start();

        //thread -Gameloop
        Thread threadGL = new Thread (SimulatorLoop.getInstance());
        threadGL.start();



    }
}
