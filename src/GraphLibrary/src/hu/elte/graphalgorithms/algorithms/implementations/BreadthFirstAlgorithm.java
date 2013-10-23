package hu.elte.graphalgorithms.algorithms.implementations;

import hu.elte.graphalgorithms.algorithms.interfaces.GraphAlgorithm;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphArc;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode.Color;
import static hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode.Color.WHITE;
import static hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode.Color.GRAY;
import static hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode.Color.BLACK;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * BreadthFirstAlgorithm (Szélességi bejárás/keresés)
 *
 * @author Balassa Imre
 */
public class BreadthFirstAlgorithm implements GraphAlgorithm<ColorableGraphNode, ColorableGraphArc> {

    private Graph<ColorableGraphNode, ColorableGraphArc> graph;
    private Map<ColorableGraphNode, ColorableGraphNode> predecessors;
    private Map<ColorableGraphNode, Long> distance;
    private Map<ColorableGraphNode, Color> colors;
    private Queue<ColorableGraphNode> queue;
    private int step;

    @Override
    public void initialize(Graph<ColorableGraphNode, ColorableGraphArc> g) throws Exception {
        this.graph = g;
        predecessors = new TreeMap<>();
        distance = new TreeMap<>();
        colors = new TreeMap<>();
        queue = new LinkedList<>();
    }

    @Override
    public String run() throws Exception {
        return run(null);
    }

    @Override
    public String run(ColorableGraphNode s) throws Exception {
        StringBuilder result = new StringBuilder();
        result.append(s == null ? start() : start(s));
        while (!queue.isEmpty()) {
            result.append(doStep());
        }
        return result.toString();
    }

    @Override
    public String start() throws Exception {
        if (graph == null) {
            throw new UnsupportedOperationException("Graph is not yet set.");
        }
        List<ColorableGraphNode> nodes = graph.getNodes();
        if (nodes == null || nodes.isEmpty()) {
            throw new UnsupportedOperationException("Graph does not contain nodes."); //To change body of generated methods, choose Tools | Templates.
        }
        return start(nodes.get(0));
    }

    @Override
    public String start(ColorableGraphNode s) throws Exception {
        step = 0;
        if (graph == null) {
            throw new UnsupportedOperationException("Graph is not yet set.");
        }
        for (ColorableGraphNode node : graph.getNodes()) {
            if (node != s) {
                colors.put(node, WHITE);
                predecessors.put(node, null);
                distance.put(node, Long.MAX_VALUE);
            }
        }
        colors.put(s, GRAY);
        predecessors.put(s, null);
        distance.put(s, 0l);
        queue.offer(s);
        return dumpState();
    }

    private String dumpState() {
        StringBuilder sb = new StringBuilder();
        sb.append("Step: ").append(step);
        sb.append(", Queue: ").append(queue);
        sb.append(", Predecessors: ").append(predecessors);
        sb.append(", Colors: ").append(colors);
        sb.append(", Distance: ").append(distance);
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String doStep() throws Exception {
        if (queue.isEmpty()) {
            throw new UnsupportedOperationException("the queue is empty");
        }
        step++;
        ColorableGraphNode node = queue.poll();
        List<ColorableGraphArc> outgoing = graph.getOutboundArcs(node.getId());
        Set<ColorableGraphNode> outgoingNodes = new TreeSet<>();
        for (ColorableGraphArc arc : outgoing) {
            outgoingNodes.add(graph.getNode(arc.getToId()));
        }
        for (ColorableGraphNode adjacent : outgoingNodes) {
            if (colors.get(adjacent).equals(WHITE)) {
                colors.put(adjacent, GRAY);
                distance.put(adjacent, distance.get(node) + 1);
                predecessors.put(adjacent, node);
                queue.offer(adjacent);
            }
        }
        colors.put(node, BLACK);
        return dumpState();
    }
}
