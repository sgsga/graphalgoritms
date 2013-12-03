package hu.elte.graphalgorithms.algorithms.implementations;

import hu.elte.graphalgorithms.algorithms.interfaces.GraphAlgorithm;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphArc;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Mélységi bejárás
 * @author Balassa Imre
 */
public class DepthFirstAlgorithm implements GraphAlgorithm<ColorableGraphNode, ColorableGraphArc> {    
    private Graph<ColorableGraphNode, ColorableGraphArc> graph;
    private Map<ColorableGraphNode, ColorableGraphNode.Color> colors = new TreeMap<ColorableGraphNode, ColorableGraphNode.Color>();
    private Map<ColorableGraphNode, ColorableGraphNode> predecessors = new TreeMap<ColorableGraphNode, ColorableGraphNode>();
    private Map<ColorableGraphNode, Integer> beginTimes = new TreeMap<ColorableGraphNode, Integer>();
    private Map<ColorableGraphNode, Integer> leaveTimes = new TreeMap<ColorableGraphNode, Integer>();
    private boolean initialized;
    private boolean started;
    private Stack<ColorableGraphNode> stack = new Stack<ColorableGraphNode>();
    private int time;
    
    private void setColor(ColorableGraphNode node, ColorableGraphNode.Color color) {
        if (node != null) {
            colors.put(node, color);
            node.setColor(color);
        }
    }
    
    @Override
    public void initialize(Graph<ColorableGraphNode, ColorableGraphArc> g)  {
        graph = g;
        time = 0;
        for (ColorableGraphNode node : g.getNodes()) {
            setColor(node, ColorableGraphNode.Color.WHITE);
        }
        initialized = true;
    }

    @Override
    public String run(){
        start();
        while (!stack.empty()) {
            doStep();
        }
        return "";
    }

    @Override
    public String run(ColorableGraphNode s){
        return run();
    }

    @Override
    public String start() {
//        if (!initialized) {
//            throw new UnsupportedOperationException("Not supported yet.");
//        }
        started = true;
        return "";
    }

    @Override
    public String start(ColorableGraphNode s){
        start();
        return "";
    }

    @Override
    public String doStep(){
//        if (!started) {
//            throw new UnsupportedOperationException("Not supported yet.");
//        }
        if (stack.isEmpty()) {
            for (ColorableGraphNode node : graph.getNodes()) {
                if (ColorableGraphNode.Color.WHITE.equals(node.getColor())) {
                    toGrey(node);
                    return "";
                }
            }
        } else {
            ColorableGraphNode greyNode = stack.pop();
            for (ColorableGraphArc arc : graph.getOutboundArcs(greyNode.getId())) {
                ColorableGraphNode adjacent = graph.getNode(arc.getToId());
                if (ColorableGraphNode.Color.WHITE.equals(adjacent.getColor())) {
                    predecessors.put(greyNode, adjacent);
                    // put it back
                    stack.push(greyNode);
                    toGrey(adjacent);
                    return "";
                }
            }
            toBlack(greyNode);
        }
        return "";
    }
    
    private void toGrey(ColorableGraphNode node) {
        setColor(node, ColorableGraphNode.Color.GRAY);
        time += 1;
        beginTimes.put(node, time);
        stack.push(node);
    }
    
    private void toBlack(ColorableGraphNode node) {
        setColor(node, ColorableGraphNode.Color.BLACK);
        time += 1;
        leaveTimes.put(node, time);
    }
}
