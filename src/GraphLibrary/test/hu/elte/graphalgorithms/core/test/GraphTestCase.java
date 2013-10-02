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
public class GraphTestCase {
    private static DirectedGraph<GeneralGraphNode, GeneralGraphArc> graph = new DirectedGraph<>();
    
    public GraphTestCase() {
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
    public void Step01() {
        createNewGraph();
        assertNotNull(graph);
        assertEquals(0, graph.getArcCount());
        assertEquals(0, graph.getNodeCount());
    }
    
    @Test(description = "Step 02: Add a node and check data", dependsOnMethods = {"Step01"} )
    public void Step02() {
        GeneralGraphNode node = new GeneralGraphNode();
        Integer createdNode = null;
        try {
            createdNode = graph.createNode(node);
        } catch (IdAlreadySetException ex) {
            Logger.getLogger(GraphTestCase.class.getName()).log(Level.SEVERE, null, ex);
            fail("Test failed: "+ex.getMessage());
        }
        assertEquals(node, graph.getNode(createdNode));
    }
}