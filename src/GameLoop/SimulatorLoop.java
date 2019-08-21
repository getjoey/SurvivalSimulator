package GameLoop;

import Controllers.Controller;
import Settings.Settings;
import View.SimulationDrawingPane;

public class SimulatorLoop implements Runnable {

    Controller simulationController;
    SimulationDrawingPane drawingPane;

    public SimulatorLoop(){
        simulationController = Controller.getInstance();
        drawingPane = SimulationDrawingPane.getInstance();
    }

    @Override
    public void run() {

        while(true){
            drawingPane.repaint();
            simulationController.getMoves();
            simulationController.doMoves();

            try {
                Thread.sleep(Settings.GameLoopSleepTimer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
