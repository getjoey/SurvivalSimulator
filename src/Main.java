import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {


        JFrame frame = new JFrame("Simulator");
        int size = Settings.gridSize*Settings.squareSize;
        frame.setSize(size+Settings.paneOffsetX*2,size+Settings.paneOffsetY*2); //square frame


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SimulationDrawingPane simulationDrawingPane = SimulationDrawingPane.getInstance();
        frame.getContentPane().add(simulationDrawingPane, BorderLayout.CENTER);

        frame.setVisible(true);

        //thread
        Thread thread = new Thread (new SimulatorLoop());
        thread.start();





        System.out.println("Terminated Succesfully");
    }
}
