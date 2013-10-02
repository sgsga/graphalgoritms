/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core;

import hu.elte.graphalgorithms.core.exceptions.ArcAlreadyExistsException;
import hu.elte.graphalgorithms.core.exceptions.IdAlreadySetException;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

public class DirectedGraph<N extends GeneralGraphNode, A extends GeneralGraphArc> implements Cloneable, Graph<N, A> {

    private ConcurrentHashMap<Integer, N> nodeDatas;
    private ConcurrentHashMap<Integer, A> arcDatas;
    private Integer nodeSequence;
    private Integer arcSequence;
    private ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>> graph;

    public DirectedGraph() {
        nodeDatas = new ConcurrentHashMap<>();
        arcDatas = new ConcurrentHashMap<>();
        nodeSequence = 0;
        arcSequence = 0;
        graph = new ConcurrentHashMap<>();
    }

    @Override
    public Integer createNode(N nodeData) throws IdAlreadySetException {
        int id = nodeSequence++;
        nodeData.setId(id);
        nodeDatas.put(id, nodeData);
        graph.put(id, new ConcurrentHashMap<Integer, Integer>());
        return id;
    }

    @Override
    public boolean removeNode(Integer id) {
        if (nodeDatas.containsKey(id)) {
            List<A> inboundArcs = getInboundArcs(id);
            if (inboundArcs.size() > 0) {
                for (A arcData : inboundArcs) {
                    graph.get(arcData.getFromId()).remove(arcData.getToId());
                }
            }
            graph.remove(id);
            nodeDatas.remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public N getNode(Integer id) {
        return nodeDatas.get(id);
    }

    @Override
    public List<N> getNodes() {
        return new ArrayList<>(nodeDatas.values());
    }

    @Override
    public Integer createArc(int startNode, int endNode, float cost, A arcData) throws IdAlreadySetException, ArcAlreadyExistsException {
        try {
            ConcurrentHashMap<Integer, Integer> outboundArcs = graph.get(startNode);
            if (!outboundArcs.containsKey(endNode)) {
                int id = arcSequence++;
                arcData.initialize(startNode, endNode, cost, id);
                arcDatas.put(id, arcData);
                outboundArcs.put(endNode, id);
                return id;
            } else {
                throw new ArcAlreadyExistsException();
            }
        } catch (IdAlreadySetException | ArcAlreadyExistsException e) {
            return null;
        }
    }

    @Override
    public boolean removeArc(Integer id) {
        A arc = getArc(id);
        if (arc != null) {
            graph.get(arc.getFromId()).remove(arc.getToId());
            arcDatas.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public A getArc(Integer id) {
        return arcDatas.get(id);
    }

    @Override
    public List<A> getArcs() {
        return new ArrayList<>(arcDatas.values());
    }

    @Override
    public List<A> getInboundArcs(Integer id) {
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
    public List<A> getOutboundArcs(Integer id) {
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
    public boolean isDirected() {
        if (getArcCount() == 0) {
            return false;
        }
        for (Integer arcId : nodeDatas.keySet()) {
            if (getPairOfArc(arcId) == null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getNodeCount() {
        return nodeDatas.size();
    }

    @Override
    public int getArcCount() {
        return arcDatas.size();
    }

    @Override
    public A getPairOfArc(Integer id) {
        A arc = arcDatas.get(id);
        Integer pairId = graph.get(arc.getToId()).get(arc.getFromId());
        if (pairId == null) {
            return null;
        }
        return arcDatas.get(pairId);
    }

    @Override
    public A getArc(Integer from, Integer to) {
        Integer arcId = graph.get(from).get(to);
        if (arcId != null) {
            return arcDatas.get(arcId);
        }
        return null;
    }
}
