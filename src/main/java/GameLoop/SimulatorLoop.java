package GameLoop;

import Controllers.GridMapController;
import Settings.JSONrw;


public class SimulatorLoop implements Runnable {

    private static SimulatorLoop instance = null;
    private GridMapController simulationGridMapController;
    private int keyPressed = 0;
    private JSONrw dataReader;

    private SimulatorLoop(){
        simulationGridMapController = GridMapController.getInstance();
        dataReader = JSONrw.getInstance();
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
                    simulationGridMapController.resetGame();
                    break;
                }
                case 80:{
                    keyPressed = 0;
                    simulationGridMapController.printStatistics();
                    break;
                }
            }

                simulationGridMapController.sortCreaturesBySpeed();
                simulationGridMapController.doMoves();
                simulationGridMapController.spawnNewFoods();


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
