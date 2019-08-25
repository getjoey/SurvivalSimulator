package Settings;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONrw {

    public JSONObject readConfigData(){
        JSONParser jsonParser = new JSONParser();
        JSONObject configData = null;

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
        JSONObject configData = readConfigData();
        JSONObject creatures = (JSONObject) configData.get("creatures");
        JSONObject a = (JSONObject) creatures.get("A");
        return a;
    }

    public JSONObject getCreatureB(){
        JSONObject configData = readConfigData();
        JSONObject creatures = (JSONObject) configData.get("creatures");
        JSONObject b = (JSONObject) creatures.get("B");
        return b;
    }



}
