/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms.core.test;

import hu.elte.graphalgorithms.algorithms.implementations.BreadthFirstAlgorithm;
import hu.elte.graphalgorithms.algorithms.implementations.KruskalAlgorithm;
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
public class KruskalNGTest {

    private Graph<ColorableGraphNode, ColorableGraphArc> testGraph;

    public KruskalNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        // a simple graph 
        //        0 -8- 1
        //        | \   |
        //        1  2  3
        //        |   \ |
        //        2 -9- 3
        testGraph = new DirectedGraph<ColorableGraphNode, ColorableGraphArc>();
        ColorableGraphNode cgn0 = new ColorableGraphNode();
        Integer cg0id = testGraph.createNode(cgn0);
        ColorableGraphNode cgn1 = new ColorableGraphNode();
        Integer cg1id = testGraph.createNode(cgn1);
        ColorableGraphNode cgn2 = new ColorableGraphNode();
        Integer cg2id = testGraph.createNode(cgn2);
        ColorableGraphNode cgn3 = new ColorableGraphNode();
        Integer cg3id = testGraph.createNode(cgn3);
        testGraph.createArc(cg0id, cg1id, 8f, new ColorableGraphArc());
        testGraph.createArc(cg1id, cg0id, 8f, new ColorableGraphArc());
        testGraph.createArc(cg0id, cg3id, 2f, new ColorableGraphArc());
        testGraph.createArc(cg3id, cg0id, 2f, new ColorableGraphArc());
        testGraph.createArc(cg0id, cg2id, 1f, new ColorableGraphArc());
        testGraph.createArc(cg2id, cg0id, 1f, new ColorableGraphArc());
        testGraph.createArc(cg1id, cg3id, 3f, new ColorableGraphArc());
        testGraph.createArc(cg3id, cg1id, 3f, new ColorableGraphArc());
        testGraph.createArc(cg2id, cg3id, 9f, new ColorableGraphArc());
        testGraph.createArc(cg3id, cg2id, 9f, new ColorableGraphArc());

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
        KruskalAlgorithm instance = new KruskalAlgorithm();
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
    public void testRun_ColorableGraphNode() throws Exception {
        System.out.println("run");
        KruskalAlgorithm instance = new KruskalAlgorithm();
        instance.initialize(testGraph);
        instance.run();
        List<ColorableGraphNode> nodes = testGraph.getNodes();
        int nrGreen = 0;
        int nrWhite = 0;
        for (ColorableGraphNode node : nodes) {
            if (node.getColor() == ColorableGraphNode.Color.GREEN) {
                nrGreen++;
            } else if (node.getColor() == ColorableGraphNode.Color.WHITE) {
                nrWhite++;
            }
        }
        assertEquals(nrGreen, nodes.size());
        assertEquals(nrWhite, 0);

    }



    @Test
    public void testFullKruskal() throws Exception {
        ColorableGraphNode start = testGraph.getNode(0);
        KruskalAlgorithm instance = new KruskalAlgorithm();
        instance.initialize(testGraph);
        instance.start(start);
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getArc(0).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(1).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(2).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(3).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(4).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(5).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(6).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(7).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(8).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(9).getColor(), ColorableGraphArc.Color.BLACK);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getArc(0).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(1).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(2).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(3).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(4).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(5).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(6).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(7).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(8).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(9).getColor(), ColorableGraphArc.Color.BLACK);       
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getArc(0).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(1).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(2).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(3).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(4).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(5).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(6).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(7).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(8).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(9).getColor(), ColorableGraphArc.Color.BLACK);    
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getArc(0).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(1).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(2).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(3).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(4).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(5).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(6).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(7).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(8).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(9).getColor(), ColorableGraphArc.Color.BLACK);   
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getArc(0).getColor(), ColorableGraphArc.Color.RED);
        assertEquals(testGraph.getArc(1).getColor(), ColorableGraphArc.Color.RED);
        assertEquals(testGraph.getArc(2).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(3).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(4).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(5).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(6).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(7).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(8).getColor(), ColorableGraphArc.Color.BLACK);
        assertEquals(testGraph.getArc(9).getColor(), ColorableGraphArc.Color.BLACK); 
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.GREEN);
        assertEquals(testGraph.getArc(0).getColor(), ColorableGraphArc.Color.RED);
        assertEquals(testGraph.getArc(1).getColor(), ColorableGraphArc.Color.RED);
        assertEquals(testGraph.getArc(2).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(3).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(4).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(5).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(6).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(7).getColor(), ColorableGraphArc.Color.BLUE);
        assertEquals(testGraph.getArc(8).getColor(), ColorableGraphArc.Color.RED);
        assertEquals(testGraph.getArc(9).getColor(), ColorableGraphArc.Color.RED); 

    }
}
