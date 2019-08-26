
import Entity.Creature;
import Entity.CreatureFactory;
import org.junit.Test;

public class test1 {

    @Test
    public void testA() throws Exception{
        CreatureFactory cf = new CreatureFactory();
        Creature a = cf.makeCreature("A");

        assert a.getType().equals("A");
    }

    @Test
    public void baby() throws Exception{
        int energy = 20+1;
        int energyBB = energy/2;
        energy = energy/2;
        System.out.println(energy);
        System.out.println(energyBB);


    }
}
