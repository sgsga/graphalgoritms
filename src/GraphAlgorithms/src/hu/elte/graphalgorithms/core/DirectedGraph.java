/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core;

import hu.elte.graphalgorithms.core.interfaces.Graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DirectedGraph<N extends GeneralGraphNode, A extends GeneralGraphArc> implements Cloneable, Graph<N,A> {

    private HashMap<Integer,N> nodeDatas;
    private HashMap<Integer,A> arcDatas;
    private Integer nodeSequence;
    private Integer arcSequence;
    
    private HashMap<Integer,HashMap<Integer,Integer>> graph;

    public DirectedGraph() {
        nodeDatas = new HashMap<>();
        arcDatas  = new HashMap<>();
        nodeSequence = 0;
        arcSequence = 0;
    }

    
    @Override
    public Integer createNode(N nodeData) {
        try {
            int id = nodeSequence++;
            nodeDatas.put(id, nodeData);
            graph.put(id, new HashMap<Integer,Integer>());
            return id;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean removeNode(Integer id) {
        if (nodeDatas.containsKey(id)){
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
    public Integer createArc(int startNode, int endNode, float cost, A arcData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<A> getOutboundArcs(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isDirected() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
