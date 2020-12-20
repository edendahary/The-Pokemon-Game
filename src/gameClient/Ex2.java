package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Ex2 implements Runnable, ActionListener {
    private static MyFrame _win;
    private static Arena _ar;
    private static JTextField tf;
    private static JLabel l;
    private static JLabel l1;
    private static JButton b;
    private int scenario ;
    private	int id;
    private static JTextField idText;

    public static void main(String[] args) {
        JPanel Panel= new JPanel();
        JFrame Frame= new JFrame();
        Frame.setSize(400, 200);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel.setLayout(null);
        Panel.setBackground(Color.green);
        l=new JLabel("ID :");
        l.setBounds(10, 20, 80, 25);
        l1=new JLabel("Game Number :");
        l1.setBounds(10, 50, 100, 25);
        idText= new JTextField(20);
        idText.setBounds(100, 20, 165, 25);
        Panel.add(idText);
        tf= new JTextField();
        tf.setBounds(100, 50, 165, 25);
        b= new JButton("Go");
        b.setBounds(130,100,80,25);
        b.addActionListener(new Ex2());
        Panel.add(tf);
        Panel.add(l1);
        Panel.add(l);
        Panel.add(b);
        Frame.add(Panel);
        Frame.setVisible(true);
    }
    public Ex2(){}
    public Ex2(int id, int scenario_num) {
        this.scenario = scenario_num;
        this.id = id;
    }
    @Override
    public void run() {
        game_service game = Game_Server_Ex2.getServer(scenario);
        game.login(id);
        String g = game.getGraph();
        String pks = game.getPokemons();
        dw_graph_algorithms gg = new DWGraph_Algo();
        gg.load(g);
        init(game);

        game.startGame();
        _win.setTitle("Ex2 - OOP: Pokemon ");
        int ind=0;
        long dt=100;

        while(game.isRunning()) {
            synchronized (game_service.class) {
                try {
                    moveAgants(game, gg.getGraph());
                    if (ind % 1 == 0) {
                        _win.repaint();
                    }
                    Thread.sleep(dt);
                    ind++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }
    private void init(game_service game) {
        String g = game.getGraph();
        dw_graph_algorithms gg = new DWGraph_Algo();
        String fs = game.getPokemons();
        gg.load(g);
        _ar = new Arena(game);
        _ar.setGraph(gg.getGraph());
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyFrame("Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);
        _win.show();
        JSONObject line;
        String info = game.toString();
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int AmountOfAgents = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());

           List<CL_Pokemon>p = Arena.json2Pokemons(game.getPokemons());
            Queue <CL_Pokemon>PokemonQueue =ToQueue(p,gg);
            for(int a = 0;a<AmountOfAgents;a++) {
                CL_Pokemon CurrPoke = PokemonQueue.poll();
                int nn = CurrPoke.get_edge().getDest();
                if(CurrPoke.getType() < 0) {nn = CurrPoke.get_edge().getSrc();}
                game.addAgent(nn);
            }
        }
        catch (JSONException e) {e.printStackTrace();}
    }


        private  void moveAgants(game_service game, directed_weighted_graph gg){
            dw_graph_algorithms ga = new DWGraph_Algo();
            ga.init(gg);
            String lg = game.move();
            List<CL_Agent> log = Arena.getAgents(lg, gg);
            _ar.setAgents(log);
            String fs =  game.getPokemons();
            List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
            _ar.setPokemons(ffs);
            for(CL_Pokemon Poke: ffs){
                Arena.updateEdge(Poke,ga.getGraph());
            }
            List<node_data> path = null;
            for(int i=0;i<log.size();i++) {
                CL_Agent ag = log.get(i);
                int id = ag.getID();
                int dest = ag.getNextNode();
                int src = ag.getSrcNode();
                double v = ag.getValue();
                if(dest==-1) {
                    dest = nextNode(gg, src,ffs);
                    path = ga.shortestPath(src, dest);
                    if (path!=null) {
                        for (node_data curr : path) {
                            game.chooseNextEdge(ag.getID(), curr.getKey());
                        }
                    }
                    System.out.println("Agent: "+id+", val: "+v+"   turned to node: "+dest);
                }
            }
        }
    private static int nextNode(directed_weighted_graph g, int src,List<CL_Pokemon> P) {
        double dist;
        double MinDist= Double.MAX_VALUE;
        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(g);
        int ans = -1;
        Queue <CL_Pokemon>PokemonQueue = ToQueue(P,ga);
        while(!PokemonQueue.isEmpty()){
            CL_Pokemon Poke =PokemonQueue.poll();
            dist = ga.shortestPathDist(src,Poke.get_edge().getDest());
            if(dist > -1 && dist < MinDist){
                MinDist = dist;
                if( !Poke.getTrace().equals("InTrace")) {
                    Poke.setTrace("InTrace");
                    if (Poke.getType() == -1 && src < Poke.get_edge().getSrc()) {
                        ans = Poke.get_edge().getSrc();

                    } else {
                        ans = Poke.get_edge().getDest();
                        if (ans == src) {
                            ans = Poke.get_edge().getSrc();
                        }

                    }
                }else if (!PokemonQueue.isEmpty()){
                    MinDist= Double.MAX_VALUE;
                    continue;
                }

            }else if (!PokemonQueue.isEmpty()){
                continue;
            }
        }

        return ans;
    }
    public static Queue<CL_Pokemon> ToQueue (List <CL_Pokemon>p,dw_graph_algorithms gg){
        Queue<CL_Pokemon> PokemonQueue = new PriorityQueue<CL_Pokemon>(new Comparator<CL_Pokemon>() {

            @Override
            public int compare(CL_Pokemon o1, CL_Pokemon o2) {
                if (o1.getValue() < o2.getValue()) {
                    return 1;
                } else if (o1.getValue() > o2.getValue()) {
                    return -1;
                }
                return 0;
            }
        });
        for(CL_Pokemon curr: p){
            PokemonQueue.add(curr);
            Arena.updateEdge(curr,gg.getGraph());
        }
        return PokemonQueue;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
            String SenText = tf.getText();
            int Sen = Integer.parseInt(SenText);
                String idT = idText.getText();
                int idTT = Integer.parseInt(idT);
                Thread game1 = new Thread(new Ex2(idTT, Sen));
                game1.start();
        }

}
