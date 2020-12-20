package api;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {
    private static Random _rnd = null;

    @Test
    void isConnected() {
        directed_weighted_graph g= graph_creator(3,6,2);
        dw_graph_algorithms g1 = new DWGraph_Algo();
        g1.init(g);
        assertTrue(g1.isConnected(),"The graph should be connected");
        g1.getGraph().removeNode(1);
        assertTrue(g1.isConnected(),"The graph should be connected");
        g1.getGraph().removeNode(0);
        assertTrue(g1.isConnected(),"The graph should be connected(only one node)");
        g1.getGraph().removeNode(4);
        assertTrue(g1.isConnected(),"The graph should be connected");
        directed_weighted_graph graph = new DWGraph_DS();
        dw_graph_algorithms EmptyG = new DWGraph_Algo();
        EmptyG.init(graph);
        assertTrue(EmptyG.isConnected(),"Empty graph is connected");

    }

    @Test
    void shortestPathDist() {
        directed_weighted_graph g= graph_creator(4,12,3);
        dw_graph_algorithms graphAlgorithms = new DWGraph_Algo();
        graphAlgorithms.init(g);
        double dis =graphAlgorithms.shortestPathDist(0,3);
        assertEquals(3.0,dis,"Should be equals");
        g.removeEdge(0,3);
        g.removeEdge(0,2);
        dis =graphAlgorithms.shortestPathDist(0,1);
        assertEquals(1.0,dis,"Should be equals");
    }

    @Test
    void shortestPath() {
        directed_weighted_graph g= graph_creator(4,10,2);
        dw_graph_algorithms graphAlgorithms  = new DWGraph_Algo();
        graphAlgorithms.init(g);
        String s="";
        for(node_data curr:graphAlgorithms.shortestPath(0,3) ){
            s+=curr.getKey() +"->";
        }
        assertEquals("0->1->3->",s,"Should be equals");
    }
    public static directed_weighted_graph graph_creator(int v_size, int e_size, int seed) {
        directed_weighted_graph g = new DWGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            node_data n = new NodeData(i);
            g.addNode(n);
        }
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int c = _rnd.nextInt(10);
            int i = nodes[a];
            int j = nodes[b];
            g.connect(i,j,c);
        }
        return g;
    }
    private static int[] nodes(directed_weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_data> V = g.getV();
        node_data[] nodes = new node_data[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
}