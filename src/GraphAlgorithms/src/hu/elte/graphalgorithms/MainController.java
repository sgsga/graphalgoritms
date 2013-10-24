/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms;

import hu.elte.graphalgorithms.algorithms.implementations.KruskalAlgorithm;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphArc;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode;
import static hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode.Color.BLACK;
import static hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode.Color.PURPLE;
import hu.elte.graphalgorithms.core.ExtendedDirectedGraph;
import hu.elte.graphalgorithms.core.exceptions.UndirectedGraphRequiredException;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import hu.elte.graphalgorithms.exceptions.NotSupportedModeException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import jfx.messagebox.MessageBox;

/**
 *
 * @author nagysan
 */
public class MainController implements Initializable {

    protected static final String NODE_ID_KEY = "NodeId";
    protected static final String LABEL_KEY = "Label";
    
    private void changeSelectedNode(Circle c) {
        System.out.println("Current node: " + c.getProperties().get(NODE_ID_KEY));
        if (selectedNode != null) {
            selectedNode.setStrokeWidth(3);
            selectedNode.setStroke(Color.GRAY);
        }
        selectedNode = c;
        selectedNode.setStrokeWidth(3);
        selectedNode.setStroke(Color.RED);
    }

    private enum Mode {

        NODE, ARC, DELETE
    };
    protected Graph<ColorableGraphNode, ColorableGraphArc> graph = new ExtendedDirectedGraph<>();
    private Mode currentMode;
    private Circle selectedNode = null;
    private ArrayList<Circle> circles = new ArrayList<>();
    @FXML
    private AnchorPane ap;
    @FXML
    private RadioButton nodeModeRb;
    @FXML
    private RadioButton arcModeRb;
    @FXML
    private RadioButton deleteModeRb;
    @FXML
    private ToggleGroup modeGroup;
    @FXML
    private Button btRun;
    private Circle arcTo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentMode = Mode.NODE;
    }

    @FXML
    public void mouseMoved(MouseEvent e) {
    }

    @FXML
    public void mouseDragged(MouseEvent e) {
        if (e.getButton().equals(MouseButton.PRIMARY) && currentMode.equals(Mode.NODE)) {
            if (selectedNode != null) {
                selectedNode.relocate(e.getX() - selectedNode.getRadius(), e.getY() - selectedNode.getRadius());
                Text label = (Text) selectedNode.getProperties().get(LABEL_KEY);
                label.setX(e.getX()-label.getLayoutBounds().getWidth()/2+1);
                label.setY(e.getY()+5);
            }
        }
    }

    @FXML
    public void mouseReleased(MouseEvent e) {
    }

    @FXML
    public void mousePressed(MouseEvent e) throws Exception {
        if (e.getButton().equals(MouseButton.PRIMARY) && currentMode.equals(Mode.NODE)) {
            Integer id = graph.createNode(new ColorableGraphNode());
            Circle c = new Circle(20);
            ap.getChildren().add(c);
            circles.add(c);
            System.out.println(selectedNode);
            changeSelectedNode(c);
            c.getProperties().put(NODE_ID_KEY, id);
            System.out.println(c.getProperties().get(NODE_ID_KEY));
            c.setCenterX(e.getX());
            c.setCenterY(e.getY());
            Text label = new Text(id.toString());
            c.getProperties().put(LABEL_KEY, label);
            ap.getChildren().add(label);
            label.setFill(Color.RED);
            label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
            label.setTextAlignment(TextAlignment.CENTER);
            label.setX(e.getX()-label.getLayoutBounds().getWidth()/2);
            label.setY(e.getY()+5);
            label.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                   t.consume();
                }
            });
//            label.setLayoutX(e.getX()-label.getWidth()/2);
//            label.setLayoutX(e.getY()-label.getHeight()/2);
            c.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    Circle circle = (Circle) t.getSource();
                    if (currentMode.equals(Mode.NODE)) {
                        changeSelectedNode(circle);
                    } else if (currentMode.equals(Mode.DELETE)) {
                        Integer nodeId = (Integer) circle.getProperties().get(NODE_ID_KEY);
                        graph.removeNode(nodeId);
                        ap.getChildren().remove(circle);
                        circles.remove(circle);
                        Text label = (Text) circle.getProperties().get(LABEL_KEY);
                        circle.getProperties().clear();
                        ap.getChildren().remove(label);
                        System.out.println("Current node count: " + graph.getNodeCount());
                    } else if (currentMode.equals(Mode.ARC)) {
                        if (selectedNode == null) {
                            selectedNode = circle;
                        } else {
                            try {
                                arcTo = (Circle) t.getSource();
                                Integer u = (Integer) selectedNode.getProperties().get(NODE_ID_KEY);
                                Integer v = (Integer) arcTo.getProperties().get(NODE_ID_KEY);
                                System.out.println(selectedNode.getProperties().get(NODE_ID_KEY) + "-->" + arcTo.getProperties().get(NODE_ID_KEY));
                                if (graph.getArc(u, v) != null) {
                                    MessageBox.show(null, "Többszörös él nem megengedett!", "Hiba", MessageBox.OK);
                                } else {
                                    if (!u.equals(v)) {
                                        graph.createArc(u, v, 1.0f, new ColorableGraphArc());
                                        Line l = new Line();
                                        ap.getChildren().add(l);
                                        double dx;
                                        if (u<v){ dx = 10; }
                                        else {dx = -10;}
                                        l.setStartX(selectedNode.getCenterX()+dx);
                                        l.setStartY(selectedNode.getCenterY());
                                        l.setEndX(arcTo.getCenterX()+dx);
                                        l.setEndY(arcTo.getCenterY());
                                        l.toBack();
                                    } else {
                                        MessageBox.show(null, "A hurokélek nem megengedettek!", "Hiba", MessageBox.OK);
                                    }
                                }

                                selectedNode = null;
                            } catch (Exception ex) {
                                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                    t.consume();
                }
            });
            c.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    if (currentMode.equals(Mode.NODE)) {
                    } else if (currentMode.equals(Mode.ARC)) {
                    }
                    t.consume();
                }
            });
            c.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                }
            });
            System.out.println("Current node count: " + graph.getNodeCount());
        }
    }

    @FXML
    public void mouseClicked(MouseEvent e) {
    }

    @FXML
    public void modeChanged() throws NotSupportedModeException {
        if (modeGroup.getSelectedToggle().equals(nodeModeRb)) {
            currentMode = Mode.NODE;
        } else if (modeGroup.getSelectedToggle().equals(arcModeRb)) {
            currentMode = Mode.ARC;
        } else if (modeGroup.getSelectedToggle().equals(deleteModeRb)) {
            currentMode = Mode.DELETE;
        } else {
            throw new NotSupportedModeException();
        }
        System.out.println("Current mode: " + currentMode.toString());
    }

    private Polyline getPolylineArc(Point2D tip, Point2D tail, int mode) {
        Polyline pl = new Polyline();
        double dy = tip.getY() - tail.getY();
        double dx = tip.getX() - tail.getX();
        double theta = Math.atan2(dy, dx);
        //System.out.println("theta = " + Math.toDegrees(theta));  
        double phi = Math.toRadians(30);

        double x, y, rho = theta + phi;
        for (int j = 0; j < 2; j++) {
            x = tip.getX() - 20 * Math.cos(rho);
            y = tip.getY() - 20 * Math.sin(rho);

            rho = theta - phi;
        }
        return null;
    }

    @FXML
    protected void clearGraph() {
        if (MessageBox.show(null, "Biztosan törli a gráfot?", "Megerősítés kérése", MessageBox.YES | MessageBox.NO) == MessageBox.YES) {
            graph.clear();
            for (Circle c : circles) {
                ap.getChildren().clear();
            }
            circles.clear();
        }
    }

    @FXML
    protected void close() {
        if (MessageBox.show(null, "Biztosan kilép?", "Megerősítés kérése", MessageBox.YES | MessageBox.NO) == MessageBox.YES) {
            System.exit(0);
        }
    }

    private void refreshGraphView() {
        for (Circle c : circles) {
            Integer id = (Integer) c.getProperties().get(NODE_ID_KEY);
            ColorableGraphNode node = graph.getNode(id);
            Color color = null;
            switch (node.getColor()) {
                case WHITE:
                    color = Color.WHITE;
                    break;
                case BLACK:
                    color = Color.BLACK;
                    break;
                case BLUE:
                    color = Color.BLUE;
                    break;
                case GRAY:
                    color = Color.GRAY;
                    break;
                case GREEN:
                    color = Color.GREEN;
                    break;
                case PURPLE:
                    color = Color.PURPLE;
                    break;
                case RED:
                    color = Color.RED;
                    break;
                case YELLOW:
                    color = Color.YELLOW;
                    break;
            }
            c.setFill(color);
            //Tooltip.install(c, new Tooltip(c.getProperties().get(NODE_ID_KEY).toString()));
        }
        
    }

    @FXML
    public void btRunClicked() throws UndirectedGraphRequiredException {
        System.out.println("Clicked");
        KruskalAlgorithm ka = new KruskalAlgorithm();
        ka.initialize(graph);
        System.out.println(ka.run());
        for (ColorableGraphArc ga : graph.getArcs()) {
            System.out.println(ga);

        }
        refreshGraphView();
    }
}
