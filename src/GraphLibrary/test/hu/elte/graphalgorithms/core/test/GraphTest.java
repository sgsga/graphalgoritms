/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core.test;

import hu.elte.graphalgorithms.core.DirectedGraph;
import hu.elte.graphalgorithms.core.GeneralGraphArc;
import hu.elte.graphalgorithms.core.GeneralGraphNode;
import hu.elte.graphalgorithms.core.exceptions.IdAlreadySetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author nagysan
 */
public class GraphTest {
    private static DirectedGraph<GeneralGraphNode, GeneralGraphArc> graph = new DirectedGraph<>();
    
    public GraphTest() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    private void createNewGraph(){
      graph = new DirectedGraph<>();  
    }
    
    @Test(description = "Step 01: The graph created and is empty")
    public void createGraph() {
        createNewGraph();
        assertNotNull(graph);
        assertEquals(0, graph.getArcCount());
        assertEquals(0, graph.getNodeCount());
    }
    
    @Test(description = "Step 02: Add nodes and check data", dependsOnMethods = {"createGraph"} )
    public void addNodes() {
        GeneralGraphNode node1 = new GeneralGraphNode();
        GeneralGraphNode node2 = new GeneralGraphNode();
        Integer createdNode1 = null;
        Integer createdNode2 = null;
        try {
            createdNode1 = graph.createNode(node1);
            createdNode2 = graph.createNode(node2);
        } catch (IdAlreadySetException ex) {
            Logger.getLogger(GraphTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Test failed: "+ex.getMessage());
        }
        assertSame(node1, graph.getNode(createdNode1));
        assertSame(node2, graph.getNode(createdNode2));
        assertEquals(createdNode1, node1.getId());
        assertEquals(createdNode2, node2.getId());
    }
    
    @Test(description = "Step 03: Add an arc and check it", dependsOnMethods = {"addNodes"} )
    public void addArc() {
        GeneralGraphArc arc = new GeneralGraphArc();
    }
}