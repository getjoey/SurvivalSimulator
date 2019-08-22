import GameLoop.SimulatorLoop;
import View.SimulationDrawingPane;
import View.SimulationMainFrame;

public class Main {

    public static void main(String[] args) {

        //Gui
        SimulationMainFrame View = new SimulationMainFrame();

        //thread -drawing
        Thread threadD = new Thread(SimulationDrawingPane.getInstance());
        threadD.start();

        //thread -Gameloop
        Thread threadGL = new Thread (SimulatorLoop.getInstance());
        threadGL.start();


    }
}
