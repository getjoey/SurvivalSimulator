package GameLoop;

import Controllers.Controller;
import Settings.GeneralSettings;
import Settings.JSONrw;
import View.SimulationDrawingPane;


public class SimulatorLoop implements Runnable {

    private static SimulatorLoop instance = null;
    private Controller simulationController;
    private SimulationDrawingPane drawingPane;
    private int keyPressed = 0;
    private JSONrw dataReader;

    private SimulatorLoop(){
        simulationController = Controller.getInstance();
        dataReader = JSONrw.getInstance();
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
                    simulationController.resetGame();
                    break;
                }
                case 80:{
                    keyPressed = 0;
                    simulationController.printStatistics();
                    break;
                }
            }

                simulationController.sortCreaturesBySpeed();
                simulationController.doMoves();
                simulationController.spawnNewFoods();


            try {
                Thread.sleep(dataReader.getThreadTimer());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void setKeyPressed(int keyPressed) {
        this.keyPressed = keyPressed;
    }
}
