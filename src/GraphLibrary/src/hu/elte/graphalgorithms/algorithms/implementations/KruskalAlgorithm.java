/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.algorithms.implementations;

import hu.elte.graphalgorithms.core.exceptions.UndirectedGraphRequiredException;
import hu.elte.graphalgorithms.algorithms.interfaces.GraphAlgorithm;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphArc;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode;
import hu.elte.graphalgorithms.core.GeneralGraphArcLessCostComparator;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author nagysan
 */
public class KruskalAlgorithm implements GraphAlgorithm<ColorableGraphNode, ColorableGraphArc> {

    private Graph<ColorableGraphNode, ColorableGraphArc> graph;
    private HashMap<Integer, Integer> nodeSets = new HashMap<>();
    private PriorityQueue<ColorableGraphArc> sortedArcs = new PriorityQueue<>();

    @Override
    public void initialize(Graph<ColorableGraphNode, ColorableGraphArc> g) throws UndirectedGraphRequiredException {
        if (g.isDirected()) {
            throw new UndirectedGraphRequiredException();
        }
        graph = g;
        nodeSets.clear();
        // Creates unique sets
        if (g.getNodeCount() > 0) {
            for (ColorableGraphNode node : g.getNodes()) {
                nodeSets.put(node.getId(), node.getId());
            }
        }
    }

    @Override
    public String run() {
        start();
        for (ColorableGraphArc ga : graph.getArcs()) {
            System.out.println(ga.toString());

        }
        int coloredArcs = 0;
        while (!sortedArcs.isEmpty() && coloredArcs<graph.getArcCount()) {
            ColorableGraphArc currentArc = sortedArcs.poll();
            if (currentArc.getFromId() > currentArc.getToId()) {
                //continue;
            }
            if (currentArc.getColor() != ColorableGraphArc.Color.BLACK) {
                continue;
            }

            if (!nodeSets.get(currentArc.getFromId()).equals(nodeSets.get(currentArc.getToId()))) {
                // Merge sets
                for (Integer nodeId : nodeSets.keySet()) {
                    if (nodeSets.get(nodeId).equals(nodeSets.get(currentArc.getToId()))) {
                        nodeSets.put(nodeId, nodeSets.get(currentArc.getFromId()));
                    }
                }
                currentArc.setColor(ColorableGraphArc.Color.BLUE);
                graph.getPairOfArc(currentArc.getId()).setColor(ColorableGraphArc.Color.BLUE);
                graph.getNode(currentArc.getFromId()).setColor(ColorableGraphNode.Color.GREEN);
                graph.getNode(currentArc.getToId()).setColor(ColorableGraphNode.Color.GREEN);
                
            } else {
                currentArc.setColor(ColorableGraphArc.Color.RED);
                graph.getPairOfArc(currentArc.getId()).setColor(ColorableGraphArc.Color.RED);
            }
            coloredArcs += 2;
        }
        return null;
    }

    @Override
    public String run(ColorableGraphNode s) {
        return run();
    }

    @Override
    public String start() {
        sortedArcs.clear();
        sortedArcs = new PriorityQueue<>(1, new GeneralGraphArcLessCostComparator<>(0.001f));
        sortedArcs.addAll(graph.getArcs());
        return "";
    }

    @Override
    public String start(ColorableGraphNode s) {
        return start();
    }

    @Override
    public String doStep() {
        if (!sortedArcs.isEmpty()) {
            ColorableGraphArc currentArc = sortedArcs.poll();
            while (currentArc.getFromId() > currentArc.getToId() || currentArc.getColor() != ColorableGraphArc.Color.WHITE) {
                currentArc = sortedArcs.poll();
            }

            if (!nodeSets.get(currentArc.getFromId()).equals(nodeSets.get(currentArc.getToId()))) {
                for (Integer nodeId : nodeSets.keySet()) {
                    if (nodeSets.get(nodeId).equals(nodeSets.get(currentArc.getToId()))) {
                        nodeSets.put(nodeId, nodeSets.get(currentArc.getFromId()));
                    }
                }
                currentArc.setColor(ColorableGraphArc.Color.BLUE);
                graph.getPairOfArc(currentArc.getId()).setColor(ColorableGraphArc.Color.BLUE);
                graph.getNode(currentArc.getFromId()).setColor(ColorableGraphNode.Color.GREEN);
                graph.getNode(currentArc.getToId()).setColor(ColorableGraphNode.Color.GREEN);
            } else {
                currentArc.setColor(ColorableGraphArc.Color.RED);
                graph.getPairOfArc(currentArc.getId()).setColor(ColorableGraphArc.Color.RED);
            }

            return "";
        } else {
            return null;
        }

    }
}
