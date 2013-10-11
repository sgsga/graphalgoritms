/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.graphalgorithms;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

/**
 *
 * @author nagysan
 */
public class MainController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private AnchorPane ap;
    private Circle selectedNode = null;
    ArrayList<Circle> circles = new ArrayList<>();

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void mouseMoved(MouseEvent e) {
    }
    
    @FXML
    public void mouseDragged(MouseEvent e) {
        System.out.println("MouseMoved and Selected node is"+(selectedNode != null?" not null":" null"));
        if (selectedNode != null ) {


            
            double x = selectedNode.getLayoutX();
            double y = selectedNode.getLayoutY();
            selectedNode.relocate(e.getX()-selectedNode.getRadius(), e.getY()-selectedNode.getRadius());
            System.out.println(e.getX() + "|" + e.getY());
            System.out.println(selectedNode.getBoundsInParent().getMinX() + "|" + selectedNode.getBoundsInParent().getMaxY());
            
            
            System.out.println("Pos");
        }
    }

    @FXML
    public void mouseReleased(MouseEvent e) {
        selectedNode = null;
        System.out.println("Released");
    }

    @FXML
    public void mousePressed(MouseEvent e) {
        Circle c = new Circle(20);
        ap.getChildren().add(c);
        circles.add(c);
        selectedNode = c;
        c.setCenterX(e.getX());
        c.setCenterY(e.getY());
        c.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                System.out.println("Mouse pressed on:" + t.getSource().toString());
                selectedNode = (Circle) t.getSource();
                t.consume();
            }
        });
        c.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                System.out.println("Mouse released on:" + t.getSource().toString());
                selectedNode = null;
                t.consume();
            }
        });
        c.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                System.out.println("Mouse moved on:" + t.getSource().toString());

            }
        });
    }

    @FXML
    public void mouseClicked(MouseEvent e) {
    }
}
