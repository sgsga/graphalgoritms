/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core.interfaces;

import hu.elte.graphalgorithms.core.GeneralGraphArc;
import hu.elte.graphalgorithms.core.GeneralGraphNode;
import hu.elte.graphalgorithms.core.exceptions.ArcAlreadyExistsException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nagysan
 */
public interface Graph<N extends GeneralGraphNode, A extends GeneralGraphArc> extends Cloneable, Serializable {

    public Integer createNode(N nodeData);

    public ArrayList<Integer> removeNode(Integer id);

    public N getNode(Integer id);

    public List<N> getNodes();

    public Integer createArc(int startNode, int endNode, float cost, A arcData) throws ArcAlreadyExistsException;

    public boolean removeArc(Integer id);
    
    public A getArc(Integer id);

    public List<A> getArcs();

    public List<A> getInboundArcs(Integer id);

    public List<A> getOutboundArcs(Integer id);

    public boolean isDirected();

    public int getNodeCount();

    public int getArcCount();

    public A getPairOfArc(Integer id);
    
    public A getArc(Integer from, Integer to);
    
    public void clear();
    
    public void clearColors();
    
    public interface NodeArcFactory<N,A> {
        public N createNode();
        public A createArc();
    }
}
