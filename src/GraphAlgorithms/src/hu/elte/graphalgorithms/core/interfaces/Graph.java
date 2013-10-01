/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core.interfaces;

import hu.elte.graphalgorithms.core.GeneralGraphArc;
import hu.elte.graphalgorithms.core.GeneralGraphNode;
import java.util.List;

/**
 *
 * @author nagysan
 */
public interface Graph extends Cloneable {

    public Integer createNode(GeneralGraphNode nodeData);

    public boolean removeNode(Integer id);

    public GeneralGraphNode getNode(Integer id);

    public List<GeneralGraphNode> getNodes();

    public Integer createArc(int startNode, int endNode, float cost, GeneralGraphArc arcData);

    public boolean removeArc(Integer id);

    public List<GeneralGraphArc> getArcs();

    public List<GeneralGraphArc> getInboundArcs(Integer id);

    public List<GeneralGraphArc> getOutboundArcs(Integer id);

    public boolean isDirected();

    public Integer getNodeCount();

    public Integer getArcCount();

    public GeneralGraphArc getPairOfArc(Integer id);
}
