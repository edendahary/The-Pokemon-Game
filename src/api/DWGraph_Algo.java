package api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class DWGraph_Algo implements dw_graph_algorithms {
    private directed_weighted_graph graph;
    private static class DijkstraResult {
        private final double[] dist;
        private final node_data[] prev;

        public DijkstraResult(double[] dist, node_data[] prev) {
            this.dist = dist;
            this.prev = prev;
        }

        public double[] getDist() {
            return dist;
        }

        public node_data[] getPrev() {
            return prev;
        }
    }

    @Override
    public void init(directed_weighted_graph g) {
        this.graph=g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.graph;
    }

    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph g = new DWGraph_DS();
        //copy all the nodes and edges in the graph

        for(node_data nodeData : this.graph.getV()) {
            node_data node = new NodeData(nodeData.getKey());
            g.addNode(node);
            Collection<edge_data> edges = g.getE(nodeData.getKey());

            for(edge_data edgeData : edges) {
                g.connect(nodeData.getKey(), edgeData.getDest(), edgeData.getWeight());
            }
        }

        return g;
    }

    @Override
    public boolean isConnected() {
        for(node_data nodeData : graph.getV()) {
            node_data[] prevs = dijkstra(nodeData.getKey()).getPrev();

            for(int i = 0; i < prevs.length; i++) {
                if(nodeData.getTag() != i) { // not in cell of current source  - nodeData
                    if(prevs[i] == null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        double[] distances = dijkstra(src).getDist();
        return distances[this.graph.getNode(dest).getTag()];
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {

        node_data[] prev = dijkstra(src).getPrev();
        if(dest == -1){
            return null;
        }
        if(prev[this.graph.getNode(dest).getTag()] == null) {
            return null;
        } else {

            List<node_data> path = new ArrayList<>();
            node_data curNode = this.graph.getNode(dest);

            while(curNode != this.graph.getNode(src)) {
                path.add(0, curNode);
                curNode = prev[curNode.getTag()];
            }

            path.add(0, curNode);

            return path;
        }
    }

    @Override
    public boolean save(String file)throws JSONException {
            JSONObject Graph=new JSONObject();
            JSONArray Edges=new JSONArray();
            JSONArray Nodes =new JSONArray();

            for(node_data nodeData:getGraph().getV()) {
                JSONObject node=new JSONObject();
                String pos=""+nodeData.getLocation().x()+","+nodeData.getLocation().y()+","+nodeData.getLocation().z();
                node.put("pos", pos);
                node.put("id", nodeData.getKey());
                Nodes.put(node);
                if(getGraph().getE(nodeData.getKey())!= null){
                for(edge_data edge_:getGraph().getE(nodeData.getKey())) {
                    JSONObject edge=new JSONObject();
                    edge.put("src", edge_.getSrc());
                    edge.put("w", edge_.getWeight());
                    edge.put("dest", edge_.getDest());
                    Edges.put(edge);
                }
            }
            }
            Graph.put("Edges",Edges);
            Graph.put("Nodes",Nodes);
            try {
                FileWriter file_ = new FileWriter(file);
                file_.write(Graph.toString());
                file_.flush();
                file_.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

    @Override
    public boolean load(String file) {
        directed_weighted_graph g = new DWGraph_DS();

        try {
//            Scanner scanner = new Scanner(new File(file));
//            String jsonString = scanner.useDelimiter("\\A").next();
//            scanner.close();
            JSONObject obj_JsonObject = new JSONObject(file);
            JSONArray jsonArrayEdges = obj_JsonObject.getJSONArray("Edges"); // Array for the vertexes
            JSONArray jsonArrayNodes = obj_JsonObject.getJSONArray("Nodes");// Array for the edges
            for (int i = 0; i < jsonArrayNodes.length(); i++) { // Add the vertex by the position
                    JSONObject JSON_Node = jsonArrayNodes.getJSONObject(i);
                    String pos = JSON_Node.getString("pos");// Extract the coordinates to String
                    int id = JSON_Node.getInt("id"); // Extract the node ID
                    geo_location p = new GeoLcation(pos); // get p coordinates from getXYZ function
                    node_data n = new NodeData(p, id);
                    g.addNode(n); // Add new vertex to the graph
                }
                for (int i = 0; i < jsonArrayEdges.length(); i++) { // Add the edges by the vertex
                    JSONObject JSON_Edge = jsonArrayEdges.getJSONObject(i);
                    int src = JSON_Edge.getInt("src"); // Extract source
                    int dest = JSON_Edge.getInt("dest"); // Extract destination
                    double w = JSON_Edge.getDouble("w");
                    g.connect(src, dest, w);
                }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        this.init(g);
        return true;
    }


    private DijkstraResult dijkstra(int src) {
        double[] dist = new double[this.graph.nodeSize()];
        node_data[] prev = new node_data[dist.length];

        Queue<node_data> queue = new PriorityQueue<>(new Comparator<node_data>() {
            @Override
            public int compare(node_data n1, node_data n2) {
                if(dist[n1.getTag()] < dist[n2.getTag()]) {
                    return - 1;
                } else if(dist[n1.getTag()] > dist[n2.getTag()]) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        int i =  0;
        for(node_data nodeData : this.graph.getV()) {
            nodeData.setTag(i);
            nodeData.setInfo("in_queue");

            if (nodeData.getKey() == src) dist[i] = 0;
            else dist[i] = Double.MAX_VALUE;
            queue.add(nodeData);
            i++;
        }

        while(!queue.isEmpty()) {
            node_data u = queue.poll();
            u.setInfo("not_in_queue");
                if(this.graph.getE(u.getKey())==null){
                    continue;
                }
            for (edge_data edgeData : this.graph.getE(u.getKey())) {
                node_data v = this.graph.getNode(edgeData.getDest());

                if(v.getInfo().equals("in_queue")) {
                    double alt = dist[u.getTag()] + edgeData.getWeight();

                    if (alt < dist[v.getTag()]) {
                        dist[v.getTag()] = alt;
                        prev[v.getTag()] = u;

                        // re-order v in queue
                        queue.remove(v);
                        queue.add(v);
                    }
                }
            }
        }

        return new DijkstraResult(dist, prev);
    }

    public static void main(String[] args) {
        directed_weighted_graph graph = new DWGraph_DS();
        graph.addNode(new NodeData(0));
        graph.addNode(new NodeData(1));
        graph.addNode(new NodeData(2));
        graph.addNode(new NodeData(3));
        graph.connect(0,1,2);
        graph.connect(0,2,2);
        graph.connect(0,3,2);
        graph.connect(1,0,2);
        graph.connect(1,2,2);
        graph.connect(1,3,2);
        graph.connect(2,0,2);
       graph.connect(2,1,2);
        graph.connect(2,3,2);
        graph.connect(3,0,2);
       graph.connect(3,1,2);
        graph.connect(3,2,2);

        dw_graph_algorithms g = new DWGraph_Algo();
        g.init(graph);
        System.out.println(g.isConnected());

    }
}
