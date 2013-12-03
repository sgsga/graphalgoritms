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
import hu.elte.graphalgorithms.core.exceptions.KeyAlreadyExistsException;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nagysan
 */
public class KruskalAlgorithm implements GraphAlgorithm<ColorableGraphNode, ColorableGraphArc> {

    private Graph<ColorableGraphNode, ColorableGraphArc> graph;
    private PriorityQueue<ColorableGraphArc> sortedArcs = new PriorityQueue<>();
    private static final Integer OWNER = 42;
    private Integer setId;

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

        if (graph.getNodeCount() > 0) {
            for (ColorableGraphNode node : graph.getNodes()) {
                node.removeData("Set", OWNER);
            }
            for (ColorableGraphNode node : graph.getNodes()) {
                try {
                    node.addData("Set", new HashSet<ColorableGraphNode>(), OWNER);
                    ((HashSet<ColorableGraphNode>) node.getData("Set")).add(node);
                } catch (KeyAlreadyExistsException ex) {
                    Logger.getLogger(KruskalAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        setId = graph.getNodeCount() + 1;
        sortedArcs.clear();
        sortedArcs = new PriorityQueue<>(1, new GeneralGraphArcLessCostComparator<>(0.001f));
        sortedArcs.addAll(graph.getArcs());
        return "Sikeres inicializálás";
    }

    @Override
    public String start(ColorableGraphNode s) {
        return start();
    }

    @Override
    public String doStep() {
        if (!sortedArcs.isEmpty()) {
            ColorableGraphArc currentArc = null;
            while (!sortedArcs.isEmpty() && (currentArc == null || currentArc.getColor() != ColorableGraphArc.Color.BLACK)) {
                currentArc = sortedArcs.poll();
            }

            if (sortedArcs.isEmpty() && (currentArc == null || currentArc.getColor() != ColorableGraphArc.Color.BLACK)) {
                return null;
            }
            if (currentArc.getFromId() > currentArc.getToId()) {
                currentArc = graph.getPairOfArc(currentArc.getId());
            }
            HashSet<ColorableGraphNode> fromSet = ((HashSet<ColorableGraphNode>) (graph.getNode(currentArc.getFromId()).getData("Set")));
            HashSet<ColorableGraphNode> toSet = ((HashSet<ColorableGraphNode>) (graph.getNode(currentArc.getToId()).getData("Set")));
            ColorableGraphNode from = graph.getNode(currentArc.getFromId());
            ColorableGraphNode to = graph.getNode(currentArc.getToId());
            boolean inSet = fromSet.contains(to) && toSet.contains(from);
            if (!inSet) {
                try {
                    HashSet<ColorableGraphNode> newSet = new HashSet<>(fromSet);
                    newSet.addAll(toSet);
                    from.removeData("Set", OWNER);
                    to.removeData("Set", OWNER);
                    from.addData("Set", newSet, OWNER);
                    to.addData("Set", newSet, OWNER);
                    for (Iterator<ColorableGraphNode> it = fromSet.iterator(); it.hasNext();) {
                        ColorableGraphNode n = it.next();
                        n.removeData("Set", OWNER);
                        n.addData("Set", newSet, OWNER);
                    }
                    for (Iterator<ColorableGraphNode> it = toSet.iterator(); it.hasNext();) {
                        ColorableGraphNode n = it.next();
                        n.removeData("Set", OWNER);
                        n.addData("Set", newSet, OWNER);
                    }
                    currentArc.setColor(ColorableGraphArc.Color.BLUE);
                    graph.getPairOfArc(currentArc.getId()).setColor(ColorableGraphArc.Color.BLUE);
                    from.setColor(ColorableGraphNode.Color.GREEN);
                    to.setColor(ColorableGraphNode.Color.GREEN);
                } catch (KeyAlreadyExistsException ex) {
                    Logger.getLogger(KruskalAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                currentArc.setColor(ColorableGraphArc.Color.RED);
                graph.getPairOfArc(currentArc.getId()).setColor(ColorableGraphArc.Color.RED);
            }

            return "";
        } else {
            for (ColorableGraphNode node : graph.getNodes()) {
                node.removeData("Set", OWNER);
            }
            return null;
        }

    }
}
