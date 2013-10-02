/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core.interfaces;

import hu.elte.graphalgorithms.core.GeneralGraphArc;
import hu.elte.graphalgorithms.core.GeneralGraphNode;
import hu.elte.graphalgorithms.core.exceptions.ArcAlreadyExistsException;
import java.util.List;

/**
 *
 * @author nagysan
 */
public interface Graph<N extends GeneralGraphNode, A extends GeneralGraphArc> extends Cloneable {

    public Integer createNode(N nodeData) throws Exception;

    public boolean removeNode(Integer id);

    public N getNode(Integer id);

    public List<N> getNodes();

    public Integer createArc(int startNode, int endNode, float cost, A arcData) throws Exception;

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
}
