/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core;

import hu.elte.graphalgorithms.core.exceptions.ArcAlreadyExistsException;
import hu.elte.graphalgorithms.core.exceptions.IdAlreadySetException;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

public class DirectedGraph<N extends GeneralGraphNode, A extends GeneralGraphArc> implements Cloneable, Graph<N, A> {

    protected ConcurrentHashMap<Integer, N> nodeDatas;
    protected ConcurrentHashMap<Integer, A> arcDatas;
    protected Integer nodeSequence;
    protected Integer arcSequence;
    protected ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>> graph;

    public DirectedGraph() {
        nodeDatas = new ConcurrentHashMap<>();
        arcDatas = new ConcurrentHashMap<>();
        nodeSequence = 0;
        arcSequence = 0;
        graph = new ConcurrentHashMap<>();
    }

    @Override
    public final Integer createNode(N nodeData){
        int id = nodeSequence++;
        if (nodeData.isIdBlank()) {
            nodeData.setId(id);
        } else {
            if (nodeSequence < nodeData.getId()) {
                nodeSequence = nodeData.getId() + 1;
            }
            id = nodeData.getId();
        }
        nodeDatas.put(id, nodeData);
        graph.put(id, new ConcurrentHashMap<Integer, Integer>());
        return id;
    }

    @Override
    public final ArrayList<Integer> removeNode(Integer id) {
        ArrayList<Integer> result = null;
        if (nodeDatas.containsKey(id)) {
            result = new ArrayList<>();
            List<A> inboundArcs = getInboundArcs(id);
            if (inboundArcs.size() > 0) {
                for (A arcData : inboundArcs) {
                    graph.get(arcData.getFromId()).remove(arcData.getToId());
                    result.add(arcData.getId());
                }
            }
            
            result.addAll(graph.get(id).values());
            for (Integer arcId : result){
                arcDatas.remove(arcId);
            }
            graph.remove(id);
            nodeDatas.remove(id);
        }
        return result;
    }

    @Override
    public final N getNode(Integer id) {
        return nodeDatas.get(id);
    }

    @Override
    public final List<N> getNodes() {
        return new ArrayList<>(nodeDatas.values());
    }

    @Override
    public final Integer createArc(int startNode, int endNode, float cost, A arcData) throws ArcAlreadyExistsException {
        ConcurrentHashMap<Integer, Integer> outboundArcs = graph.get(startNode);
        if (!outboundArcs.containsKey(endNode)) {
            int id = arcSequence++;
            if (!arcData.isInitialized()) {
               
                arcData.initialize(startNode, endNode, cost, id);
            } else {
                if (arcSequence < arcData.getId()) {
                    arcSequence = arcData.getId() + 1;
                }
                id = arcData.getId();
            }
            arcDatas.put(id, arcData);
            outboundArcs.put(endNode, id);
            return id;
        } else {
            throw new ArcAlreadyExistsException();
        }
    }

    @Override
    public final boolean removeArc(Integer id) {
        A arc = getArc(id);
        if (arc != null) {
            graph.get(arc.getFromId()).remove(arc.getToId());
            arcDatas.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public final A getArc(Integer id) {
        return arcDatas.get(id);
    }

    @Override
    public final List<A> getArcs() {
        return new ArrayList<>(arcDatas.values());
    }

    @Override
    public final List<A> getInboundArcs(Integer id) {
        ArrayList<A> result = new ArrayList<>();
        if (!arcDatas.isEmpty()) {
            for (A arcData : arcDatas.values()) {
                if (arcData.getToId().equals(id)) {
                    result.add(arcData);
                }
            }
        }
        return result;
    }

    @Override
    public final List<A> getOutboundArcs(Integer id) {
        ConcurrentHashMap<Integer, Integer> outboundArcs = graph.get(id);
        ArrayList<A> result = new ArrayList<>(outboundArcs.size());
        if (outboundArcs.size() > 0) {
            for (Integer arcId : outboundArcs.values()) {
                result.add(arcDatas.get(arcId));
            }
        }
        return result;
    }

    @Override
    public final boolean isDirected() {
        if (getArcCount() == 0) {
            return false;
        }
        for (Integer arcId : arcDatas.keySet()) {
            if (getPairOfArc(arcId) == null || !getArc(arcId).getCost().equals(getPairOfArc(arcId).getCost())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final int getNodeCount() {
        return nodeDatas.size();
    }

    @Override
    public final int getArcCount() {
        return arcDatas.size();
    }

    @Override
    public final A getPairOfArc(Integer id) {
        A arc = arcDatas.get(id);
        Integer pairId = graph.get(arc.getToId()).get(arc.getFromId());
        if (pairId == null) {
            return null;
        }
        return arcDatas.get(pairId);
    }

    @Override
    public final A getArc(Integer from, Integer to) {
        Integer arcId = graph.get(from).get(to);
        if (arcId != null) {
            return arcDatas.get(arcId);
        }
        return null;
    }

    @Override
    public void clear() {
        arcDatas.clear();
        nodeDatas.clear();
        graph.clear();
        arcSequence = 0;
        nodeSequence = 0;
    }

    @Override
    public void clearColors() {
        for (N node : nodeDatas.values()) {
        }
    }
}
