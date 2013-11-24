/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms;

import com.sun.javafx.animation.transition.Position2D;
import hu.elte.graphalgorithms.algorithms.implementations.KruskalAlgorithm;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphArc;
import hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode;
import static hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode.Color.BLACK;
import static hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode.Color.BLUE;
import static hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode.Color.GRAY;
import static hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode.Color.GREEN;
import static hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode.Color.PURPLE;
import static hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode.Color.RED;
import static hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode.Color.WHITE;
import static hu.elte.graphalgorithms.algorithms.util.ColorableGraphNode.Color.YELLOW;
import hu.elte.graphalgorithms.core.ExtendedDirectedGraph;
import hu.elte.graphalgorithms.core.exceptions.UndirectedGraphRequiredException;
import hu.elte.graphalgorithms.core.interfaces.Graph;
import hu.elte.graphalgorithms.exceptions.NotSupportedModeException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
import javafx.util.Callback;
import javafx.util.Pair;
import javax.print.attribute.HashDocAttributeSet;
import jfx.messagebox.MessageBox;

/**
 *
 * @author nagysan
 */
public class MainController implements Initializable {

    protected static final String NODE_ID_KEY = "NodeId";
    protected static final String LABEL_KEY = "Label";
    protected static final String ATTACHED_CIRCLE_KEY = "AttachedNode";
    protected static final String INBOUND_ARC_PREFIX = "INBOUNDARC_";
    protected static final String OUTBOUND_ARC_PREFIX = "OUTBOUNDARC_";
    protected static final int ARC_DISTANCE = 8;
    protected Graph<ColorableGraphNode, ColorableGraphArc> graph = new ExtendedDirectedGraph<>();
    private Mode currentMode;
    private Node selectedNode = null;
    private Node selectedNodeInArcMode = null;
    private HashMap<Integer, Node> circles = new HashMap<>();
    private Node arcTo;
    @FXML
    private ComboBox<AlgorithmModel> cbAlgoritmSelector;
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
    @FXML
    private Button btClearSelectedNode;
    @FXML
    private Label lbArcCount;
    @FXML
    private Label lbNodeCount;
    @FXML
    private Label lbDirected;
    @FXML
    private ListView<String> lvLog;

    protected void setSelectedNode(Node c) {
        if (c != null) {
            log("Current node: " + c.getNodeId());
            btClearSelectedNode.setDisable(false);
        }
        selectedNode = c;
        refreshGraphView();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentMode = Mode.NODE;
        assert ap != null : "fx:id=\"ap\" was not injected: check your FXML file 'Main.fxml'.";
        assert arcModeRb != null : "fx:id=\"arcModeRb\" was not injected: check your FXML file 'Main.fxml'.";
        assert btClearSelectedNode != null : "fx:id=\"btClearSelectedNode\" was not injected: check your FXML file 'Main.fxml'.";
        assert btRun != null : "fx:id=\"btRun\" was not injected: check your FXML file 'Main.fxml'.";
        assert cbAlgoritmSelector != null : "fx:id=\"cbAlgoritmSelector\" was not injected: check your FXML file 'Main.fxml'.";
        assert deleteModeRb != null : "fx:id=\"deleteModeRb\" was not injected: check your FXML file 'Main.fxml'.";
        assert modeGroup != null : "fx:id=\"modeGroup\" was not injected: check your FXML file 'Main.fxml'.";
        assert nodeModeRb != null : "fx:id=\"nodeModeRb\" was not injected: check your FXML file 'Main.fxml'.";
        initAlgorithmList();
    }

    @FXML
    public void mouseMoved(MouseEvent e) {
        //log("Pos:"+e.getX()+"|"+e.getY());
    }

    @FXML
    void onAlgorithmChanged(ActionEvent event) {

        log(cbAlgoritmSelector.getSelectionModel().getSelectedItem().getName());
    }
    Arc arc = null;

    private double distance(double ax, double ay, double bx, double by) {
        return Math.sqrt(Math.pow(ax - bx, 2) + Math.pow(ay - by, 2));
    }
    Position2D mousepos = null;

    @FXML
    public void mouseDragged(MouseEvent e) {
        if (e.getButton().equals(MouseButton.PRIMARY) && currentMode.equals(Mode.NODE)) {

            if (selectedNode != null) {

                selectedNode.setCenterX(e.getX());
                selectedNode.setCenterY(e.getY());
                //selectedNode.relocate(e.getX() - selectedNode.getRadius(), e.getY() - selectedNode.getRadius());
                //log(Double.toString(selectedNod.getX())+"|"+Double.toString(selectedNode.getCenterY()));
                Text label = (Text) selectedNode.getProperties().get(LABEL_KEY);
                label.setX(e.getX() - label.getLayoutBounds().getWidth() / 2 + 1);
                label.setY(e.getY() + 5);
                for (Map.Entry<Object, Object> p : selectedNode.getProperties().entrySet()) {
                    if (p.getValue() instanceof Arc) {
                        Arc arc = (Arc) p.getValue();
                        Integer u = arc.getNodeFromId();
                        Integer v = arc.getNodeToId();
                        log(u + "|" + v);
                        Node nodeTo = circles.get(v);
                        Node nodeFrom = circles.get(u);
                        if (graph.getArc(v, u) != null) {

                            Pair<Point2D, Point2D> points1 = getPoints(nodeFrom.getCenterX(), nodeFrom.getCenterY(), nodeTo.getCenterX(), nodeTo.getCenterY(), ARC_DISTANCE);
                            Pair<Point2D, Point2D> points2 = getPoints(nodeTo.getCenterX(), nodeTo.getCenterY(), nodeFrom.getCenterX(), nodeFrom.getCenterY(), ARC_DISTANCE);
                            if (u < v) {
                                moveArc(arc, points1.getKey().getX(), points1.getKey().getY(), points2.getKey().getX(), points2.getKey().getY());
                            } else {
                                moveArc(arc, points1.getValue().getX(), points1.getValue().getY(), points2.getValue().getX(), points2.getValue().getY());
                            }
                        } else {
                            moveArc(arc, nodeFrom.getCenterX(), nodeFrom.getCenterY(), nodeTo.getCenterX(), nodeTo.getCenterY());
                        }
                    }
                }
            }
        }
        if (e.getButton().equals(MouseButton.PRIMARY) && currentMode.equals(Mode.ARC)) {
            if (arc != null) {
                arc.setEnd(e.getX(), e.getY());;
            }
            for (Node c : circles.values()) {
                if (distance(e.getX(), e.getY(), c.getCenterX(), c.getCenterY()) < c.getRadius()) {
                    arcTo = c;
                }
            }
            //ap.getChildren().add(new Node(e.getX(), e.getY(), 1));
        }
    }

    @FXML
    public void mouseReleased(MouseEvent e) {
        log("Released");
    }

    @FXML
    public void mousePressed(MouseEvent e) throws Exception {
        if (e.getButton().equals(MouseButton.PRIMARY) && currentMode.equals(Mode.NODE)) {
            Integer id = graph.createNode(new ColorableGraphNode());
            final Node c = new Node(id,20);
            ap.getChildren().add(c);
            circles.put(id, c);
            c.setCenterX(e.getX());
            c.setCenterY(e.getY());
            Text label = new Text(id.toString());
            c.getProperties().put(LABEL_KEY, label);
            ap.getChildren().add(label);
            c.setFill(getNodeColor(graph.getNode(id).getColor()));
            label.setFill(getNodeTextColor(graph.getNode(id).getColor()));
            label.setFont(Font.font("Times New Roman", FontWeight.BOLD, 14));
            label.setTextAlignment(TextAlignment.CENTER);
            label.setX(e.getX() - label.getLayoutBounds().getWidth() / 2);
            label.setY(e.getY() + 5);
            label.getProperties().put(ATTACHED_CIRCLE_KEY, c);
            setSelectedNode(c);
            EventHandler<MouseEvent> mouseClickedEventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    Node circle = null;
                    if (t.getSource() instanceof Text) {
                        Text label = (Text) t.getSource();
                        circle = (Node) label.getProperties().get(ATTACHED_CIRCLE_KEY);
                    } else {
                        circle = (Node) t.getSource();
                    }
                    if (currentMode.equals(Mode.NODE)) {
                        setSelectedNode(circle);
                        System.out.println("Hello");
                        setSelectedNode(circle);
                        mousepos = new Position2D();
                        mousepos.x = t.getX();
                        mousepos.y = t.getY();
                    } else if (currentMode.equals(Mode.DELETE)) {
                        Integer nodeId = circle.getNodeId();
                        for (Map.Entry<Object, Object> p : circle.getProperties().entrySet()) {
                            Object o = p.getValue();
                            if (o instanceof Arc){
                                Arc arc = (Arc)o;
                            }
                        }
                        graph.removeNode(nodeId);
                        ap.getChildren().remove(circle);
                        if (selectedNode == circle) {
                            btClearSelectedNode.setDisable(true);
                            selectedNode = null;
                        }
                        circles.remove(nodeId);
                        Text label = (Text) circle.getProperties().get(LABEL_KEY);
                        circle.getProperties().clear();
                        ap.getChildren().remove(label);
                        lbNodeCount.setText(Integer.toString(graph.getNodeCount()));
                        lbDirected.setText(graph.isDirected() ? "Igen" : "Nem");
                    } else if (currentMode.equals(Mode.ARC)) {
                        if (selectedNodeInArcMode == null) {
                            selectedNodeInArcMode = circle;
                        } else {
                            try {
                                arcTo = circle;
                                Integer u = selectedNodeInArcMode.getNodeId();
                                Integer v = arcTo.getNodeId();
                                log(selectedNodeInArcMode.getNodeId() + "-->" + arcTo.getNodeId());
                                if (graph.getArc(u, v) != null) {
                                    MessageBox.show(null, "Többszörös él nem megengedett!", "Hiba", MessageBox.OK);
                                } else {
                                    if (!u.equals(v)) {
                                        graph.createArc(u, v, 1.0f, new ColorableGraphArc());


                                        lbArcCount.setText(Integer.toString(graph.getArcCount()));
                                        lbDirected.setText(graph.isDirected() ? "Igen" : "Nem");
                                    } else {
                                        MessageBox.show(null, "A hurokélek nem megengedettek!", "Hiba", MessageBox.OK);
                                    }
                                }

                                selectedNodeInArcMode = null;
                            } catch (Exception ex) {
                                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                    t.consume();
                }
            };


            label.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClickedEventHandler);
            c.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseClickedEventHandler);

            EventHandler<MouseEvent> mouseReleasedEventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    Node circle = null;
                    if (t.getSource() instanceof Text) {
                        Text label = (Text) t.getSource();
                        circle = (Node) label.getProperties().get(ATTACHED_CIRCLE_KEY);
                    } else {
                        circle = (Node) t.getSource();
                    }
                    if (currentMode.equals(Mode.NODE)) {
                        System.out.println("Hello");
                        setSelectedNode(circle);
                        mousepos = new Position2D();
                        mousepos.x = t.getX();
                        mousepos.y = t.getY();
                    } else if (currentMode.equals(Mode.ARC)) {
                        if (arc != null && selectedNodeInArcMode != null) {
                            try {
                                //arcTo = circle;
                                Integer u = selectedNodeInArcMode.getNodeId();
                                Integer v = arcTo.getNodeId();
                                log(selectedNodeInArcMode.getNodeId() + "-->" + arcTo.getNodeId());
                                if (graph.getArc(u, v) != null) {
                                    MessageBox.show(null, "Többszörös él nem megengedett!", "Hiba", MessageBox.OK);
                                    ap.getChildren().remove(arc);
                                } else {
                                    if (!u.equals(v)) {
                                        Integer id = graph.createArc(u, v, 1.0f, new ColorableGraphArc());
                                        arc.setGraphArc(graph.getArc(id));
                                        arcTo.getProperties().put(INBOUND_ARC_PREFIX + selectedNodeInArcMode.getNodeId().toString(), arc);
                                        selectedNodeInArcMode.getProperties().put(OUTBOUND_ARC_PREFIX + arcTo.getNodeId().toString(), arc);
                                        Pair<Point2D, Point2D> points1 = getPoints(selectedNodeInArcMode.getCenterX(), selectedNodeInArcMode.getCenterY(), arcTo.getCenterX(), arcTo.getCenterY(), ARC_DISTANCE);
                                        Pair<Point2D, Point2D> points2 = getPoints(arcTo.getCenterX(), arcTo.getCenterY(), selectedNodeInArcMode.getCenterX(), selectedNodeInArcMode.getCenterY(), ARC_DISTANCE);
                                        arc.setNodeToId(v);
                                        arc.setNodeFromId(u);

                                        if (graph.getArc(v, u) != null) {
                                            Arc otherArc = (Arc) selectedNodeInArcMode.getProperties().get(INBOUND_ARC_PREFIX + arcTo.getNodeId().toString());
                                            if (u < v) {
                                                moveArc(arc, points1.getKey().getX(), points1.getKey().getY(), points2.getKey().getX(), points2.getKey().getY());
                                                moveArc(otherArc, points1.getValue().getX(), points1.getValue().getY(), points2.getValue().getX(), points2.getValue().getY());
                                            } else {
                                                moveArc(otherArc, points1.getKey().getX(), points1.getKey().getY(), points2.getKey().getX(), points2.getKey().getY());
                                                moveArc(arc, points1.getValue().getX(), points1.getValue().getY(), points2.getValue().getX(), points2.getValue().getY());
                                            }
                                        } else {
                                            moveArc(arc, selectedNodeInArcMode.getCenterX(), selectedNodeInArcMode.getCenterY(), arcTo.getCenterX(), arcTo.getCenterY());
                                        }
                                        lbArcCount.setText(Integer.toString(graph.getArcCount()));
                                        lbDirected.setText(graph.isDirected() ? "Igen" : "Nem");
                                    } else {
                                        MessageBox.show(null, "A hurokélek nem megengedettek!", "Hiba", MessageBox.OK);
                                        ap.getChildren().remove(arc);

                                    }
                                }




                                arcTo = null;
                                arc = null;
                                selectedNodeInArcMode = null;
                            } catch (Exception ex) {
                                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                    t.consume();
                }
            };
            c.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseReleasedEventHandler);
            label.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseReleasedEventHandler);
            EventHandler<MouseEvent> mousePressedEventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    Node circle = null;
                    if (t.getSource() instanceof Text) {
                        Text label = (Text) t.getSource();
                        circle = (Node) label.getProperties().get(ATTACHED_CIRCLE_KEY);
                    } else {
                        circle = (Node) t.getSource();
                    }
                    if (currentMode.equals(Mode.NODE)) {
                        System.out.println("Hello");
                        setSelectedNode(circle);
                        mousepos = new Position2D();
                        mousepos.x = t.getX();
                        mousepos.y = t.getY();
                    } else if (currentMode.equals(Mode.ARC)) {
                        selectedNodeInArcMode = circle;
                        arc = new Arc(circle.getCenterX(), circle.getCenterY(), t.getX(), t.getY());
                        arc.setStrokeWidth(1.5);

                        ap.getChildren().add(arc);
                        arc.toBack();
                        arc.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent t) {
                                Arc l = (Arc) t.getTarget();
                                l.setStroke(Color.RED);
                                t.consume();
                            }
                        });
                        arc.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent t) {
                                Arc l = (Arc) t.getTarget();
                                l.setStroke(Color.BLACK);
                                t.consume();
                            }
                        });
                    }
                    t.consume();
                }
            };

            label.addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressedEventHandler);
            c.addEventFilter(MouseEvent.MOUSE_PRESSED, mousePressedEventHandler);

            EventHandler<MouseEvent> mouseMovedEventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    Node circle = null;
                    if (t.getSource() instanceof Text) {
                        Text label = (Text) t.getSource();
                        circle = (Node) label.getProperties().get(ATTACHED_CIRCLE_KEY);
                    } else {
                        circle = (Node) t.getSource();
                    }
                    log(System.currentTimeMillis() + "Moved on:" + circle.getNodeId());
                    t.consume();
                }
            };
            //label.addEventFilter(MouseEvent.MOUSE_MOVED, mouseMovedEventHandler);
            //c.addEventFilter(MouseEvent.MOUSE_MOVED, mouseMovedEventHandler);
            lbNodeCount.setText(Integer.toString(graph.getNodeCount()));
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
        log("Current mode: " + currentMode.toString());
    }

    private void getPolylineArc(Point2D tip, Point2D tail, int mode) {
        Polyline pl = new Polyline();
        double dy = tip.getY() - tail.getY();
        double dx = tip.getX() - tail.getX();
        double theta = Math.atan2(dy, dx);
        //System.out.println("theta = " + Math.toDegrees(theta));  
        double phi = Math.toRadians(30);

        double x1, y1,x2,y2, rho = theta + phi;
            x1 = tip.getX() - 20 * Math.cos(rho);
            y1 = tip.getY() - 20 * Math.sin(rho);
            rho = theta - phi;
            x2 = tip.getX() - 20 * Math.cos(rho);
            y2 = tip.getY() - 20 * Math.sin(rho);
    }

    @FXML
    protected void clearGraph() {
        if (MessageBox.show(null, "Biztosan törli a gráfot?", "Megerősítés kérése", MessageBox.YES | MessageBox.NO) == MessageBox.YES) {
            graph.clear();
            ap.getChildren().clear();
            circles.clear();
        }
    }

    @FXML
    protected void close() {
        if (MessageBox.show(null, "Biztosan kilép?", "Megerősítés kérése", MessageBox.YES | MessageBox.NO) == MessageBox.YES) {
            System.exit(0);
        }
    }

    private Color getNodeColor(ColorableGraphNode.Color nodeColor) {
        Color color = null;
        switch (nodeColor) {
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
        return color;
    }

    private Color getNodeTextColor(ColorableGraphNode.Color nodeColor) {
        Color textColor = Color.RED;
        switch (nodeColor) {
            case WHITE:
                textColor = Color.BLACK;
                break;
            case BLACK:
                textColor = Color.WHITE;
                break;
            case BLUE:
                break;
            case GRAY:
                textColor = Color.DEEPSKYBLUE;
                break;
            case GREEN:
                break;
            case PURPLE:
                break;
            case RED:
                textColor = Color.YELLOW;
                break;
            case YELLOW:
                textColor = Color.RED;
                break;
            default:
                textColor = Color.RED;
                break;
        }
        return textColor;
    }

    private void refreshGraphView() {
        for (Node c : circles.values()) {
            Integer id = c.getNodeId();
            ColorableGraphNode node = graph.getNode(id);
            Color color = getNodeColor(node.getColor());
            Color textColor = getNodeTextColor(node.getColor());

            c.setFill(color);
            Text label = (Text) c.getProperties().get(LABEL_KEY);
            label.setFill(textColor);
            if (c == selectedNode) {
                selectedNode.setStrokeWidth(3);
                selectedNode.setStroke(Color.RED);
            } else {
                c.setStrokeWidth(3);
                c.setStroke(getNodeColor(graph.getNode(c.getNodeId()).getColor()));
            }
            //Tooltip.install(c, new Tooltip(c.getProperties().get(NODE_ID_KEY).toString()));
        }

    }

    @FXML
    public void btRunClicked() throws UndirectedGraphRequiredException {
        AlgorithmModel currentAlgorithm = cbAlgoritmSelector.getSelectionModel().getSelectedItem();
        if (currentAlgorithm.needStartNode && selectedNode == null) {
            MessageBox.show(null, "A kiválasztott algoritmus startcsúcsot igényel!", "Figyelmeztetés", MessageBox.OK | MessageBox.ICON_WARNING);
            return;
        }
        if (currentAlgorithm.id == 0) {
            if (graph.isDirected()) {
                MessageBox.show(null, "A kiválasztott algoritmus irányítatlan gráfot igényel!", "Figyelmeztetés", MessageBox.OK | MessageBox.ICON_WARNING);
                return;
            }
            KruskalAlgorithm ka = new KruskalAlgorithm();
            ka.initialize(graph);
            log(ka.run());
            for (ColorableGraphArc ga : graph.getArcs()) {
                log(ga.toString());

            }
        }
        refreshGraphView();
    }

    @FXML
    public void clearSelectedNode() {
        setSelectedNode(null);
        btClearSelectedNode.setDisable(true);
    }

    private void log(String message) {
        lvLog.getItems().add(message != null ? message : "The message is null!");
        lvLog.scrollTo(lvLog.getItems().size() - 1);
    }

    private void initAlgorithmList() {
        cbAlgoritmSelector.getItems().clear();
        cbAlgoritmSelector.getItems().add(new AlgorithmModel("Kruskal algoritmus", 0, false));
        cbAlgoritmSelector.getItems().add(new AlgorithmModel("Dijkstra algoritmus", 1, true));
        cbAlgoritmSelector.getItems().add(new AlgorithmModel("Szélességi bejárás", 2, true));
        cbAlgoritmSelector.getItems().add(new AlgorithmModel("Mélységi bejárás", 3, true));
        cbAlgoritmSelector.getSelectionModel().selectFirst();
        cbAlgoritmSelector.setCellFactory(new Callback<ListView<AlgorithmModel>, ListCell<AlgorithmModel>>() {
            @Override
            public ListCell<AlgorithmModel> call(ListView<AlgorithmModel> p) {
                final ListCell<AlgorithmModel> cell = new ListCell<AlgorithmModel>() {
                    @Override
                    protected void updateItem(AlgorithmModel t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.toString());
                        } else {
                            setText(null);
                        }
                    }
                };

                return cell;
            }
        });
    }

    private static class AlgorithmModel {

        String name;
        Integer id;
        Boolean needStartNode;

        public AlgorithmModel(String name, Integer id, Boolean needStartNode) {
            this.name = name;
            this.id = id;
            this.needStartNode = needStartNode;
        }

        @Override
        public String toString() {
            return name;
        }

        private String getName() {
            return name;
        }
    }

    private enum Mode {

        NODE, ARC, DELETE
    };

    static Pair<Point2D, Point2D> getPoints(double u, double v, double x, double y, double r) {
        double vx = y - v;
        double vy = -(x - u);
        double length = (double) Math.sqrt(vx * vx + vy * vy);
        vx = vx / length;
        vy = vy / length;
        double a = vy;
        double b = -vx;
        double c = vy * u - vx * v;
        double A = c - a * u;
        double C = a * a + b * b;
        double D = -2 * (A * b + v * a * a);
        double E = A * A + a * a * (v * v - r * r);
        double sy1 = (double) (-D + Math.sqrt(D * D - 4 * C * E)) / (2 * C);
        double sy2 = (double) (-D - Math.sqrt(D * D - 4 * C * E)) / (2 * C);
        double sx1 = (u != x) ? (c - b * sy1) / a : u-r;
        double sx2 = (u != x) ? (c - b * sy2) / a : u+r;
//        System.out.println(Arrays.toString(new double[]{vx, vy, a, b, c, A, C, D, E, D * D - 4 * C * E}));
//        System.out.println("(" + sx1 + "," + sy1 + ")");
//        System.out.println("(" + sx2 + "," + sy2 + ")");
        return new Pair(new Point2D(sx1, sy1), new Point2D(sx2, sy2));
    }

    protected void moveArc(Arc arc, double startX, double startY, double endX, double endY) {
        arc.setStart(startX, startY);
        arc.setEnd(endX, endY);
    }
    
    protected class Node extends Circle {
        protected Integer nodeId;

        public Node(Integer nodeId) {
            this.nodeId = nodeId;
        }

        public Node(Integer nodeId, double d) {
            super(d);
            this.nodeId = nodeId;
        }

        public Integer getNodeId() {
            return nodeId;
        }

        public void setNodeId(Integer nodeId) {
            this.nodeId = nodeId;
        }
        
        
        
    }
    
    protected class Arc extends Line {
        
        protected Integer nodeToId;
        protected Integer nodeFromId;
        protected ColorableGraphArc graphArc;
        protected Text label = new Text("1");
        
        {
            label.setFill(Color.ANTIQUEWHITE);
        }

        public Arc(double d, double d1, double d2, double d3) {
            super(d, d1, d2, d3);
            refreshLabel();
        }
        
        public void setEnd(double x, double y){
            setEndX(x);
            setEndY(y);
            refreshLabel();
        }
        
        public void setStart(double x, double y){
            setStartX(x);
            setStartY(y);
            refreshLabel();
        }

        public Integer getNodeToId() {
            return nodeToId;
            
        }

        public void setNodeToId(Integer nodeToId) {
            this.nodeToId = nodeToId;
        }

        public Integer getNodeFromId() {
            return nodeFromId;
        }

        public void setNodeFromId(Integer nodeFromId) {
            this.nodeFromId = nodeFromId;
        }

        public ColorableGraphArc getGraphArc() {
            return graphArc;
        }

        public void setGraphArc(ColorableGraphArc graphArc) {
            this.graphArc = graphArc;
            ap.getChildren().add(label);
            refreshLabel();
        }


        private void refreshLabel(){
            label.setX((getEndX()+getStartX()-label.getWrappingWidth())/2);
            label.setY((getEndY()+getStartY())/2);
//            label.setX(20);
//            label.setY(100);
        }
        
    }
}
