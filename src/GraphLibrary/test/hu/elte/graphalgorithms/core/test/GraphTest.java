/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core.test;

import hu.elte.graphalgorithms.core.DirectedGraph;
import hu.elte.graphalgorithms.core.GeneralGraphArc;
import hu.elte.graphalgorithms.core.GeneralGraphNode;
import hu.elte.graphalgorithms.core.exceptions.ArcAlreadyExistsException;
import hu.elte.graphalgorithms.core.exceptions.IdAlreadySetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.testng.Assert.*;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author nagysan
 */
@Test(singleThreaded = true)
public class GraphTest {

    private static final Logger log = Logger.getLogger(TestNG.class.getName());
    private static DirectedGraph<GeneralGraphNode, GeneralGraphArc> graph = new DirectedGraph<>();
    private static ArrayList<GeneralGraphNode> createdNodes = new ArrayList<>();
    private static ArrayList<GeneralGraphArc> createdArcs = new ArrayList<>();

    public GraphTest() {
    }

    @BeforeMethod
    public void testStart(Method method) {
        log.info("Test started: GraphTest." + method.getName());
    }

    @AfterMethod
    public void testEnd(ITestResult result) {
        log.info("Test ended: GraphTest." + result.getMethod().getMethodName());
    }

    private void createNewGraph() {
        graph = new DirectedGraph<>();
    }

    @Test(description = "The graph created and is empty")
    public void createGraphTest() {
        createNewGraph();
        assertNotNull(graph);
        assertEquals(0, graph.getArcCount());
        assertEquals(0, graph.getNodeCount());
    }

    @Test(description = "Add nodes and check data",
          dependsOnMethods = {"createGraphTest"})
    public void createNodesTest() throws IdAlreadySetException {
        GeneralGraphNode node1 = new GeneralGraphNode();
        GeneralGraphNode node2 = new GeneralGraphNode();
        Integer createdNode1 = null;
        Integer createdNode2 = null;
        createdNode1 = graph.createNode(node1);
        createdNode2 = graph.createNode(node2);
        assertEquals(createdNode1, node1.getId());
        assertEquals(createdNode2, node2.getId());
        createdNodes.add(node1);
        createdNodes.add(node2);
    }

    
    @Test(description = "Check getNodes method",
          dependsOnMethods = {"createNodesTest"})
    public void getNodeTest() {
        assertSame(createdNodes.get(0), graph.getNode(createdNodes.get(0).getId()));
        assertSame(createdNodes.get(1), graph.getNode(createdNodes.get(1).getId()));
    }

    @Test(description = "Get nodes",
          dependsOnMethods = {"createNodesTest"})
    public void getNodesTest() {
        List<GeneralGraphNode> nodes = graph.getNodes();
        for (int i = 0; i < createdNodes.size(); ++i) {
            boolean found = false;
            for (int j = 0; j < nodes.size(); ++j) {
                if (createdNodes.get(i) == nodes.get(j)) {
                    found = true;
                }
            }
            assertTrue(found, "A node cannot be found!");
        }

    }

    @Test(description = "Create arc",
          dependsOnMethods = {"createNodesTest"})
    public void createArcTest() throws IdAlreadySetException, ArcAlreadyExistsException {
            GeneralGraphArc arc1 = new GeneralGraphArc();
            GeneralGraphArc arc2 = new GeneralGraphArc();
            Integer createdArcId1 = graph.createArc(createdNodes.get(0).getId(), createdNodes.get(1).getId(), (float) 42.0, arc1);
            Integer createdArcId2 = graph.createArc(createdNodes.get(1).getId(), createdNodes.get(0).getId(), (float) -42.0, arc2);
            createdArcs.add(arc1);
            createdArcs.add(arc2);
    }
    @Test(description = "Create wrong arc",
          dependsOnMethods = {"createArcTest"}, expectedExceptions = {ArcAlreadyExistsException.class})
    public void createWrongArcTest() throws IdAlreadySetException, ArcAlreadyExistsException{
        GeneralGraphArc arc1 = new GeneralGraphArc();
        Integer createdArcId1 = graph.createArc(createdNodes.get(0).getId(), createdNodes.get(1).getId(), (float) 42.0, arc1);
    }
    
    @Test(description = "Check the two getArc method",
          dependsOnMethods = {"createArcTest"})
    public void getArcTest() {
        assertSame(createdArcs.get(0), graph.getArc(createdArcs.get(0).getId()));
        assertSame(createdArcs.get(0), graph.getArc(createdArcs.get(0).getFromId(),createdArcs.get(0).getToId()));
        assertSame(createdArcs.get(1), graph.getArc(createdArcs.get(1).getId()));
        assertSame(createdArcs.get(1), graph.getArc(createdArcs.get(1).getFromId(),createdArcs.get(1).getToId()));
    }

    @Test(description = "Check the two getArc method",
          dependsOnMethods = {"createArcTest"})
    public void getArcsTest() {
        List<GeneralGraphArc> arcs = graph.getArcs();
        assertTrue(arcs.size()==createdArcs.size());
        for (int i = 0; i < createdArcs.size(); ++i) {
            boolean found = false;
            for (int j = 0; j < arcs.size(); ++j) {
                if (createdArcs.get(i) == arcs.get(j)) {
                    found = true;
                }
            }
            assertTrue(found, "A node cannot be found!");
        }
    }
    
    @Test(description = "Check the getPairOfArc method",
          dependsOnMethods = {"createArcTest"})
    public void getPairOfArcTest() {
        assertSame(createdArcs.get(0), graph.getPairOfArc(createdArcs.get(1).getId()));
        assertSame(createdArcs.get(1), graph.getPairOfArc(createdArcs.get(0).getId()));
    }
    @Test(description = "Check the getInboundArcs method",
          dependsOnMethods = {"createArcTest","getArcsTest"})
    public void getInboundArcsTest() {
        
    }
}