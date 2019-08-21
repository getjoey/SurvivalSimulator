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

        //thread -Gameloop (handles drawing to SimulationDrawingPane and gameloop ticks)
        Thread thread = new Thread (new SimulatorLoop());
        thread.start();
    }
}
