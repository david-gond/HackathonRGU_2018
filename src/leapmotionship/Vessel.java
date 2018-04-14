package leapmotionship;

import org.bson.types.ObjectId;

public class Vessel
{

    private ObjectId id;
    private String callSign;
    private String name;
    private int shipType;
    private int dimension_A;
    private int dimension_B;
    private int dimension_C;
    private int dimension_D;
    private int mmsi;
    private int count;
    private int length;
    private int width;
    private double latitude;
    private double longitude;
    
    public Vessel()
    {
        this.id = new ObjectId("None");
        this.callSign = "None";
        this.name = "None";
        this.shipType = 0;
        this.dimension_A = 0;
        this.dimension_B = 0;
        this.dimension_C = 0;
        this.dimension_D = 0;
        this.mmsi = 0;
        this.count = 0;
        this.length = 0;
        this.width = 0;  
        this.latitude = 0;
        this.longitude = 0;
    }

    public Vessel(ObjectId id, String callSign, String name, int shipType, int dimension_A, int dimension_B, int dimension_C, int dimension_D, int mmsi, int count, int length, int width, double latitude, double longitude)
    {
        this.id = id;
        this.callSign = callSign;
        this.name = name;
        this.shipType = shipType;
        this.dimension_A = dimension_A;
        this.dimension_B = dimension_B;
        this.dimension_C = dimension_C;
        this.dimension_D = dimension_D;
        this.mmsi = mmsi;
        this.count = count;
        this.length = length;
        this.width = width;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        this.id = id;
    }

    public String getCallSign()
    {
        return callSign;
    }

    public void setCallSign(String callSign)
    {
        this.callSign = callSign;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getShipType()
    {
        return shipType;
    }

    public void setShipType(int shipType)
    {
        this.shipType = shipType;
    }

    public int getDimension_A()
    {
        return dimension_A;
    }

    public void setDimension_A(int dimension_A)
    {
        this.dimension_A = dimension_A;
    }

    public int getDimension_B()
    {
        return dimension_B;
    }

    public void setDimension_B(int dimension_B)
    {
        this.dimension_B = dimension_B;
    }

    public int getDimension_C()
    {
        return dimension_C;
    }

    public void setDimension_C(int dimension_C)
    {
        this.dimension_C = dimension_C;
    }

    public int getDimension_D()
    {
        return dimension_D;
    }

    public void setDimension_D(int dimension_D)
    {
        this.dimension_D = dimension_D;
    }

    public int getMmsi()
    {
        return mmsi;
    }

    public void setMmsi(int mmsi)
    {
        this.mmsi = mmsi;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
    
    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }
    
    @Override
    public String toString()
    {
        String infos = "{";
        infos += " _id: ObjectId(" + id + ")\n";
        infos += "  CallSign: " + callSign + '\n';
        infos += "  Name: " + name + '\n';
        infos += "  ShipType: " + shipType + '\n';
        infos += "  Dimension_A: " + dimension_A + '\n';
        infos += "  Dimension_B: " + dimension_B + '\n';
        infos += "  Dimension_C: " + dimension_C + '\n';
        infos += "  Dimension_D: " + dimension_D + '\n';
        infos += "  MMSI: " + mmsi + '\n';
        infos += "  count: " + count + '\n';
        infos += "  length: " + length + '\n';
        infos += "  width: " + width + '\n';
        infos += "  latitude: " + latitude + '\n';
        infos += "  longitude: " + longitude + '\n';
        infos += "}\n";
        return infos;
    }
}
