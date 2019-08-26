package Settings;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONrw {

    public static JSONrw instance = null;
    private JSONObject configData;
    private JSONrw(){
        configData = readConfigData();
    }

    public synchronized static JSONrw getInstance(){
        if(instance == null){
            instance = new JSONrw();
        }
        return instance;
    }

    public JSONObject readConfigData(){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("Config.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            configData = (JSONObject) obj;

            //parse and do it


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return configData;
    }

    public JSONObject getCreatureA(){
        JSONObject creatures = (JSONObject) configData.get("creatures");
        JSONObject a = (JSONObject) creatures.get("A");
        return a;
    }

    public JSONObject getCreatureB(){
        JSONObject creatures = (JSONObject) configData.get("creatures");
        JSONObject b = (JSONObject) creatures.get("B");
        return b;
    }

    public JSONObject getMapSettings(){
        JSONObject mapSettings = (JSONObject) configData.get("map");
        return mapSettings;
    }

    //updates when requested, unlike others that only update configData on reset (spacebar)
    public int getThreadTimer(){
        JSONParser jsonParser = new JSONParser();
        JSONObject configDataLive = null;
        int threadTimer =  100;

        try (FileReader reader = new FileReader("Config.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            configDataLive = (JSONObject) obj;
            threadTimer =  Integer.parseInt(configDataLive.get("threadTimer").toString());
        }catch(Exception e){
            threadTimer = Integer.parseInt(configData.get("threadTimer").toString());
        }

        return threadTimer;
    }


    public Color getColorA(){
        JSONObject creatures = (JSONObject) configData.get("creatures");
        JSONObject a = (JSONObject) creatures.get("A");
        String color = a.get("color").toString();

        return hex2Rgb(color);
    }
    public Color getColorB(){
        JSONObject creatures = (JSONObject) configData.get("creatures");
        JSONObject b = (JSONObject) creatures.get("B");
        String color = b.get("color").toString();

        return hex2Rgb(color);
    }


    public Color hex2Rgb(String colorStr) {
        //System.out.println(colorStr);
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }



}
