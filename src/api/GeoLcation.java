package api;

public class GeoLcation implements geo_location {
    private double x,y,z;
    public GeoLcation() {
        this.x=0.0;
        this.y=0.0;
        this.z=0.0;
    }
    public GeoLcation(String pos){
            double x = Double.parseDouble(pos.substring(0, pos.indexOf(","))); // get x coordinate
            pos = pos.substring(pos.indexOf(",") + 1);
            double y = Double.parseDouble(pos.substring(0, pos.indexOf(","))); // get y coordinate
            pos = pos.substring(pos.indexOf(",") + 1); // get z coordinate
            double z = Double.parseDouble(pos.substring(0));
            this.x=x;
            this.y=y;
            this.z=z;
    }
    public GeoLcation(double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    
    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double distance(geo_location g) {
        return Math.sqrt(Math.pow(this.x-g.x(),2)+Math.pow(this.y-g.y(),2)+Math.pow(this.z-g.z(),2));
    }
}
