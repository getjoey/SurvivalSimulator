package View;

import javax.swing.*;
import java.awt.*;

public class AnalyticsDisplayFrame  extends JFrame {

    public AnalyticsDisplayFrame(){
        this.setTitle("Analytics");
        this.setSize(400,300); //square frame
        this.setLocationRelativeTo(null);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AnalyticsPanel pane = AnalyticsPanel.getInstance();
        this.getContentPane().add(pane, BorderLayout.CENTER);
        this.setVisible(true);
    }


}
