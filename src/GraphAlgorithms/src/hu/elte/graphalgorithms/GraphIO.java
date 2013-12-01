package hu.elte.graphalgorithms;

import hu.elte.graphalgorithms.MainController.Node;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphArc;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;

/**
 *
 * @author Balassa Imre
 */
public class GraphIO {

    public void saveGraph(File saveFile, MainController controller) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(saveFile));
            for (ColorableGraphNode node : controller.getGraph().getNodes()) {
                bw.append("ND:" + node.getId().toString());
                bw.newLine();
            }
            for (ColorableGraphArc arc : controller.getGraph().getArcs()) {
                bw.append("AD:" + arc.getId().toString() + "|");
                bw.append(arc.getFromId().toString() + "|");
                bw.append(arc.getToId().toString() + "|");
                bw.append(arc.getCost().toString());
                bw.newLine();
            }
            for (Node nodeView : controller.getNodes().values()) {
                bw.append("NV:" + nodeView.getNodeId() + "|");
                bw.append(Double.toString(nodeView.getCenterX()) + "|");
                bw.append(Double.toString(nodeView.getCenterY()));
                bw.newLine();
            }
            for (MainController.Arc arcView : controller.getArcs().values()) {
                bw.append("AV:" + arcView.getGraphArc().getId() + "|");
                bw.append(Double.toString(arcView.getStartX()) + "|");
                bw.append(Double.toString(arcView.getStartY()) + "|");
                bw.append(Double.toString(arcView.getEndX()) + "|");
                bw.append(Double.toString(arcView.getEndY()));
                bw.newLine();
            }
            controller.log("Fájl mentve: " + saveFile.getAbsolutePath());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphIO.class.getName()).log(Level.SEVERE, null, ex);
            controller.log("Nem sikerült menteni a fájlt: " + ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(GraphIO.class.getName()).log(Level.SEVERE, null, ex);
            controller.log("Nem sikerült menteni a fájlt: " + ex.getMessage());
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ex) {
                    Logger.getLogger(GraphIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void loadGraph(File loadFile, MainController controller) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(loadFile));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.matches("ND.*")) {
                    controller.log("Node->" + line);
                    controller.createNode(Integer.parseInt(line.split(":")[1]));
                } else if (line.matches("AD.*")) {
                    controller.log("Arc->" + line);
                    String[] data = line.split(":")[1].split("\\|");
                    controller.createArc(Integer.parseInt(data[0]),
                            Integer.parseInt(data[1]),
                            Integer.parseInt(data[2]),
                            Float.parseFloat(data[3]));
                } else if (line.matches("NV.*")) {
                    controller.log("NodeView->" + line);
                    String[] data = line.split(":")[1].split("\\|");
                    controller.createNodeView(Integer.parseInt(data[0]),
                            Double.parseDouble(data[1]),
                            Double.parseDouble(data[2]));

                } else if (line.matches("AV.*")) {
                    controller.log("ArcView->" + line);
                    String[] data = line.split(":")[1].split("\\|");
                    controller.createArcView(Integer.parseInt(data[0]),
                            Double.parseDouble(data[1]),
                            Double.parseDouble(data[2]),
                            Double.parseDouble(data[3]),
                            Double.parseDouble(data[4]));
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GraphIO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GraphIO.class.getName()).log(Level.SEVERE, null, ex);
            controller.log("Hiba a betöltés során.");
            controller.clearFullGraph();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(GraphIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static class GraphHolder implements Serializable {

        Graph graph;
        HashMap<Integer, Circle> circles;
    }
}
