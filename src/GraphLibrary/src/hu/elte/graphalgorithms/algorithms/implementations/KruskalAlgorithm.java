/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.algorithms.implementations;

import hu.elte.graphalgorithms.core.exceptions.UndirectedGraphRequiredException;
import hu.elte.graphalgorithms.algorithms.interfaces.GraphAlgorithm;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphArc;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode;
import hu.elte.graphalgorithms.core.GeneralGraphArc;
import hu.elte.graphalgorithms.core.GeneralGraphArcLessCostComparator;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author nagysan
 */
public class KruskalAlgorithm implements GraphAlgorithm<ColorableGraphNode, ColorableGraphArc>{
    
    private Graph<ColorableGraphNode, ColorableGraphArc> graph;
    private HashMap<Integer, Integer> nodeSets = new HashMap<>();

    @Override
    public void initialize(Graph<ColorableGraphNode, ColorableGraphArc> g) throws UndirectedGraphRequiredException{
        if (g.isDirected()){
            throw new UndirectedGraphRequiredException();
        }
        graph = g;
        nodeSets.clear();
        if (g.getNodeCount()>0){
            for(ColorableGraphNode node : g.getNodes()){
                nodeSets.put(node.getId(), node.getId());
            }
        }
    }

    @Override
    public void run() {
        PriorityQueue<ColorableGraphArc> sortedArcs;
        sortedArcs = new PriorityQueue<>(0,new GeneralGraphArcLessCostComparator<>(0.00001f));
        sortedArcs.addAll(graph.getArcs());
        while (!sortedArcs.isEmpty()){
            ColorableGraphArc currentArc = sortedArcs.poll();
            if (!nodeSets.get(currentArc.getFromId()).equals(nodeSets.get(currentArc.getToId()))) {
                for(Integer nodeId : nodeSets.keySet()){
                    if (nodeSets.get(nodeId).equals(nodeSets.get(currentArc.getToId()))){
                        nodeSets.put(nodeId, currentArc.getFromId());
                    }
                }
                currentArc.setColor(ColorableGraphArc.Color.BLUE);
                graph.getPairOfArc(currentArc.getId()).setColor(ColorableGraphArc.Color.BLUE);
            } else {
                currentArc.setColor(ColorableGraphArc.Color.RED);
                graph.getPairOfArc(currentArc.getId()).setColor(ColorableGraphArc.Color.RED);
            }
        }
        
    }

    @Override
    public void run(ColorableGraphNode s) {
        run();
    }

    @Override
    public void start() {
        doStep();
    }

    @Override
    public void start(ColorableGraphNode s) {
        start();
    }

    @Override
    public boolean doStep() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
