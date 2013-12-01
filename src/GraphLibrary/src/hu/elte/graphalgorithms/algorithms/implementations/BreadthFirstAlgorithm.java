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
    private boolean initialized;
    private boolean started;
    @Override
    public void initialize(Graph<ColorableGraphNode, ColorableGraphArc> g){
        this.graph = g;
        predecessors = new TreeMap<>();
        distance = new TreeMap<>();
        colors = new TreeMap<>();
        queue = new LinkedList<>();
        initialized = true;
    }

    @Override
    public String run()  {
        return run(null);
    }

    @Override
    public String run(ColorableGraphNode s) {
        StringBuilder result = new StringBuilder();
        result.append(s == null ? start() : start(s));
        while (!queue.isEmpty()) {
            result.append(doStep());
        }
        return result.toString();
    }

    @Override
    public String start() {
        List<ColorableGraphNode> nodes = graph.getNodes();
        return start(nodes.get(0));
    }

    @Override
    public String start(ColorableGraphNode s) {
        step = 0;
        for (ColorableGraphNode node : graph.getNodes()) {
            if (node != s) {
                setColor(node, WHITE);
                predecessors.put(node, null);
                distance.put(node, Long.MAX_VALUE);
            }
        }
        setColor(s, GRAY);
        predecessors.put(s, null);
        distance.put(s, 0l);
        queue.offer(s);
        started = true;
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
    public String doStep() {
        step++;
        if (queue.isEmpty()) return null;
        ColorableGraphNode node = queue.poll();
        List<ColorableGraphArc> outgoing = graph.getOutboundArcs(node.getId());
        Set<ColorableGraphNode> outgoingNodes = new TreeSet<>();
        for (ColorableGraphArc arc : outgoing) {
            outgoingNodes.add(graph.getNode(arc.getToId()));
        }
        for (ColorableGraphNode adjacent : outgoingNodes) {
            if (colors.get(adjacent).equals(WHITE)) {
                setColor(adjacent, GRAY);
                distance.put(adjacent, distance.get(node) + 1);
                predecessors.put(adjacent, node);
                graph.getArc(node.getId(), adjacent.getId()).setColor(ColorableGraphArc.Color.BLUE);
                queue.offer(adjacent);
            }
        }
        setColor(node, BLACK);
        return dumpState();
    }
    
    private void setColor(ColorableGraphNode node, Color color) {
        if (node != null) {
            colors.put(node, color);
            node.setColor(color);
        }
    }
}
