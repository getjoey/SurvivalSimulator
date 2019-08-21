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
            simulationController.move();
        }

    }

}
