package api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class DWGraph_DSTest  {

    @Test
    void getNode(){
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        Assertions.assertEquals(5,g.nodeSize());
        Assertions.assertEquals(null,g.getNode(5));
    }
    @Test
    void getEdge() {
        directed_weighted_graph g = new DWGraph_DS();
        node_data n1 = new NodeData() ;
        node_data n2 = new NodeData() ;
        g.addNode(n1);
        g.addNode(n2);
        Assertions.assertEquals(true,g.getEdge(n1.getKey(),n2.getKey())==null);
        g.connect(n1.getKey(),n2.getKey(),0);
        Assertions.assertEquals(true,g.getEdge(n1.getKey(),n2.getKey())!=null);
    }

    @Test
    void addNode() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        g.addNode(new NodeData(5));
        Assertions.assertEquals(6,g.nodeSize());
        Assertions.assertEquals(0,g.edgeSize());
        g.addNode(new NodeData(5));
        Assertions.assertEquals(6,g.nodeSize());
    }

    @Test
    void connect() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        g.addNode(new NodeData(5));
        g.connect(0,1,0);
        g.connect(0,2,0);
        g.connect(0,3,0);
        g.connect(0,4,0);
        Assertions.assertEquals(true,g.getEdge(0,1)!= null);
        g.connect(0,0,0);
        Assertions.assertEquals(4,g.edgeSize());
        Assertions.assertTrue(g.getEdge(0,0)==null);
    }

    @Test
    void getV() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        g.addNode(new NodeData(5));
        Collection<node_data> Nodes = g.getV();
        Assertions.assertEquals(6,Nodes.size());
    }

    @Test
    void getE() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        g.addNode(new NodeData(5));
        g.connect(0,1,0);
        g.connect(0,2,0);
        g.connect(0,3,0);
        g.connect(0,4,0);
        Collection<edge_data> ni = g.getE(0);
        Assertions.assertEquals(4,ni.size());
    }

    @Test
    void removeNode() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        g.addNode(new NodeData(5));
        g.removeNode(5);
        Assertions.assertEquals(5,g.nodeSize());
        g.removeNode(5);
        g.removeNode(90);
        Assertions.assertEquals(5,g.nodeSize());
    }
    @Test
    void removeEdge() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        g.addNode(new NodeData(5));
        g.connect(0,1,0);
        g.connect(0,2,0);
        g.connect(0,3,0);
        g.connect(0,4,0);
        g.removeEdge(0,1);
        Assertions.assertEquals(3,g.edgeSize());
        g.removeEdge(0,3);
        Assertions.assertEquals(null,g.getEdge(0,3));
    }

    @Test
    void nodeSize() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        g.addNode(new NodeData(5));
        g.addNode(new NodeData(6));

        Assertions.assertEquals(7,g.nodeSize());
        g.removeNode(0);
        g.removeNode(1);
        Assertions.assertEquals(5,g.nodeSize());
        node_data n = new NodeData(7);
        g.addNode(n);
        Assertions.assertEquals(6,g.nodeSize());
        g.addNode(n);
        Assertions.assertEquals(6,g.nodeSize());
    }

    @Test
    void edgeSize() {
        directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new NodeData(0));
        g.addNode(new NodeData(1));
        g.addNode(new NodeData(2));
        g.addNode(new NodeData(3));
        g.addNode(new NodeData(4));
        g.addNode(new NodeData(5));
        g.addNode(new NodeData(6));
        g.connect(0,0,1);
        Assertions.assertEquals(0,g.edgeSize());
        g.connect(0,1,1);
        g.connect(0,2,1);
        g.connect(0,3,1);
        Assertions.assertEquals(3,g.edgeSize());
        g.removeNode(0);
        Assertions.assertEquals(0,g.edgeSize());
        g.connect(0,1,1);
        Assertions.assertEquals(0,g.edgeSize());
        g.addNode(new NodeData(0));
        g.connect(0,1,1);
        g.connect(0,2,1);
        g.connect(0,3,1);
        g.removeEdge(0,1);
        g.removeEdge(0,0);
        g.removeEdge(0,5);
        Assertions.assertEquals(2,g.edgeSize());

    }

}