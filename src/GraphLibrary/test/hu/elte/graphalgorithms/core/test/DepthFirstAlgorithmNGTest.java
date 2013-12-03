/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.elte.graphalgorithms.core.test;

import hu.elte.graphalgorithms.algorithms.implementations.DepthFirstAlgorithm;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphArc;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode;
import hu.elte.graphalgorithms.core.DirectedGraph;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author hu3b1059
 */
public class DepthFirstAlgorithmNGTest {
    private Graph<ColorableGraphNode, ColorableGraphArc> testGraph;

    public DepthFirstAlgorithmNGTest() {
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
    }

//    /**
//     * Test of initialize method, of class DepthFirstAlgorithm.
//     */
//    @Test
//    public void testInitialize() throws Exception {
//        System.out.println("initialize");
//        Graph<ColorableGraphNode, ColorableGraphArc> g = null;
//        DepthFirstAlgorithm instance = new DepthFirstAlgorithm();
//        instance.initialize(g);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of run method, of class DepthFirstAlgorithm.
//     */
    @Test
    public void testRun_0args() {
        System.out.println("run");
        DepthFirstAlgorithm instance = new DepthFirstAlgorithm();
        instance.initialize(testGraph);
        instance.start();
        instance.run();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.BLACK);
    }
//
//    /**
//     * Test of run method, of class DepthFirstAlgorithm.
//     */
//    @Test
//    public void testRun_ColorableGraphNode() {
//        System.out.println("run");
//        ColorableGraphNode s = null;
//        DepthFirstAlgorithm instance = new DepthFirstAlgorithm();
//        String expResult = "";
//        String result = instance.run(s);
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of start method, of class DepthFirstAlgorithm.
//     */
//    @Test
//    public void testStart_0args() {
//        System.out.println("start");
//        DepthFirstAlgorithm instance = new DepthFirstAlgorithm();
//        String expResult = "";
//        String result = instance.start();
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of start method, of class DepthFirstAlgorithm.
//     */
//    @Test
//    public void testStart_ColorableGraphNode() {
//        System.out.println("start");
//        ColorableGraphNode s = null;
//        DepthFirstAlgorithm instance = new DepthFirstAlgorithm();
//        String expResult = "";
//        String result = instance.start(s);
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of doStep method, of class DepthFirstAlgorithm.
//     */
//    @Test
//    public void testDoStep() {
//        System.out.println("doStep");
//        DepthFirstAlgorithm instance = new DepthFirstAlgorithm();
//        String expResult = "";
//        String result = instance.doStep();
//        assertEquals(result, expResult);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
     @Test
    public void testFullDepthtFirst() throws Exception {
        ColorableGraphNode start = testGraph.getNode(0);
        DepthFirstAlgorithm instance = new DepthFirstAlgorithm();
        instance.initialize(testGraph);
        instance.start(start);
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.WHITE);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.WHITE);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.WHITE);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.GRAY);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.BLACK);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.BLACK);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.WHITE);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.BLACK);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.BLACK);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.BLACK);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.GRAY);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.BLACK);
        instance.doStep();
        assertEquals(testGraph.getNode(0).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(1).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(2).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(3).getColor(), ColorableGraphNode.Color.BLACK);
        assertEquals(testGraph.getNode(4).getColor(), ColorableGraphNode.Color.BLACK);
    }
}
