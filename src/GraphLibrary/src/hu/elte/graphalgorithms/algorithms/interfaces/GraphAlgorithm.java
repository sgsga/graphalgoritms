/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.algorithms.interfaces;

import hu.elte.graphalgorithms.core.GeneralGraphArc;
import hu.elte.graphalgorithms.core.GeneralGraphNode;
import hu.elte.graphalgorithms.core.interfaces.Graph;

/**
 *
 * @author nagysan
 */
public interface GraphAlgorithm<N extends GeneralGraphNode, A extends GeneralGraphArc> {
    public void initialize(Graph<N,A> g) throws Exception;
    public void run() throws Exception;
    public void run(N s)  throws Exception;
    public void start()  throws Exception;
    public void start(N s) throws Exception;
    public boolean doStep() throws Exception;
}
