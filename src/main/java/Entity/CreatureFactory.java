package Entity;

import Settings.CreatureASettings;
import Settings.CreatureBSettings;
import Settings.JSONrw;
import org.json.simple.JSONObject;

import java.awt.*;

public class CreatureFactory {

    JSONrw dataReader;
    public CreatureFactory(){
        dataReader = new JSONrw();
    }

    public Creature makeCreature(String type){

        JSONObject cData = null;
        Creature c = null;

        if(type.equals("A")){
            cData =  dataReader.getCreatureA();
        }
        else if(type.equals("B")){
            cData =  dataReader.getCreatureB();
        }

        int ie = Integer.parseInt(cData.get("initialEnergy").toString());
        int v = Integer.parseInt(cData.get("vision").toString());
        int etc = Integer.parseInt(cData.get("energyTurnCost").toString());
        float rc = Float.parseFloat(cData.get("reproductionChance").toString());
        float bs = Float.parseFloat(cData.get("baseSpeed").toString());
        String hexColor = (cData.get("color").toString());
        Color color = hex2Rgb(hexColor);

        c = new Creature(ie, v, etc, rc, bs, color, type);

        return c;
    }

    public Color hex2Rgb(String colorStr) {
        System.out.println(colorStr);

        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }
}
