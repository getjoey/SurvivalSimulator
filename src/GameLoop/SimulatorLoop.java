package GameLoop;

import Controllers.Controller;
import Settings.Settings;
import View.SimulationDrawingPane;


public class SimulatorLoop implements Runnable {

    private static SimulatorLoop instance = null;
    private Controller simulationController;
    private SimulationDrawingPane drawingPane;
    private int keyPressed = 0;

    private SimulatorLoop(){
        simulationController = Controller.getInstance();
        //drawingPane = SimulationDrawingPane.getInstance();
    }

    public static synchronized SimulatorLoop getInstance(){
        if(instance == null){
            instance = new SimulatorLoop();
        }
        return instance;
    }

    @Override
    public void run() {

        while(true){


            if(keyPressed == 32){
                keyPressed = 0;
                simulationController.resetGame();
            }
            else{
                simulationController.getMoves();
                simulationController.doMoves();
                simulationController.spawnNewFoods();
            }

            try {
                Thread.sleep(Settings.GameLoopSleepTimer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void setKeyPressed(int keyPressed) {
        this.keyPressed = keyPressed;
    }
}
