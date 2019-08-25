package GameLoop;

import Controllers.CreatureController;
import Settings.MapSettings;
import View.SimulationDrawingPane;


public class SimulatorLoop implements Runnable {

    private static SimulatorLoop instance = null;
    private CreatureController simulationCreatureController;
    private SimulationDrawingPane drawingPane;
    private int keyPressed = 0;

    private SimulatorLoop(){
        simulationCreatureController = CreatureController.getInstance();
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

            switch(keyPressed){
                case 32:{
                    keyPressed = 0;
                    simulationCreatureController.resetGame();
                    break;
                }
                case 80:{
                    keyPressed = 0;
                    simulationCreatureController.printStatistics();
                    break;
                }
            }

                simulationCreatureController.sortCreaturesBySpeed();
                simulationCreatureController.doMoves();
                simulationCreatureController.spawnNewFoods();


            try {
                Thread.sleep(MapSettings.GameLoopSleepTimer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void setKeyPressed(int keyPressed) {
        this.keyPressed = keyPressed;
    }
}
