/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.algorithms.implementations;

import com.google.gson.Gson;
import hu.elte.graphalgorithms.core.exceptions.UndirectedGraphRequiredException;
import hu.elte.graphalgorithms.algorithms.interfaces.GraphAlgorithm;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphArc;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode;
import hu.elte.graphalgorithms.core.ExtendedDirectedGraph;
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
    public String run() {
        PriorityQueue<ColorableGraphArc> sortedArcs;
        sortedArcs = new PriorityQueue<>(1,new GeneralGraphArcLessCostComparator<>(0.00001f));
        sortedArcs.addAll(graph.getArcs());
        while (!sortedArcs.isEmpty()){
            ColorableGraphArc currentArc = sortedArcs.poll();
            if (currentArc.getFromId()>currentArc.getToId()) continue;
            if (currentArc.getColor() != ColorableGraphArc.Color.WHITE) continue;
            
            if (!nodeSets.get(currentArc.getFromId()).equals(nodeSets.get(currentArc.getToId()))) {
                for(Integer nodeId : nodeSets.keySet()){
                    if (nodeSets.get(nodeId).equals(nodeSets.get(currentArc.getToId()))){
                        nodeSets.put(nodeId, nodeSets.get(currentArc.getFromId()));
                    }
                }
                currentArc.setColor(ColorableGraphArc.Color.BLUE);
                graph.getPairOfArc(currentArc.getId()).setColor(ColorableGraphArc.Color.BLUE);
            } else {
                currentArc.setColor(ColorableGraphArc.Color.RED);
                graph.getPairOfArc(currentArc.getId()).setColor(ColorableGraphArc.Color.RED);
            }
        }
        return null;
    }

    @Override
    public String run(ColorableGraphNode s) {
        return run();
    }

    @Override
    public String start() {
        return doStep();
    }

    @Override
    public String start(ColorableGraphNode s) {
        return start();
    }

    @Override
    public String doStep() {
        return null;
        
    }
    
}
