/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.algorithms.interfaces;

import hu.elte.graphalgorithms.core.GeneralGraphNode;
import hu.elte.graphalgorithms.core.interfaces.Graph;

/**
 *
 * @author nagysan
 */
public interface GraphAlgorithm {
    public void initialize(Graph g);
    public void run();
    public void run(GeneralGraphNode s);
    public void start();
    public boolean doStep();
}
