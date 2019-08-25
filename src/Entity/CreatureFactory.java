package Entity;

import Settings.CreatureASettings;
import Settings.CreatureBSettings;

public class CreatureFactory {

    public CreatureFactory(){

    }

    public Creature makeCreature(String type){

        Creature c = null;
        if(type.equals("A")){
            c = new Creature(CreatureASettings.initialEnergy,
                    CreatureASettings.vision,
                    CreatureASettings.energyTurnCost,
                    CreatureASettings.reproductionChance,
                    CreatureASettings.baseSpeed,
                    CreatureASettings.color,
                    "A");

        }
        else if(type.equals("B")){
            c = new Creature(CreatureBSettings.initialEnergy,
                    CreatureBSettings.vision,
                    CreatureBSettings.energyTurnCost,
                    CreatureBSettings.reproductionChance,
                    CreatureBSettings.baseSpeed,
                    CreatureBSettings.color,
                    "B");

        }

        return c;
    }
}
