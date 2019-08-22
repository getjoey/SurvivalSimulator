package Entity;

import Settings.CreatureASettings;
import Settings.CreatureBSettings;

public class CreatureFactory {

    public CreatureFactory(){

    }

    public Creature makeCreature(String type){
        Creature c = new Creature();
        if(type.equals("A")){
            c.initializeCreature(CreatureASettings.initialEnergy,
                    CreatureASettings.vision,
                    CreatureASettings.energyTurnCost,
                    CreatureASettings.reproductionChance,
                    CreatureASettings.baseSpeed,
                    CreatureASettings.color);
        }
        if(type.equals("B")){
            c.initializeCreature(CreatureBSettings.initialEnergy,
                    CreatureBSettings.vision,
                    CreatureBSettings.energyTurnCost,
                    CreatureBSettings.reproductionChance,
                    CreatureBSettings.baseSpeed,
                    CreatureBSettings.color);
        }

        return c;
    }
}
