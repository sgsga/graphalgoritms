/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core;

import hu.elte.graphalgorithms.core.exceptions.ArcAlreadyExistsException;
import hu.elte.graphalgorithms.core.exceptions.IdAlreadySetException;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DirectedGraph<N extends GeneralGraphNode, A extends GeneralGraphArc> implements Cloneable, Graph<N, A> {

    private HashMap<Integer, N> nodeDatas;
    private HashMap<Integer, A> arcDatas;
    private Integer nodeSequence;
    private Integer arcSequence;
    private HashMap<Integer, HashMap<Integer, Integer>> graph;

    public DirectedGraph() {
        nodeDatas = new HashMap<>();
        arcDatas = new HashMap<>();
        nodeSequence = 0;
        arcSequence = 0;
    }

    @Override
    public Integer createNode(N nodeData) {
        try {
            int id = nodeSequence++;
            nodeData.setId(id);
            nodeDatas.put(id, nodeData);
            graph.put(id, new HashMap<Integer, Integer>());
            return id;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean removeNode(Integer id) {
        if (nodeDatas.containsKey(id)) {
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
            HashMap<Integer, Integer> outboundArcs = graph.get(startNode);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        if (!arcDatas.isEmpty()){
            for(A arcData : arcDatas.values()){
                if (arcData.getToId().equals(id)) {
                    result.add(arcData);
                }
            }
        }
        return result;
    }

    @Override
    public List<A> getOutboundArcs(Integer id) {
        HashMap<Integer, Integer> outboundArcs = graph.get(id);
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
    public Integer getNodeCount() {
        return nodeDatas.size();
    }

    @Override
    public Integer getArcCount() {
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
