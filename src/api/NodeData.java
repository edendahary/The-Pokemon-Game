package api;

public class NodeData implements node_data{
    private int key,tag;
    private geo_location g;
    private double weight;
    private String info;
    private static int k=0;
    public NodeData(){
        this.key=k++;
        g= new GeoLcation();
    }
    public NodeData(int key){
        this.key = key;
        this.tag = 0;
        this.weight = 0;
    }

    public NodeData(geo_location p, int id) {
        this.key=id;
        this.g=p;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public geo_location getLocation() {
        return this.g;
    }

    @Override
    public void setLocation(geo_location p) {
        this.g = p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight=w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info=s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
