package leapmotionship;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeapMotionShip 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Semaphore semaphore = new Semaphore(0);
        try
        {
            Database db = new Database(semaphore);
            db.connect("mongodb://hackuser:hackuser@csdm-mongodb.rgu.ac.uk/hackais");
            ArrayList<Vessel> vessels = db.getAllVessels();
            semaphore.acquire();
            System.out.println(vessels.size());
            /*
            for (Vessel vessel : vessels)
            {
                System.out.println(vessel);
            }*/
            db.disconnect();
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(LeapMotionShip.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
