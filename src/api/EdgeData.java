package api;

public class EdgeData implements edge_data {
    private int src, dest, tag;
    private double weight;
    private String info;

    public EdgeData() {
    }
    public EdgeData(int srcKey,double weight,int dest){
        this.src=srcKey;
        this.weight=weight;
        this.dest=dest;
    }

    public EdgeData(int srcKey, int destKey, double weight) {
        this.src = srcKey;
        this.dest = destKey;
        this.weight = weight;
    }

    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
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


