/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.algorithms.implementations;

import hu.elte.graphalgorithms.algorithms.interfaces.GraphAlgorithm;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphArc;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

/**
 *
 * @author nagysan
 */
public class DijkstraAlgorithm implements GraphAlgorithm<ColorableGraphNode, ColorableGraphArc> {

    private Graph<ColorableGraphNode, ColorableGraphArc> graph;
    private ConcurrentHashMap<Integer,Double> distance = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,Integer> parent = new ConcurrentHashMap<>();
    private PriorityBlockingQueue<ColorableGraphNode> nodes;

    @Override
    public void initialize(Graph<ColorableGraphNode, ColorableGraphArc> g) {
        graph = g;
    }

    @Override
    public String run() {
        return run(graph.getNodes().get(0));
    }

    @Override
    public String run(ColorableGraphNode s) {
        String result = start(s);
        String res = null;
        while ((res = doStep()) != null) {
            result += res;
        }
        return result;
    }

    @Override
    public String start() {
        return start(graph.getNodes().get(0));
    }

    @Override
    public String start(ColorableGraphNode s) {
        distance.clear();
        parent.clear();
        for (ColorableGraphNode node : graph.getNodes()){
            distance.put(node.getId(), Double.POSITIVE_INFINITY);
            parent.put(node.getId(), -1);
            node.setColor(ColorableGraphNode.Color.WHITE);
        }
        distance.put(s.getId(), 0.0);
        parent.put(s.getId(), -1);
        nodes = new PriorityBlockingQueue(graph.getNodeCount(), new Comparator<ColorableGraphNode>(){

            @Override
            public int compare(ColorableGraphNode o1, ColorableGraphNode o2) {
                if (distance.get(o1.getId()) < distance.get(o2.getId())) {
                    return -1;
                } else if (distance.get(o1.getId()) > distance.get(o2.getId())){
                    return 1;
                }
                return 0;
            }
        });
        for (ColorableGraphNode node : graph.getNodes()){
            nodes.add(node);
        }
        
        return "Sikeres inicializáció!\n"+doStep();
    }

    @Override
    public String doStep() {
        if (nodes.isEmpty()) return null;
        ColorableGraphNode node = nodes.poll();
        node.setColor(ColorableGraphNode.Color.BLACK);
        for (ColorableGraphArc nb : graph.getOutboundArcs(node.getId())){
            ColorableGraphNode to = graph.getNode(nb.getToId());
            if (to.getColor() != ColorableGraphNode.Color.BLACK){
                if (distance.get(to.getId())>distance.get(node.getId())+nb.getCost()){
                    distance.put(to.getId(), distance.get(node.getId())+nb.getCost());
                    if (parent.get(to.getId()) != -1){
                        graph.getArc(parent.get(to.getId())).setColor(ColorableGraphArc.Color.BLACK);
                    }
                    if (nodes.contains(to)) nodes.remove(to);
                    nodes.add(to);
                    parent.put(to.getId(),nb.getId());
                    graph.getArc(parent.get(to.getId())).setColor(ColorableGraphArc.Color.BLUE);
                    
                }
            }
        }
        return "OK";
    }
    
}
