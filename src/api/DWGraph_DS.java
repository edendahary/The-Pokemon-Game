package api;

import java.util.Collection;
import java.util.HashMap;

public class  DWGraph_DS implements directed_weighted_graph{
    private int ES=0;
    private int MC=0;
    HashMap<node_data,HashMap<Integer,edge_data>>Graph;
    HashMap<Integer,node_data>MapNode;

    public DWGraph_DS(){
        MapNode = new HashMap<>();
        Graph = new HashMap<>();
    }
    @Override
    public node_data getNode(int key) {
        return this.MapNode.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if(src == dest){
            return null;
        }
        if(this.Graph.containsKey(this.MapNode.get(src))==false){
            return null;
        }
        return this.Graph.get(this.MapNode.get(src)).get(dest);
    }

    @Override
    public void addNode(node_data n) {
        this.MapNode.put(n.getKey(),n);
    }

    @Override
    public void connect(int src, int dest, double w) {
        if(src == dest){
            return;
        }
        if(this.MapNode.containsKey(src) && this.MapNode.containsKey(dest)) {
            if (this.Graph.containsKey(this.MapNode.get(src))){
                if(!this.Graph.get(this.MapNode.get(src)).containsKey(dest)){
                    edge_data NewEdgeWeight = new EdgeData(src, dest, w);
                    this.Graph.get(this.MapNode.get(src)).put(dest,NewEdgeWeight);
                    ES++;
                }else{
                    edge_data NewEdgeWeight = new EdgeData(src, dest, w);
                    this.Graph.get(this.MapNode.get(src)).replace(dest,NewEdgeWeight);
                }
            }else {
                    HashMap<Integer, edge_data> t = new HashMap<>();
                    edge_data e = new EdgeData(src, dest, w);
                    t.put(dest, e);
                    this.Graph.put(this.MapNode.get(src), t);
                    ES++;
                }
            }
        }



    @Override
    public Collection<node_data> getV() {
        return this.MapNode.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        if(this.MapNode.containsKey(node_id) && this.Graph.containsKey(this.MapNode.get(node_id))) {
            return this.Graph.get(this.MapNode.get(node_id)).values();
        }
        return null;
    }

    @Override
    public node_data removeNode(int key) {
        node_data NodeDataToRemove = this.MapNode.get(key);
        for(node_data curr : this.Graph.keySet()){
            if(this.Graph.get(curr).containsKey(key)){
                this.Graph.get(curr).remove(key);
                ES--;
            }
        }
        if(this.Graph.containsKey(NodeDataToRemove)) {
            ES -= this.Graph.get(NodeDataToRemove).size();
            this.Graph.remove(NodeDataToRemove);
        }
        this.MapNode.remove(key);
        return NodeDataToRemove;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        if(src == dest ){
            return null;
        }
        edge_data e =this.Graph.get(this.MapNode.get(src)).get(dest);
        if(e == null){
            return null;
        }
        this.Graph.get(this.MapNode.get(src)).remove(dest);
        ES--;
        return e;
    }

    @Override
    public int nodeSize() {
        return this.MapNode.size();
    }

    @Override
    public int edgeSize() {
        return ES;
    }

    @Override
    public int getMC() {
        return MC;
    }
}
