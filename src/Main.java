import GameLoop.SimulatorLoop;
import Settings.Settings;
import View.SimulationDrawingPane;
import View.SimulationMainFrame;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        //Gui
        SimulationMainFrame View = new SimulationMainFrame();

        //thread -drawing
        Thread threadD = new Thread(SimulationDrawingPane.getInstance());
        threadD.start();

        //thread -Gameloop
        Thread threadGL = new Thread (new SimulatorLoop());
        threadGL.start();
    }
}
