import java.util.ArrayList;

public class Controller {

    static Controller instance = null;
    Map map;
    int creatureAmount = 100;
    int foodAmount = 200;


    private Controller( )
    {
        map = new Map(foodAmount , creatureAmount);
    }

    public static synchronized Controller getInstance(){
        if(instance ==null){
            instance = new Controller();
        }
      return instance;
    }



    //each frame this is called
    public void move(){
        IGameEntity[][] grid = map.getGrid();

        for (int x = 0; x < Settings.gridSize; x++) {
            for (int y = 0; y < Settings.gridSize; y++) {
                if(grid[x][y] != null){
                    if(grid[x][y] instanceof Creature){
                        ((Creature)grid[x][y]).move(grid,x,y);
                    }
                }

            }
        }
        /*
        int x=0;
        int y=0;
        for(ArrayList<IGameEntity> col : map.getGrid())
        {
            for(IGameEntity ge: col)
            {
                if(ge instanceof Creature)
                {
                    System.out.println("x"+x+","+y);
                    ((Creature) ge).move(map.getGrid(),x,y);
                    System.out.println();
                }
                x++;
            }
            x=0;
            y++;
        }
        */
    }




    public Map getMap() {
        return map;
    }
}
