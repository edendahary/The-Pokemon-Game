package api;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DWGraph_AlgoTest {
    private DWGraph_Algo graph_algo;

    @Test
    void saveLoad() throws JSONException, FileNotFoundException {
        graph_algo = new DWGraph_Algo();
        directed_weighted_graph toInit = new DWGraph_DS();
        node_data[] nodes = new node_data[10];
        for (int i = 0; i < nodes.length; i++) {
            node_data node = new NodeData();
            nodes[i] = node;
            toInit.addNode(node);
        }
        for (int i = 0; i < nodes.length - 2; i++) {
            toInit.connect(nodes[i].getKey(), nodes[i + 1].getKey(), Math.random() * 10);
            toInit.connect(nodes[i].getKey(), nodes[i + 2].getKey(), Math.random() * 10);
        }
        graph_algo.init(toInit);
        graph_algo.save("test.json");
        dw_graph_algorithms Load = new DWGraph_Algo();
        directed_weighted_graph new_graph = new DWGraph_DS();
        Load.init(new_graph);
        Load.load("test.json");
        assertEquals(toInit, Load.getGraph());
    }
}
