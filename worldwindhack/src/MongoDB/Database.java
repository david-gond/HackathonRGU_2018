package MongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.async.client.*;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import org.bson.Document;
import org.bson.types.ObjectId;

public class Database
{

    private final Semaphore semaphore;
    private MongoClient mongoClient = null;
    private MongoDatabase database = null;

    public Database(Semaphore semaphore)
    {
        this.semaphore = semaphore;
    }

    public void connect(String host)
    {
        mongoClient = MongoClients.create(host);
        database = mongoClient.getDatabase("hackais");
    }

    public void disconnect()
    {
        mongoClient.close();
    }

    public MongoCollection getAllDBVessels()
    {
        return database.getCollection("vessels");
    }

    public MongoCollection getAllDBPositions()
    {
        return database.getCollection("positions");
    }

    public MongoCollection getAllDBTowns()
    {
        return database.getCollection("towns");
    }

    public ArrayList<Vessel> getAllVessels()
    {
        ArrayList<Vessel> vessels = new ArrayList<>();
        MongoCollection dbVessels = getAllDBVessels();
        MongoCollection dbPositions = getAllDBPositions();
        FindIterable<Document> it = dbVessels.find();
        it.forEach((final Document document) ->
        {
            ObjectId id = document.getObjectId("_id");
            String callSign = document.getString("CallSign");
            String name = document.getString("Name");
            int shipType = document.getInteger("ShipType");
            int dimension_A = document.getInteger("Dimension_A");
            int dimension_B = document.getInteger("Dimension_B");
            int dimension_C = document.getInteger("Dimension_C");
            int dimension_D = document.getInteger("Dimension_D");
            int mmsi = document.getInteger("MMSI");
            int count = document.getInteger("count");
            int length = document.getInteger("length");
            int width = document.getInteger("width");
            double latitude = 0.0;
            double longitude = 0.0;

            Vessel vessel = new Vessel(id, callSign, name, shipType,
                    dimension_A, dimension_B, dimension_C, dimension_D,
                    mmsi, count, length, width, latitude, longitude
            );

            // Get the last known position of each vessel
            dbPositions.find(eq("MMSI", mmsi))
                    .sort(new BasicDBObject("RecvTime", -1))
                    .first((Object t, Throwable thrwbl) ->
                    {
                        if (t != null)
                        {
                            Document document1 = (Document) t;
                            /* As some coordinates can be at 0, they are considered as 
                               Integer and so it can happen a ClassCastException when
                               trying to cast to Double. One solution is as below, 
                               by considering explicitly as an Integer each value at 0.
                             */
                            List<Double> position = (List<Double>) document1.get("location");
                            try
                            {
                                vessel.setLatitude(position.get(0)); // Avoid Cast Exception
                            }
                            catch (ClassCastException e)
                            {
                                List<Integer> positionTemp
                                        = (List<Integer>) document1.get("location");
                                vessel.setLatitude(positionTemp.get(0).doubleValue());
                            }

                            try
                            {
                                vessel.setLongitude(position.get(1)); // Avoid Cast Exception
                            }
                            catch (ClassCastException e)
                            {
                                List<Integer> positionTemp
                                        = (List<Integer>) document1.get("location");
                                vessel.setLongitude(positionTemp.get(1).doubleValue());
                            }
                        }
                    });
            vessels.add(vessel);
        }, (final Void result, final Throwable t) ->
        {
            System.out.println("All vessels added!");
            semaphore.release();
        });
        return vessels;

    }
}
