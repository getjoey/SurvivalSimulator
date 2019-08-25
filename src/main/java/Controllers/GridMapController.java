package Controllers;


import Settings.JSONrw;
import org.json.simple.JSONObject;

//reads JSON config
public class GridMapController {

    private JSONObject configData;

    public GridMapController(){
        configData = new JSONrw().readConfigData();
        System.out.println(configData);
    }


}
