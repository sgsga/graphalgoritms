/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.graphalgorithms.algorithms.implementations;

import hu.elte.graphalgorithms.algorithms.implementations.BreadthFirstAlgorithm;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphArc;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode;
import hu.elte.graphalgorithms.core.DirectedGraph;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import java.util.List;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Balassa Imre
 */
public class BreadthFirstAlgorithmNGTest {
    private Graph<ColorableGraphNode, ColorableGraphArc> testGraph;
    public BreadthFirstAlgorithmNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        // a simple 2 level deep graph 
        //    0
        //   / \
        //  1   2
        //  |   |
        //  11  22
        testGraph = new DirectedGraph<ColorableGraphNode,ColorableGraphArc>();
        ColorableGraphNode cgn0 = new ColorableGraphNode();
        testGraph.createNode(cgn0);
        ColorableGraphNode cgn1 = new ColorableGraphNode();
        testGraph.createNode(cgn1);
        ColorableGraphNode cgn2 = new ColorableGraphNode();
        testGraph.createNode(cgn2);
        ColorableGraphNode cgn11 = new ColorableGraphNode();
        testGraph.createNode(cgn11);
        ColorableGraphNode cgn22 = new ColorableGraphNode();
        testGraph.createNode(cgn22);
        testGraph.createArc(cgn0.getId(), cgn1.getId(), 0f, new ColorableGraphArc());
        testGraph.createArc(cgn1.getId(), cgn11.getId(), 0f, new ColorableGraphArc());
        testGraph.createArc(cgn0.getId(), cgn2.getId(), 0f, new ColorableGraphArc());
        testGraph.createArc(cgn2.getId(), cgn22.getId(), 0f, new ColorableGraphArc());
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        testGraph = null;
    }

    /**
     * Test of initialize method, of class BreadthFirstAlgorithm.
     */
    @Test
    public void testInitialize() throws Exception {
        System.out.println("initialize");
         BreadthFirstAlgorithm instance = new BreadthFirstAlgorithm();
        instance.initialize(testGraph);
        List<ColorableGraphNode> nodes = testGraph.getNodes();
        // after initialization all nodes must be white
        for (ColorableGraphNode node : nodes) {
            assertEquals(node.getColor(), ColorableGraphNode.Color.WHITE);
        }
    }

    /**
     * Test of run method, of class BreadthFirstAlgorithm.
     */
    @Test
    public void testRun_0args() throws Exception {
        System.out.println("run");
        BreadthFirstAlgorithm instance = new BreadthFirstAlgorithm();
        instance.initialize(testGraph);
        String result = instance.run();
        List<ColorableGraphNode> nodes = testGraph.getNodes();
        
        for (ColorableGraphNode node : nodes) {
            assertEquals(node.getColor(), ColorableGraphNode.Color.BLACK);
        }
    }

    /**
     * Test of run method, of class BreadthFirstAlgorithm.
     */
    @Test
    public void testRun_ColorableGraphNode() throws Exception {
        System.out.println("run");
        ColorableGraphNode start = testGraph.getNode(0);
        BreadthFirstAlgorithm instance = new BreadthFirstAlgorithm();
        instance.initialize(testGraph);
        instance.run();
        List<ColorableGraphNode> nodes = testGraph.getNodes();
        int nrGrey = 0;
        int nrBlack = 0;
        int nrWhite = 0;
        for (ColorableGraphNode node : nodes) {
            if (node.getColor()== ColorableGraphNode.Color.GRAY) {
                nrGrey++;
            }     else if (node.getColor()== ColorableGraphNode.Color.WHITE) {
                nrWhite++;
            } else if (node.getColor()== ColorableGraphNode.Color.BLACK) {
                nrBlack++;
            }
        }
        assertEquals(nrGrey, 0);
        assertEquals(nrBlack, nodes.size());
        assertEquals(nrWhite, 0);

    }

    /**
     * Test of start method, of class BreadthFirstAlgorithm.
     */
    @Test
    public void testStart_0args() throws Exception {
        System.out.println("start");
        ColorableGraphNode start = testGraph.getNode(0);
        BreadthFirstAlgorithm instance = new BreadthFirstAlgorithm();
        instance.initialize(testGraph);
        instance.start();
        List<ColorableGraphNode> nodes = testGraph.getNodes();
        int nrGrey = 0;
        for (ColorableGraphNode node : nodes) {
            if (node.getColor()== ColorableGraphNode.Color.GRAY) {
                nrGrey++;
            }
        }
        assertEquals(nrGrey, 1);
    }

    /**
     * Test of start method, of class BreadthFirstAlgorithm.
     */
    @Test
    public void testStart_ColorableGraphNode() throws Exception {
        System.out.println("start");
        ColorableGraphNode start = testGraph.getNode(0);
        BreadthFirstAlgorithm instance = new BreadthFirstAlgorithm();
        instance.initialize(testGraph);
        String result = instance.start(start);
        List<ColorableGraphNode> nodes = testGraph.getNodes();
        for (ColorableGraphNode node : nodes) {
            if (node.equals(start)){
                assertEquals(node.getColor(), ColorableGraphNode.Color.GRAY);
            } else {
                assertEquals(node.getColor(), ColorableGraphNode.Color.WHITE);
             }
        }
    }

    /**
     * Test of doStep method, of class BreadthFirstAlgorithm.
     */
    @Test
    public void testDoStep() throws Exception {
        System.out.println("doStep");
        ColorableGraphNode start = testGraph.getNode(0);
        BreadthFirstAlgorithm instance = new BreadthFirstAlgorithm();
        instance.initialize(testGraph);
        instance.start();
        instance.doStep();
        List<ColorableGraphNode> nodes = testGraph.getNodes();
        int nrGrey = 0;
        int nrBlack = 0;

        for (ColorableGraphNode node : nodes) {
            if (node.getColor()== ColorableGraphNode.Color.GRAY) {
                nrGrey++;
            } else if (node.getColor()== ColorableGraphNode.Color.BLACK) {
                nrBlack++;
            }
        }
        assertEquals(nrGrey, 2);
        assertEquals(nrBlack, 1);
    }
    
    @Test
    public void testFullBreadtFirst() throws Exception {
        ColorableGraphNode start = testGraph.getNode(0);
        BreadthFirstAlgorithm instance = new BreadthFirstAlgorithm();
        instance.initialize(testGraph);
        instance.start(start);
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.WHITE);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.WHITE);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.WHITE);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.GRAY);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.GRAY);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.BLACK);
        
    }
    
    
}
