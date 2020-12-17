package api;

import gameClient.util.Point3D;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Iterator;

public class MyGUI extends JPanel {
    JFrame jFrame;
    private directed_weighted_graph graph;
    private dw_graph_algorithms graphAlgo;
    private game_service game;

    public MyGUI(game_service game) throws JSONException {
        // Initialize an empty graph and painting.
        graph = new DWGraph_DS();
        graphAlgo = new DWGraph_Algo();
        graphAlgo.init(graph);
        game.startGame();
        drawGUI();
        // draw
    }

    public MyGUI(directed_weighted_graph graph) {
        this.graph = graph;
        graphAlgo = new DWGraph_Algo();
        graphAlgo.init(graph);
        drawGUI();
    }


    public void drawGUI() {
        jFrame = new JFrame();
        jFrame.setTitle("Draw Graph");
        jFrame.setSize(800, 600);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.add(this);

    }

    public void paintComponent(Graphics g) {
        for (node_data CurrNode : this.graph.getV()) {
            g.setColor(Color.black);
            g.fillOval((int) CurrNode.getLocation().x(), (int) CurrNode.getLocation().y(), 10, 10);
        }
        Collection<node_data> nodes = graph.getV();
        Iterator<node_data> i;
        i = nodes.iterator();
        while (i.hasNext()) {
            node_data nd = i.next();
            Iterator<edge_data> edgei = graph.getE(nd.getKey()).iterator();
            while (edgei.hasNext()) {
                edge_data EDGE = edgei.next();
                Point3D src = new Point3D(nd.getLocation().x(), nd.getLocation().y(), nd.getLocation().z());
                geo_location LOCATION = graph.getNode(EDGE.getDest()).getLocation();
                Point3D dest = new Point3D(LOCATION.x(), LOCATION.y(), LOCATION.z());
                double w = EDGE.getWeight();
                g.setColor(Color.blue);
                g.drawLine((int) src.x(), (int) src.y(), (int) dest.x(), (int) dest.y());
                g.setColor(Color.ORANGE);
                int src_x = (int) src.x();
                int src_y = (int) src.y();
                int dest_x = (int) dest.x();
                int dest_y = (int) dest.y();
                int dir_x = (((((((src_x + dest_x) / 2) + dest_x) / 2) + dest_x) / 2) + dest_x) / 2;
                int dir_y = (((((((src_y + dest_y) / 2) + dest_y) / 2) + dest_y) / 2) + dest_y) / 2;
                g.fillOval(dir_x - 5, dir_y - 5, 10, 10);
                g.setColor(Color.red);
                g.drawString(String.format("%.1f", w), (int) (dest.x() + src.x()) / 2, (int) (dest.y() + src.y()) / 2);
                g.setColor(Color.CYAN);


                //      g.drawOval();

            }
        }
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

//        dw_graph_algorithms g = new DWGraph_Algo();
//        g.init(graph);
        MyGUI gui = new MyGUI(graph);

    }
}
